package gen.cmd.cab;

import gen.cmd.Command;
import xio.Protocol;

public class CClearLog extends Command {

	//EB 01 FF 39 00 01 FF
	

    public static final short CMDID = (short)0xFF39;
    
    public byte status;
    
	public CClearLog() {
		super((byte)0, CMDID);
		status = (byte)0x00;
	}
	
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\ttag:").append(tag);
        sb.append(",cabId:").append(cabId);
        sb.append(",cmd:").append(cmd);
        sb.append(",status").append(status);     
        sb.append("}");
        return sb.toString();
    }

	
	 public void marshal(common.marshal.Octets bs) {
	        super.marshal(bs);
	        bs.writeByte(status);	       
	    }

	    public void unmarshal(common.marshal.Octets bs) {
	        super.unmarshal(bs);	        
	        status = bs.readByte();
	    }
	    
	    
	    
	@Override
	public int getTypeId() {
		return CMDID;
	}

	@Override
	public Protocol newObject() {
		return new CClearLog();	
	}

}
