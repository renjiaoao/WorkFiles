package gen.msg.gate;

public final class SAuth extends xio.Protocol {

	public final static int TYPEID = 102;
	final public int getTypeId() { return TYPEID; }

	public int errcode;

	public SAuth() {
		this.errcode = 0;
	}
	public SAuth(int errcode) {
		this.errcode = errcode;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( this.getClass().getName()).append("{");
		sb.append(",errcode:").append(errcode);
		sb.append("}");
		return sb.toString();
	}

	public void marshal(common.marshal.Octets bs) {
		bs.writeInt(errcode);
	}

	public void unmarshal(common.marshal.Octets bs) {
		errcode = bs.readInt();
	}

	public SAuth newObject() { return new SAuth(); }
}