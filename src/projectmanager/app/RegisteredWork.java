package projectmanager.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegisteredWork {
	private Activity activity; // work done on this activity
	private Calendar date = new GregorianCalendar(); // default date = today!
	private Calendar startTime;
	private Calendar endTime;
	private int halfHoursWorked;
	
	public RegisteredWork(Activity activity, Calendar startCalendar1, Calendar endCalendar1) {
		this.activity = activity;
		this.startTime = startCalendar1;
		this.endTime = endCalendar1;
		this.date = setDate(startCalendar1);
		this.halfHoursWorked = countHalfHoursWorked(startCalendar1, endCalendar1);
		this.activity.addRegisteredWork(this);
	}
	
	public RegisteredWork(Activity activity, int halfHoursWorked) {
		this.activity = activity;
		this.halfHoursWorked = halfHoursWorked;
		this.activity.addRegisteredWork(this);
	}
	/*
	public RegisteredWork(Employee employee, Activity activity, int halfHoursWorked, GregorianCalendar date) {
		this(employee, activity, halfHoursWorked);
		this.date = date;
	}
	
	public RegisteredWork(Activity activity, String date, String startTime, String endTime) {
		this.activity = activity;
		this.halfHoursWorked = countHalfHoursWorked(date, startTime, endTime);
		this.date = dateFromInput(date);
	}*/
	
	private Calendar setDate(Calendar date) {
		Calendar calendar = new GregorianCalendar();
		calendar.set(date.get(Calendar.YEAR), 
				     date.get(Calendar.MONTH), 
				     date.get(Calendar.DATE));
		return calendar;
	}
	
	private int countHalfHoursWorked(Calendar startTime, Calendar endTime) {
		// Calculate difference in milliseconds
        long diff = endTime.getTimeInMillis() - startTime.getTimeInMillis();
        
        // Calculate difference in Halfhours
        long diffHalfHours = (diff / (60 * 60 * 1000))*2;
        
        //return (int)diffHalfHours;
        return getMostHours(diffHalfHours, startTime.get(Calendar.MINUTE), endTime.get(Calendar.MINUTE));
	}
	/**
	 * Method that corrects the half hours so that they are fair
	 * @param diffHalfHours
	 * @param startMinutes
	 * @param endMinutes
	 * @return
	 */
	private int getMostHours(long diffHalfHours, int startMinutes, int endMinutes) {
		int halfHours = (int)diffHalfHours;
		if (startMinutes > 0 && startMinutes <= 15) {
			halfHours+=2;
		} else if (startMinutes >= 15 && startMinutes < 45) {
			halfHours++;;
		}
		if (endMinutes >= 15 && endMinutes < 45) {
			halfHours++;
		} else if (endMinutes >= 45) {
			halfHours+=2;
		}
		return halfHours;
	}

	public int getMostHours(int minuteInput) {
		if (minuteInput == 30) {
			return 31;
		} else {
			return minuteInput;
		}
	}
	
	public Calendar getDate() {
		return this.date;
	}

	public Activity getActivity() {
		return this.activity;
	}

	public double getHoursWorked() {
		return (double) this.halfHoursWorked/2;
	}

	public Calendar getStartTime() {
		return this.startTime;
	}

	public Calendar getEndTime() {
		return this.endTime;
	}
}
