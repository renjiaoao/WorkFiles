package gen.cmd.cab;

import java.util.ArrayList;
import java.util.List;

import common.marshal.IMarshal;

public class BCabStatus implements IMarshal {

    public byte fingerCode;     // 指纹代码
    public byte doorNumber;     // 柜门编号
    public byte doorStatus;     // 柜门状态

    public List<BBleStatus> bleStatuses;

	public BCabStatus() {
		bleStatuses = new ArrayList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");
        sb.append(",fingerCode:").append(fingerCode);
        sb.append(",doorNumber:").append(doorNumber);
        sb.append(",doorStatus:").append(doorStatus);
        sb.append("bleStatuses:").append(bleStatuses);
        sb.append("}");
        return sb.toString();
    }

    public void marshal(common.marshal.Octets bs) {
        bs.writeByte(fingerCode);
        bs.writeByte(doorNumber);
        bs.writeByte(doorStatus);

        for(BBleStatus status : bleStatuses) {
        	status.marshal(bs);
        }
    }

    public void unmarshal(common.marshal.Octets bs) {
        fingerCode = bs.readByte();
        doorNumber = bs.readByte();
        doorStatus = bs.readByte();        
        for(byte i = 0; i < 0; i++) {
        	BBleStatus bleStatus = new BBleStatus();
        	bleStatus.unmarshal(bs);
        	bleStatuses.add(bleStatus);
        }
    }
    
    public void unmarshal(common.marshal.Octets bs, int groupSize) {
        fingerCode = bs.readByte();
        doorNumber = bs.readByte();
        doorStatus = bs.readByte();   
        
        for(byte i = 0; i < groupSize; i++) {
        	BBleStatus bleStatus = new BBleStatus();
        	bleStatus.unmarshal(bs);
        	bleStatuses.add(bleStatus);
        }
    }
}
