package projectmanager.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegisteredWork {
	private Employee employee; // working employee
	private Activity activity; // work done on this activity
	private Calendar date = new GregorianCalendar(); // default date = today!
	private int halfHoursWorked;
	
	public RegisteredWork(Employee employee, Activity activity, int halfHoursWorked) {
		this.employee = employee;
		this.activity = activity;
		this.halfHoursWorked = halfHoursWorked;
		this.activity.addRegisteredWork(this);
	}
	
	public RegisteredWork(Employee employee, Activity activity, int halfHoursWorked, GregorianCalendar date) {
		this(employee, activity, halfHoursWorked);
		this.date = date;
	}
	
	public RegisteredWork(Activity activity, String date, String startTime, String endTime) {
		this.activity = activity;
		this.halfHoursWorked = countHalfHoursWorked(date, startTime, endTime);
		this.date = dateFromInput(date);
	}
	
	public Calendar dateFromInput(String date) {
		String[] dateSplit = date.split("\\.");
		Calendar calendar = new GregorianCalendar();
		calendar.set(Integer.parseInt(dateSplit[2]), 
				  Integer.parseInt(dateSplit[1]), 
				  Integer.parseInt(dateSplit[0]));
		
		return calendar;
	}
	
	public int countHalfHoursWorked(String date, String startTime, String endTime) {
		// splits the input to readable characters
		String[] dateSplit = date.split("\\.");
		String[] startTimeSplit = startTime.split(":");
		String[] endTimeSplit = endTime.split(":");
		
		// saves the inputs in calendars
		GregorianCalendar startCalendar = new GregorianCalendar();
		startCalendar.set(Integer.parseInt(dateSplit[2]), 
						  Integer.parseInt(dateSplit[1]), 
						  Integer.parseInt(dateSplit[0]), 
						  Integer.parseInt(startTimeSplit[0]), 
						  Integer.parseInt(startTimeSplit[1])); 
		GregorianCalendar endCalendar = new GregorianCalendar();
		endCalendar.set(Integer.parseInt(dateSplit[2]), 
						Integer.parseInt(dateSplit[1]), 
						Integer.parseInt(dateSplit[0]), 
						Integer.parseInt(endTimeSplit[0]), 
						Integer.parseInt(endTimeSplit[1]));
		
        // Calculate difference in milliseconds
        long diff = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
        
        // Calculate difference in Halfhours
        long diffHalfHours = (diff / (60 * 60 * 1000))*2;
        
        //return (int)diffHalfHours;
        return getMostHours(diffHalfHours, Integer.parseInt(startTimeSplit[1]), Integer.parseInt(endTimeSplit[1]));
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

	public int getHalfHoursWorked() {
		return this.halfHoursWorked;
	}
}
