package gen.cmd.cab;

import java.util.ArrayList;
import java.util.List;

import gen.cmd.Command;
import gen.cmd.Utils;
import xio.Protocol;

public class CCabStatus extends Command {

    public static final short CMDID = (short)0xFF33;

    public long date;
    public byte groupSize;
    public byte fingerArea;
    public List<BCabStatus> cabStatuses;

    public CCabStatus() {
        super((byte)0, CMDID);
        cabStatuses = new ArrayList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");
        sb.append(",tag:").append(tag);
        sb.append(",cabId:").append(cabId);
        sb.append(",cmd:").append(cmd);
        sb.append(",date:").append(date);
        sb.append(",groupSize:").append(groupSize);
        sb.append(",fingerArea:").append(fingerArea);
        sb.append(",cabStatuses:").append(cabStatuses);
        sb.append("}");
        return sb.toString();
    }

    public void marshal(common.marshal.Octets bs) {
        super.marshal(bs);

        Utils.marshalDate(date, bs);
        bs.writeByte(groupSize);
        bs.writeByte(fingerArea);

        for (BCabStatus status : cabStatuses) {
            status.marshal(bs);
        }
    }

    public void unmarshal(common.marshal.Octets bs) {
        super.unmarshal(bs);

        this.date = Utils.unmarshalDate(bs);
        this.groupSize = bs.readByte();
        this.fingerArea = bs.readByte();

        while (bs.nonEmpty()) {
            BCabStatus status = new BCabStatus();
            status.unmarshal(bs, groupSize - 3);
            cabStatuses.add(status);
        }
    }

    public int getTypeId() {
        return CMDID;
    }

    public Protocol newObject() {
        return new CCabStatus();
    }
}
