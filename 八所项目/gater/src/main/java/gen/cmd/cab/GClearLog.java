package gen.cmd.cab;

import gen.cmd.Command;
import gen.cmd.Utils;
import xio.Protocol;

public class GClearLog extends Command {

    public static final short CMDID = (short)0xFF39;

    public byte data;
    public long date;

    public GClearLog() {
        super((byte)0, CMDID);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");
        sb.append(",tag:").append(tag);
        sb.append(",cabId:").append(cabId);
        sb.append(",cmd:").append(cmd);
        sb.append(",data:").append(data);
        sb.append(",date:").append(date);
        sb.append("}");
        return sb.toString();
    }

    public void marshal(common.marshal.Octets bs) {
        super.marshal(bs);
        bs.writeByte(data);
        Utils.marshalDate(date, bs);
    }

    public void unmarshal(common.marshal.Octets bs) {
        super.unmarshal(bs);
        data = bs.readByte();
        date = Utils.unmarshalDate(bs);
    }


    public int getTypeId() {
        return CMDID;
    }

    public Protocol newObject() {
        return new GClearLog();
    }
}
