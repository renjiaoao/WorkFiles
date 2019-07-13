package gen.msg.gate;

import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;

public final class SSetCabinets extends xio.Protocol {

	public final static int TYPEID = 103;
	final public int getTypeId() { return TYPEID; }

	public List<BCabinetInfo> cabs;

	public SSetCabinets() {
		this.cabs = new ArrayList<>();
	}
	public SSetCabinets(List<BCabinetInfo> cabs) {
		this.cabs = cabs;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append( this.getClass().getName()).append("{");
		sb.append("size:").append(cabs.size()).append("{");
		for(BCabinetInfo cab : cabs) {
			sb.append(cab);
		}
		sb.append("}");
		return sb.toString();
	}

	public void marshal(common.marshal.Octets bs) {
		bs.writeCompactUint(cabs.size());
		for (BCabinetInfo _value : cabs) {
			_value.marshal(bs);
		}
	}

	public void unmarshal(common.marshal.Octets bs) {
		for (int n = bs.readCompactUint(); --n >= 0; ) {
			BCabinetInfo _value = new BCabinetInfo();
			_value.unmarshal(bs);
			cabs.add(_value);
		}
	}

	public SSetCabinets newObject() { return new SSetCabinets(); }
}