package gen.cmd.cab;

import gen.cmd.Command;
import xio.Protocol;

public class CSetDoorFinger extends Command {

    public static final short CMDID = (short)0xFF31;

    public byte result;

    public CSetDoorFinger() {
        super((byte)0, CMDID);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");
        sb.append(",tag:").append(tag);
        sb.append(",cabId:").append(cabId);
        sb.append(",cmd:").append(cmd);
        sb.append(",result:").append(result);
        sb.append("}");
        return sb.toString();
    }

    public void marshal(common.marshal.Octets bs) {
        super.marshal(bs);
        bs.writeByte(result);
    }

    public void unmarshal(common.marshal.Octets bs) {
        super.unmarshal(bs);
        result = bs.readByte();
    }


    public int getTypeId() {
        return CMDID;
    }

    public Protocol newObject() {
        return new CSetDoorFinger();
    }
}

