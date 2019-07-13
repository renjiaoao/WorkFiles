package gen.cmd;

import common.marshal.Octets;
import xio.Protocol;

public abstract class Command extends Protocol {
    public byte tag;
    public byte cabId;
    public short cmd;

    public Command(byte cabId, short cmd) {
        this.tag = (byte)0xEB;
        this.cabId = cabId;
        this.cmd = cmd;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");
        sb.append(",tag:").append(tag);
        sb.append(",cabId:").append(cabId);
        sb.append(",cmd:").append(cmd);
        sb.append("}");
        return sb.toString();
    }

    public static void marshal(Command cmd, common.marshal.Octets bs) {
        bs.writeByte(cmd.tag);
        bs.writeByte(cmd.cabId);
        bs.writeFixShort(cmd.cmd);
        
        Octets os = new Octets();
        cmd.marshal(os);
        bs.writeFixShort((short)os.size());
        os.writeTo(bs, false);
    }

    public void marshal(common.marshal.Octets bs) {
    }

    public void unmarshal(common.marshal.Octets bs) {
    }
}
