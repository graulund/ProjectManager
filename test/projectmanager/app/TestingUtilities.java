package projectmanager.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TestingUtilities {
	
	public static Calendar createCalendar(int year, int week, int hour, int minutes) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.WEEK_OF_YEAR, week);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, 0);
		return c;
	}
	
	public static Calendar createCalendar(int year, int month, int date, int hour, int minutes) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.DATE, date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, 0);
		return c;
	}
}
