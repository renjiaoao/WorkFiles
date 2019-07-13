package util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public final class Time {
	public interface TimeListener {
		public void setCurrentTime(long time);
	}
	
	public static final int HOUR_SECONDS = 3600;                 // 1小时的秒数
	public static final int MINUTE_SECONDS = 60;                 // 1分钟的秒数
	public static final int SECOND_MILLISECONDS = 1000;	 	 	 // 1秒钟的毫秒
	public static final int MINUTE_MILLISECONDS = 60*1000;	 	 // 1分钟的毫秒
	public static final int HOUR_MILLISECONDS = 60*60*1000;	 	 // 1小时的毫秒
	public static final int DAY_MILLISECONDS = 24*60*60*1000; 	 // 1天的毫秒数
	public static final int WEEK_MILLISECONDS = 7*24*60*60*1000; // 1周的毫秒数
	public static final int START_MILLISECONDS = 8*60*60*1000;	  // 8小时的毫秒
	
	private static long offset = 0;
	private static List<TimeListener> listeners = new ArrayList<TimeListener>();
	
	public static void addTimeListener(TimeListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public static long realSystemTime() {
		return System.currentTimeMillis() ;
	}
	
	public static void setSystemTime(long time) {
		setTimeOffset((int)(time - realSystemTime()));
	}
	
	public static void resetSystemTime() {
		setTimeOffset(0);
	}
	
	public static long currentTime() {
		return realSystemTime() + offset;
	}
	
	public static int currentSeconds() {
		return (int)(currentTime() / SECOND_MILLISECONDS);
	}
	
	public static Calendar currentCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currentTime());
		return calendar;
	}
	
	public static Date currentDate() {
		return new java.util.Date(currentTime());
	}

	public static int currentYear() {
		return currentCalendar().get(Calendar.YEAR);
	}
	
	public static int currentMonth() {
		return currentCalendar().get(Calendar.MONTH) + 1;
	}
	
	public static int currentDay() {
		return currentCalendar().get(Calendar.DAY_OF_MONTH);
	}

	public static int currentHour() {
		return currentCalendar().get(Calendar.HOUR_OF_DAY);
	}

	public static int currentMinute() {
		return currentCalendar().get(Calendar.MINUTE);
	}

	public static int currentSecond() {
		return currentCalendar().get(Calendar.SECOND);
	}
	
	public static int currentMillisecond() {
		return currentCalendar().get(Calendar.MILLISECOND);
	}
	
	// Calendar 中的星期几与数字对应关系为:
	// 星期一(2)、星期二(3)、星期三(4)、星期四(5)、星期五(6)、星期六(7)、星期天(1)
	// 而我们这里使用的星期几对应的就是几
	public static int getDayOfWeek(long time) {
		Calendar cal = currentCalendar();
		int day = cal.get(Calendar.DAY_OF_WEEK);
		return day >= 2 ? day - 1 : 7;
	}
	
	public static int getLastDayOfWeek() {
		int day = getDayOfWeek(currentTime());
		return day >= 2 ? day - 1 : 7;
	}
	
	public static int getDayOfMonth() {
		Calendar cal = currentCalendar();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getYearAfterTime(long time) {
		Calendar now = currentCalendar();
		Calendar cal = currentCalendar();
		cal.setTimeInMillis(time);
		
		int year = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
		if (now.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)) {
			year -= 1;
		}
		return year;
	}
	
	// 物理天，超过凌晨算1天
	public static int getDayAfterTime(long time) {
		long now = currentTime();
		if (time < now) {
			return (int) ((now - getStartTimeInDay(time)) / DAY_MILLISECONDS);
		} else {
			return (int) ((getStartTimeInDay(now) - time) / DAY_MILLISECONDS);
		}
	}
	
	public static int getHourAfterTime(long time) {
		return (int) ((currentTime() - time) / HOUR_MILLISECONDS);		
	}
	
	public static int getMinuteAfterTime(long time) {
		return (int) ((currentTime() - time) / MINUTE_MILLISECONDS);
	}
	
	public static int getSecondAfterTime(long time) {
		return (int)((currentTime() - time) / SECOND_MILLISECONDS);
	}
	
	public static long getTimeBeforeDay(int day) {
		 Calendar calendar = currentCalendar(); 
		 calendar.add(Calendar.DAY_OF_YEAR, -day);
		 calendar.set(Calendar.HOUR_OF_DAY, 0);
		 calendar.set(Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 calendar.set(Calendar.MILLISECOND, 0);
		 return calendar.getTimeInMillis();
	}
	
	public static boolean isSameWeek(long time1, long time2) {
		Calendar c1 = currentCalendar();
		Calendar c2 = currentCalendar();
		c1.setTimeInMillis(time1 - DAY_MILLISECONDS);
		c2.setTimeInMillis(time2 - DAY_MILLISECONDS);
		
		return time1 > 0 && time2 > 0 && 
				c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.WEEK_OF_YEAR) == c2.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static boolean isSameDay(long time1, long time2) {
		Calendar c1 = currentCalendar();
		Calendar c2 = currentCalendar();
		c1.setTimeInMillis(time1);
		c2.setTimeInMillis(time2);

		return time1 > 0 && time2 > 0 && 
				c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
	}
	
	public static boolean isSameHour(long time1, long time2) {
		Calendar c1 = currentCalendar();
		Calendar c2 = currentCalendar();
		c1.setTimeInMillis(time1);
		c2.setTimeInMillis(time2);
		
		return time1 > 0 && time2 > 0 && 
				c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
				&& c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
				&& c1.get(Calendar.HOUR_OF_DAY) == c2.get(Calendar.HOUR_OF_DAY);
	}
	
	public static boolean overAnyHour(long time1, Set<Integer> hours) {		
		Calendar calendar = currentCalendar();
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		long now = currentTime();
		for (Integer hour : hours) {
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			if (time1 < calendar.getTimeInMillis() 
				&& now >= calendar.getTimeInMillis()) {
				return true;
			}
		}
		return false;
	}
	
	public static long getStartTimeToday() {
		Calendar calendar = currentCalendar(); 
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	public static long getEndTimeToday() {
		Calendar calendar = currentCalendar(); 
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	public static long getStartTimeInDay(long time) {
		Calendar calendar = currentCalendar(); 
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	public static long getEndTimeInDay(long time) {
		Calendar calendar = currentCalendar(); 
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	public static long getStartTimeInMonth(int year, int month) {
		Calendar calendar = currentCalendar(); 
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	public static long getEndTimeInMonth(int year, int month) {
		Calendar calendar = currentCalendar(); 
		calendar.set(Calendar.YEAR, month == 12 ? year+1 : year);
		calendar.set(Calendar.MONTH, month == 12 ? 0 : month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() - SECOND_MILLISECONDS;
	}
	
	public static int getPassTimeInDay(long startTime) {
		long now = currentTime();
		return (int)(now - Math.max(getStartTimeInDay(now), startTime));
	}	
	
	private static void setTimeOffset(int _offset) {
		if (offset != _offset) {
	
			offset = _offset;
			for (TimeListener listener : listeners) {
				listener.setCurrentTime(currentTime());
			}
		}
	}
}

