package net;

import io.netty.handler.codec.ByteToMessageCodec;
import gen.cmd.Command;
import gen.msg.gate.*;
import common.Trace;
import xio.*;
import xio.codec.ProtocolCoder;

public class WebManager extends DynamicMultiClientManager {

    private static final Coder.Factory coderFactory = new Coder.Factory() {
        @Override
        public ByteToMessageCodec<Object> create(Xio xio, Xio.Conf conf) {
        	return new ProtocolCoder(xio, conf);
        }
    };

    private static final ProtocolDispatcher dispatcher = new ProtocolDispatcher();

    public static Coder.Factory getCoderFactory() { return coderFactory; }
    public  static ProtocolDispatcher getDispatcher() { return dispatcher; }

    private static WebManager instance = null;
    private static final int DEFAULT_SERVERID = 1;
    
    public static WebManager getInstance() { return instance; }

    public static void start(Conf conf) {
    	
        if (instance == null) {
            instance = new WebManager(conf);
            instance.open(false);
            
            conf.getStubs().put(GAuth.TYPEID, new GAuth());
            conf.getStubs().put(SAuth.TYPEID, new SAuth());
            conf.getStubs().put(SSetCabinets.TYPEID, new SSetCabinets());
            conf.getStubs().put(GSetCabinets.TYPEID, new GSetCabinets());
            conf.getStubs().put(SForward.TYPEID, new SForward());
            conf.getStubs().put(GForward.TYPEID, new GForward());
            
            instance.updateServers(conf.getServers());
        }
    }

    private WebManager(Conf conf) {
        super(conf);
        dispatcher.register(SAuth.class, this::process);
        dispatcher.register(SSetCabinets.class, this::process);
        dispatcher.register(SForward.class, this::process);
    }


    @Override
    protected void onAddClient(DynamicMultiClientManager.DynamicClient client) {
        super.onAddClient(client);

        this.register(DEFAULT_SERVERID, client);
        
        GAuth auth = new GAuth();
        auth.username = "boxer";
        auth.password = "IEKp4yNGMuZVe2amNewCTwkCnekVaflo";
        send(auth);
    }

    public void forward(Command cmd) {
        GForward forward = new GForward();
        forward.cabId = cmd.cabId;
        Command.marshal(cmd, forward.data);
        send(forward);
        
        Trace.info("forward to web server: cabId={}, data={}", 
        		forward.cabId, forward.data.toHexString(true));
    }
    
    private void send(Protocol p) {
    	this.send(DEFAULT_SERVERID, p);
    }

    private void process(SForward forward) {
        CabManager.getInstance().send(forward.cabId, 
        		((Session)forward.getContext()).getXio().getRemoteHost(), forward.data);
    }

    private void process(SAuth auth) {
    	
    }

    private void process(SSetCabinets cabinets) {
        CabManager.getInstance().updateCabinets(cabinets);
    }
}
