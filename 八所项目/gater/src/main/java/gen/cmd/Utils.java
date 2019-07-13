package gen.cmd;

import common.marshal.MarshalException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class Utils {

    public static void marshalDate(long time, common.marshal.Octets bs) throws MarshalException {
        Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
  
        bs.writeByte((byte)(calendar.get(Calendar.YEAR) - 2000));
        bs.writeByte((byte)(calendar.get(Calendar.MONTH)+1));
        bs.writeByte((byte)calendar.get(Calendar.DATE));
        bs.writeByte((byte)calendar.get(Calendar.HOUR_OF_DAY));
        bs.writeByte((byte)calendar.get(Calendar.MINUTE));
        bs.writeByte((byte)calendar.get(Calendar.SECOND));
    }

    public static long unmarshalDate(common.marshal.Octets bs) throws MarshalException {
        try {
            int[] bytes = new int[6];
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = bs.readByte();
            }

            if(bytes[0]<0 || bytes[0]>999 || bytes[1] < 0 || bytes[1] > 12
            		|| bytes[2] < 0 || bytes[2] > 31 || bytes[3] < 0 || bytes[3] > 24 || 
            		bytes[4] < 0 || bytes[4] > 59 || bytes[5] < 0 || bytes[5] > 59) {
            	String message = String.format("unmarshal Date Error : year=%d, month=%d, date=%d, hour=%d, minute=%d, second=%d",
            			bytes[0], bytes[1], bytes[2], bytes[3], bytes[4], bytes[5]);
            	throw new MarshalException(message);
            }
            
            String date = String.format("%04d-%02d-%02d %02d:%02d:%02d",
                    bytes[0]+2000, bytes[1], bytes[2], bytes[3], bytes[4], bytes[5]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(date).getTime();
        } catch (MarshalException | ParseException e) {
            throw new MarshalException(e.getMessage());
        }
    }


}
