package mongo;

public class DoorBleStatus {

    public byte fingerCode;     // 指纹代码
    public byte doorNumber;     // 柜门编号
    public byte doorStatus;     // 柜门状态
    
    public byte batteryStatus;  // 电池状态
    public byte isUsing;        // 使用标记
    public byte isPrieded;      // 撬开标记
    public byte isDisconnect;   // 是否断开
    public byte isOutAuthed;    // 是否授权外出
    public byte status;         // 状态
    
	public byte getFingerCode() {
		return fingerCode;
	}
	public void setFingerCode(byte fingerCode) {
		this.fingerCode = fingerCode;
	}
	public byte getDoorNumber() {
		return doorNumber;
	}
	public void setDoorNumber(byte doorNumber) {
		this.doorNumber = doorNumber;
	}
	public byte getDoorStatus() {
		return doorStatus;
	}
	public void setDoorStatus(byte doorStatus) {
		this.doorStatus = doorStatus;
	}
	public byte getBatteryStatus() {
		return batteryStatus;
	}
	public void setBatteryStatus(byte batteryStatus) {
		this.batteryStatus = batteryStatus;
	}
	public byte getIsUsing() {
		return isUsing;
	}
	public void setIsUsing(byte isUsing) {
		this.isUsing = isUsing;
	}
	public byte getIsPrieded() {
		return isPrieded;
	}
	public void setIsPrieded(byte isPrieded) {
		this.isPrieded = isPrieded;
	}
	public byte getIsDisconnect() {
		return isDisconnect;
	}
	public void setIsDisconnect(byte isDisconnect) {
		this.isDisconnect = isDisconnect;
	}
	public byte getIsOutAuthed() {
		return isOutAuthed;
	}
	public void setIsOutAuthed(byte isOutAuthed) {
		this.isOutAuthed = isOutAuthed;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
}
