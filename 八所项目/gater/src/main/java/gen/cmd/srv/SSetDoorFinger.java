package gen.cmd.srv;

import xio.Protocol;
import gen.cmd.Command;
import gen.cmd.Utils;

import java.util.ArrayList;
import java.util.List;

public class SSetDoorFinger extends Command {

	public static final short CMDID = (short)0xFF31;

    public byte reserve;
    public byte fingerNumber1;
    public byte fingerNumber2;
    public byte doorNumber;

    public SSetDoorFinger() {
    	super((byte) 0, CMDID);
    	
        reserve = (byte)0;
        fingerNumber1 = (byte)0;
        fingerNumber2 = (byte)0;
        doorNumber = (byte)0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");
        sb.append(",tag:").append(tag);
        sb.append(",cabId:").append(cabId);
        sb.append(",cmd:").append(cmd);
        sb.append(",reserve:").append(reserve);
        sb.append(",fingerNumber1:").append(fingerNumber1);
        sb.append(",fingerNumber2:").append(fingerNumber2);
        sb.append(",doorNumber:").append(doorNumber);
        sb.append("}");
        return sb.toString();
    }

    public void marshal(common.marshal.Octets bs) {
        bs.writeByte(reserve);
        bs.writeByte(fingerNumber1);
        bs.writeByte(fingerNumber2);
        bs.writeByte(doorNumber);
    }

    public void unmarshal(common.marshal.Octets bs) {
        this.reserve = bs.readByte();
        this.fingerNumber1 = bs.readByte();
        this.fingerNumber2 = bs.readByte();
        this.doorNumber = bs.readByte();
    }

    public int getTypeId() {
        return CMDID;
    }
    
    public Protocol newObject() {
        return new SSetDoorFinger();
    }
}
