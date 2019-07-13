package net;

import gen.cmd.Command;
import gen.cmd.cab.*;
import gen.cmd.srv.SSetDoorFinger;
import common.Trace;
import common.marshal.Octets;
import io.netty.handler.codec.ByteToMessageCodec;
import gen.msg.gate.BCabinetInfo;
import gen.msg.gate.GForward;
import gen.msg.gate.SSetCabinets;
import xio.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mongo.DoorBleStatus;

public class CabManager extends DynamicMultiClientManager {
	 
    private static final Coder.Factory coderFactory = new Coder.Factory() {
        @Override
        public ByteToMessageCodec<Object> create(Xio xio, Xio.Conf conf) {
            return new CabCoder(xio, conf);
        }
    };

    private static final ProtocolDispatcher dispatcher = new ProtocolDispatcher();

    public static Coder.Factory getCoderFactory() { return coderFactory; }
    public  static ProtocolDispatcher getDispatcher() { return dispatcher; }

    private static CabManager instance = null;

    public static CabManager getInstance() { return instance; }

    public static void start(Conf conf) {
        if (instance == null) {
            instance = new CabManager(conf);
            instance.open(false);
            
            conf.getStubs().put((int)CCabStatus.CMDID, new CCabStatus());
            conf.getStubs().put((int)CCabLog.CMDID, new CCabLog());
            conf.getStubs().put((int)CSetDoorFinger.CMDID, new CSetDoorFinger());
            //yinghui.yin for test
            //instance.updateServers(conf.getServers());
        }
    }

    private Map<String, Byte> cabInfos = new HashMap<>();

    private CabManager(Conf conf) {
        super(conf);

        // 命令特殊处理
        dispatcher.register(CCabStatus.class, this::process);
        dispatcher.register(CCabLog.class, this::process);
        dispatcher.register(CSetDoorFinger.class, this::process);
    }

    @Override
    protected void onAddClient(DynamicMultiClientManager.DynamicClient client) {

        super.onAddClient(client);

        Byte cabId = cabInfos.get(client.getName());
        if (cabId == null) {
        	cabId = 0X01; // default
        }
        if (cabId != null) {
            this.register(cabId, client);

            GCabStatus p = new GCabStatus();
            p.cabId = cabId;
            p.data = (byte) 0xFF;
            client.send(p);

            mongo.Module.INSTANCE.insert(true, p.cabId, client.getName(), "", p);
        }

    }

    public void updateCabinets(SSetCabinets cabinets) {
        Map<String, Client.Conf> servers = new HashMap<>();
        for (BCabinetInfo cab : cabinets.cabs) {
            Client.Conf conf = new Client.Conf();
            conf.ip = cab.addr;
            conf.port = cab.port;
            conf.init(this.conf.getWorkGroup(), getCoderFactory(), getDispatcher(), this.conf.getStubs());

            String name = String.format("%s:%d", cab.addr, cab.port);
            servers.putIfAbsent(name, conf);

            cabInfos.putIfAbsent(name, cab.cabId);
        }

        CabManager.getInstance().updateServers(servers);
    }

    public void send(byte cabId, String serverHost, Octets data) {
    	Trace.info("Send to cabinet: cabId={}, data={}", cabId, data.toHexString(true));
    	
    	Octets os = new Octets();
    	data.writeTo(os, false);
        if (!checkForward(cabId, serverHost, os)) {
        	Trace.error("Send to cabinet error: cabId={}, data={}", cabId, data.toHexString(true));
        } else if (cabId == 0) {
            this.getServerAsList().forEach(s -> {
            	s.send(data);
            });
        } else {
        	DynamicMultiClientManager.DynamicClient client = this.getServerByServerId(cabId);
            if (client != null) {
            	client.send(data);
           }
        }
    }

    public void process(CCabStatus p) {
        // 将数据插入mongodb
    	List<DoorBleStatus> statuses = makeDoorBleStatusList(p.cabStatuses);
    	mongo.Module.INSTANCE.insert(false, p.cabId, 
    			((Session)p.getContext()).getXio().getRemoteHost(), "", p, statuses);
    	
    	// 转发给web server
    	WebManager.getInstance().forward(p);
    	
    	/////////////////////////
    	 GForward forward = new GForward();
         forward.cabId = p.cabId;
         Command.marshal(p, forward.data);
         
         Trace.info("forward to web server: cabId={}, data={}", 
         		forward.cabId, forward.data.toHexString(true));
///////////////////////


    }

    public void process(CCabLog p) {
        if (!p.cabLogs.isEmpty()) {
            BCabLog cabLog = p.cabLogs.get(p.cabLogs.size() - 1);

            if (cabLog.isZeroMemory()) {
                // 结束时，发送39指令给设备，清除历史记录
                BCabLog lastLog = p.cabLogs.get(p.cabLogs.size() - 2);
                GClearLog cmd = new GClearLog();
                cmd.cabId = lastLog.cabId;
                cmd.data = (byte)0XFF;
                cmd.date = lastLog.logDate;
                ((Session)p.getContext()).send(cmd);
                
                mongo.Module.INSTANCE.insert(true, p.cabId, 
            			((Session)p.getContext()).getXio().getRemoteHost(), "", cmd);
            }

            // 存入数据库
            mongo.Module.INSTANCE.insert(false, p.cabId, 
        			((Session)p.getContext()).getXio().getRemoteHost(), "", p.cabLogs);
            
        	// 转发给web server
           WebManager.getInstance().forward(p);
        }
    }
    
    public void process(CSetDoorFinger p) {
    	// 将数据插入mongodb
    	mongo.Module.INSTANCE.insert(false, p.cabId, 
    			((Session)p.getContext()).getXio().getRemoteHost(), "", p);
    	
    	// 转发给web server
        WebManager.getInstance().forward(p);
    }


    private boolean checkForward(byte remoteCabId, String serverHost, Octets data) {
        byte tag = data.readByte();
        if (tag != (byte)0xEB) {
            return false;
        }

        byte cabId = data.readByte();
        short cmd = data.readFixShort();
        short size = data.readFixShort();
        
        Command command = null;
        if (cmd == SSetDoorFinger.CMDID) {
        	command = new SSetDoorFinger();
        }
        
        if (command != null) {
            command.cabId = cabId;
            command.unmarshal(data);

            // 将数据插入mongodb
            String remoteHost = "";
            if (remoteCabId > 0) {
            	DynamicMultiClientManager.DynamicClient client = this.getServerByServerId(remoteCabId);
            	if (client != null) {
            		remoteHost = client.getName();
            	}
            }
            
        	mongo.Module.INSTANCE.insert(true, remoteCabId, remoteHost, serverHost, command);
            return true;
        }

        return false;
    }
    
    private List<DoorBleStatus> makeDoorBleStatusList(List<BCabStatus> doors) {
    	List<DoorBleStatus> statuses = new ArrayList<>();
    	doors.forEach( door -> {
    		door.bleStatuses.forEach(ble -> {
    			DoorBleStatus status = new DoorBleStatus();
    			status.fingerCode = door.fingerCode;
    			status.doorNumber = door.doorNumber;
    			status.doorStatus = door.doorStatus;
    		    
    			status.batteryStatus = ble.batteryStatus;
    			status.isUsing = ble.isUsing;
    			status.isPrieded = ble.isPrieded;
    			status.isDisconnect = ble.isDisconnect;
    			status.isOutAuthed = ble.isOutAuthed;
    			status.status = ble.status;
    			statuses.add(status);
    		});
    	});
    	return statuses;
    }
}
