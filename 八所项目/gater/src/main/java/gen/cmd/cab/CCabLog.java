package gen.cmd.cab;

import gen.cmd.Command;
import xio.Protocol;

import java.util.ArrayList;
import java.util.List;

public class CCabLog extends Command {
    public static final short CMDID = (short)0xFF37;

    public List<BCabLog> cabLogs;

    public CCabLog() {
        super((byte) 0, CMDID);
        cabLogs = new ArrayList<>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");
        sb.append(",tag:").append(tag);
        sb.append(",cabId:").append(cabId);
        sb.append(",cmd:").append(cmd);
        sb.append(",cabLogs:").append(cabLogs);
        sb.append("}");
        return sb.toString();
    }

    public void marshal(common.marshal.Octets bs) {
        super.marshal(bs);

        for (BCabLog log : cabLogs) {
            log.marshal(bs);
        }
    }

    public void unmarshal(common.marshal.Octets bs) {
        super.unmarshal(bs);

        while (bs.nonEmpty()) {
            BCabLog log = new BCabLog();
            log.unmarshal(bs);
            cabLogs.add(log);
        }
    }

    public int getTypeId() {
        return CMDID;
    }

    public Protocol newObject() {
        return new CCabLog();
    }

}
