package gen.cmd.cab;

import common.marshal.IMarshal;
import common.marshal.Octets;

public class BBleStatus implements IMarshal {
    public byte batteryStatus;  // 电池状态
    public byte isUsing;        // 使用标记
    public byte isPrieded;      // 撬开标记
    public byte isDisconnect;   // 是否断开
    public byte isOutAuthed;    // 是否授权外出
    public byte status;         // 状态

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");    
	    sb.append(",batteryStatus:").append(batteryStatus);
	    sb.append(",isUsing:").append(isUsing);
	    sb.append(",isPrieded:").append(isPrieded);
	    sb.append(",isDisconnect:").append(isDisconnect);
	    sb.append(",isOutAuthed:").append(isOutAuthed);
	    sb.append(",status:").append(status);	    
	    return sb.toString();
    }

	public void marshal(common.marshal.Octets bs) {
        byte b = (byte)(((batteryStatus << 6) & 0xC0)
                | ((isUsing << 5) & 0x20)
                | ((isPrieded << 4) & 0x10)
                | ((isDisconnect << 3) & 0x08)
                | ((isOutAuthed << 2) & 0x04)
                | ((status << 0) & 0x03));
        bs.writeByte(b);
	}
	
	public void unmarshal(common.marshal.Octets bs) {
		byte b = bs.readByte();		
        batteryStatus = (byte)((b >> 6) & 0x03);
        isUsing = (byte)((b >> 5) & 0x01);
        isPrieded = (byte)((b >> 4) & 0x01);
        isDisconnect = (byte)((b >> 3) & 0x01);
        isOutAuthed = (byte)((b >> 2) & 0x01);
        status = (byte)((b >> 0) & 0x03);		
	}
}
