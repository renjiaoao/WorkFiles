package gen.msg.gate;

public final class BCabinetInfo implements common.marshal.IMarshal {

    public byte cabId;
    public String addr;
    public int port;

    public BCabinetInfo() {
        this.addr = "";
    }

    public BCabinetInfo(byte cabId, String addr, int port) {
        this.cabId = cabId;
        this.addr = addr;
        this.port = port;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("cabId:").append(cabId);
        sb.append(",name:").append(addr);
        sb.append(",port:").append(port);
        sb.append("}");
        return sb.toString();
    }

    public void marshal(common.marshal.Octets bs) {
        bs.writeByte(cabId);
        bs.writeString(addr);
        bs.writeInt(port);
    }

    public void unmarshal(common.marshal.Octets bs) {
        cabId = bs.readByte();
        addr = bs.readString();
        port = bs.readInt();
    }
}
