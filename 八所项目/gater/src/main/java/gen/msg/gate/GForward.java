package gen.msg.gate;

public final class GForward extends xio.Protocol {

	public final static int TYPEID = 106;
	final public int getTypeId() { return TYPEID; }

	public byte cabId;
	public common.marshal.Octets data;

	public GForward() {
		this.cabId = (byte)0;
		this.data = new common.marshal.Octets();
	}

	public GForward(byte cabId, common.marshal.Octets data) {
		this.cabId = cabId;
		this.data = data;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( this.getClass().getName()).append("{");
		sb.append(",cabId:").append(cabId);
		sb.append(",data:").append(data);
		sb.append("}");
		return sb.toString();
	}

	public void marshal(common.marshal.Octets bs) {
		bs.writeByte(cabId);
		bs.writeOctets(data);
	}

	public void unmarshal(common.marshal.Octets bs) {
		cabId = bs.readByte();
		data = bs.readOctets();
	}

	public GForward newObject() { return new GForward(); }
}