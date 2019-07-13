package util;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtil {
	 
	public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String SHORT_DATE_FORMAT = "yyyy-MM-dd";
	public static String SHORT_TIME_FORMAT = "HH:mm:ss";
	
	private static DateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
	private static DateFormat shortDateFormat = new SimpleDateFormat(SHORT_DATE_FORMAT);
	private static DateFormat shortTimeFormat = new SimpleDateFormat(SHORT_TIME_FORMAT);
	

	public static String getShortDate() {
		return getShortDate(Time.currentTime());
	}
	
	public static String getShortTime() {
		return getShortTime(Time.currentTime());
	}
	public static String getDateTime() {
		return getDateTime(Time.currentTime());
	}
	
	public static String getShortDate(long time) {
		return shortDateFormat.format(time);
	}
	
	public static String getShortTime(long time) {
		return shortTimeFormat.format(time);
	}
	public static String getDateTime(long time) {
		return dateTimeFormat.format(time);
	}

	public static long parseShortDate(String time) throws ParseException {
		return shortDateFormat.parse(time).getTime();
	}
	
	public static long parseShortTime(String time) throws ParseException {
		return shortTimeFormat.parse(time).getTime();
	}
	public static long parseDateTime(String time) throws ParseException {
		return dateTimeFormat.parse(time).getTime();
	}
	
	public static String formatTime(long time) {
		return new SimpleDateFormat(SHORT_TIME_FORMAT).format(time - Time.START_MILLISECONDS);	
	}

	public static Date string2Date(String time) throws ParseException {
		return dateTimeFormat.parse(time);
	}
	
	public static String date2String(Date date) {
		return dateTimeFormat.format(date);
	}
	
	public static Long string2Long(String time) throws ParseException {
		return time.length() > 0 ? string2Date(time).getTime() : 0;
	}
	
	public static String long2String(Long time)	{
		return date2String(new Date(time));
	}
}