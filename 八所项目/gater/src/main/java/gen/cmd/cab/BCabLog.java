package gen.cmd.cab;

import common.marshal.IMarshal;
import common.marshal.MarshalException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BCabLog implements IMarshal {

	public static final byte DEV_TYPE_DOOR = 1;
	public static final byte DEV_TYPE_RFID1 = 1; // 1 ~ 31
	
	public static final byte DEV_STATUS_NOUSE 				= 0; // 未用
	public static final byte DEV_STATUS_EXIST 				= 1; // 存在
	public static final byte DEV_STATUS_TAKEAWAY 			= 2; // 取走
	public static final byte DEV_STATUS_GETOUT 				= 3; // 外出
	 
	public static final byte DEV_STATUS_VOLTAGE_LITTER	 	= 4; // 欠压
	public static final byte DEV_STATUS_VOLTAGE_NORMAL 		= 5; // 正常
	public static final byte DEV_STATUS_AUTH_CHANGE 		= 6; // 授权更换
	public static final byte DEV_STATUS_CHANGE_RETURN 		= 7; // 更换归还
	
	public static final byte DEV_STATUS_AUTH_GETOUT			= 8; // 授权外出
	public static final byte DEV_STATUS_APP_CONNECT 		= 9; // APP连接

		
	 public byte cabId;          // 柜子编号
	 public byte doorNumber;     // 柜门编号
	 public short fingerCode;    // 指纹代码
	 public byte devType;        // 设备类型
	 public byte devStatus;      // 设备状态
	 public long logDate;        // 记录日期


    public BCabLog() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( this.getClass().getName()).append("{");
        sb.append(",cabId:").append(cabId);
        sb.append(",doorNumber:").append(doorNumber);
        sb.append(",fingerCode:").append(fingerCode);
        sb.append(",devType:").append(devType);
        sb.append(",devStatus:").append(devStatus);
        sb.append(",logDate:").append(logDate);
        sb.append("}");
        return sb.toString();
    }

    public void marshal(common.marshal.Octets bs) {
        bs.writeByte(cabId);
        bs.writeByte(doorNumber);
        bs.writeFixShort(fingerCode);
        bs.writeByte(devType);
        bs.writeByte(devStatus);
        bs.writeLong(logDate);
    }

    public void unmarshal(common.marshal.Octets bs) throws MarshalException {
        byte[] bytes = new byte[9];
        for (int i=0; i<bytes.length; i++) {
            bytes[i] = bs.readByte();
        }
        
        cabId = (byte)((bytes[0] & 0x40) << 1 | (bytes[6] & 0x7F));
        doorNumber = (byte)((bytes[7] & 0xF8) >> 3);
        fingerCode = (short)(((bytes[4] & 0x40) >> 3 | (bytes[3] & 0x07)) << 8 | ((bytes[5] & 0x40) << 1 | (bytes[8] & 0x7F)));

        devType = (byte)((bytes[2] & 0x60) >> 2 | (bytes[7] & 0x07));
        devStatus = (byte)(bytes[1] & 0x0F);

        byte yy = (byte)(bytes[0] & 0x3F);
        byte mm = (byte)((bytes[1] & 0xF0) >> 4);;
        byte dd = (byte)(bytes[2] & 0x1F);
        byte hh = (byte)((bytes[3] & 0xF8) >> 3);
        byte ff = (byte)(bytes[4] & 0x3F);
        byte ss = (byte)(bytes[5] & 0x3F);

        try {
            if (yy == 0 && mm == 0 && dd == 0 && hh == 0 && ff == 0 && ss == 0) {
                logDate = 0;
            } else {
                String date = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                        yy + 2000, mm, dd, hh, ff, ss);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                logDate = sdf.parse(date).getTime();

            }
        } catch (ParseException e) {
            throw new MarshalException(e.getMessage());
        }
    }

    public boolean isZeroMemory() {
        return cabId == 0
                && doorNumber == 0
                && fingerCode == 0
                && devType == 0
                && devStatus == 0
                && logDate == 0;
    }
}
