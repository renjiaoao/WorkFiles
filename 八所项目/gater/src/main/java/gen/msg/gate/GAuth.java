package gen.msg.gate;

public final class GAuth extends xio.Protocol {

	public final static int TYPEID = 101;
	final public int getTypeId() { return TYPEID; }

	public String username;
	public String password;

	public GAuth() {
		this.username = "";
		this.password = "";
	}
	public GAuth(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( this.getClass().getName()).append("{");
		sb.append(",username:").append(username);
		sb.append(",password:").append(password);
		sb.append("}");
		return sb.toString();
	}

	public void marshal(common.marshal.Octets bs) {
		bs.writeString(username);
		bs.writeString(password);
	}

	public void unmarshal(common.marshal.Octets bs) {
		username = bs.readString();
		password = bs.readString();
	}

	public GAuth newObject() { return new GAuth(); }
}