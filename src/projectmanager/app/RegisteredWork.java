package projectmanager.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Registered Work is created when a employee has
 * registered that he has worked on an activity.
 */
public class RegisteredWork extends Work {
	/**
	 * The date of this registered work
	 */
	private Calendar date = new GregorianCalendar();
	
	/**
	 * The start-time of this registered work
	 */
	private Calendar startTime;
	
	/**
	 * The end-time of this registered work
	 */
	private Calendar endTime;
	
	/**
	 * The number of half hours on this registered work
	 */
	private int halfHoursWorked;
	
	/**
	 * Constructor of Registered Work from a given startTime and endTime		
	 * @param activity
	 * @param startCalendar
	 * @param endCalendar
	 * @param serialNumber
	 */
	public RegisteredWork(Activity activity, Calendar startCalendar, Calendar endCalendar, int serialNumber){
		this.serialNumber = serialNumber;
		this.activity = activity;
		this.startTime = startCalendar;
		this.endTime = endCalendar;
		this.date = setDate(startCalendar);
		this.halfHoursWorked = countHalfHoursWorked(startCalendar, endCalendar);
		this.activity.addRegisteredWork(this);
	}
	
	/**
	 * Constructor of registered work where the serialnumber is automaticly given
	 * @param activity
	 * @param startCalendar
	 * @param endCalendar
	 */
	public RegisteredWork(Activity activity, Calendar startCalendar, Calendar endCalendar) {
		this(activity, startCalendar, endCalendar, ProjectManagerApp.newSerialNumber());
	}
	
	/**
	 * Constructor of Registered work where the date is a standard-date (1. january 200)
	 * @param activity
	 * @param halfHoursWorked
	 * @param serialNumber
	 */
	public RegisteredWork(Activity activity, int halfHoursWorked, int serialNumber){
		this.serialNumber = serialNumber;
		this.activity = activity;
		int hours   = halfHoursWorked / 2;
		int minutes = (halfHoursWorked % 2) * 30;
		Calendar startCalendar = new GregorianCalendar();
		Calendar endCalendar = new GregorianCalendar();
		startCalendar.set(2000, 0, 1, 0, 0, 0);
		endCalendar.set(2000, 0, 1, hours, minutes, 0);
		this.startTime = startCalendar;
		this.endTime = endCalendar;
		this.date = setDate(startCalendar);
		this.halfHoursWorked = halfHoursWorked;
		this.activity.addRegisteredWork(this);
	}
	
	/**
	 * Constructor of Registered work where the date is a standard-date (1. january 200)
	 * @param activity
	 * @param halfHoursWorked
	 */
	public RegisteredWork(Activity activity, int halfHoursWorked) {
		this(activity, halfHoursWorked, ProjectManagerApp.newSerialNumber());
	}
	
	public void updateHalfHoursWorked() {
		this.halfHoursWorked = countHalfHoursWorked(this.startTime, this.endTime);
	}
	
	/**
	 * Method that corrects the half hours so that they are fair
	 * @param diffHalfHours
	 * @param startMinutes
	 * @param endMinutes
	 * @return
	 */
	private int getFairHours(long diffHalfHours, int startMinutes, int endMinutes) {
		int halfHours = (int) diffHalfHours;
		if (startMinutes >= 15 && startMinutes < 45) {
			if (endMinutes < 15 || endMinutes >= 45) halfHours++;
		} else if (startMinutes < 15 || startMinutes >= 45) {
			if (endMinutes >= 15 && endMinutes < 45) halfHours++;
		}
		return halfHours;
	}
	
	/**
	 * Sets the start time 
	 * @param hour
	 * @param minutes
	 */
	public void setStartTime(int hour, int minutes) {
		this.startTime.set(Calendar.HOUR_OF_DAY, hour);
		this.startTime.set(Calendar.MINUTE, minutes);
	}
	
	/**
	 * Sets the end time 
	 * @param hour
	 * @param minutes
	 */
	public void setEndTime(int hour, int minutes) {
		this.endTime.set(Calendar.HOUR_OF_DAY, hour);
		this.endTime.set(Calendar.MINUTE, minutes);
	}
	
	/**
	 * Returns the date 
	 * @return the date
	 */
	public Calendar getDate() {
		return this.date;
	}

	/**
	 * Returns the activity
	 * @return activity
	 */
	public Activity getActivity() {
		return this.activity;
	}

	/**
	 * Returns half hours worked registered
	 * @return half hours worked
	 */
	public int getHalfHoursWorked() {
		return this.halfHoursWorked;
	}
	
	/**
	 * Returns the start time 
	 * @return start time
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Returns the end time
	 * @return end time
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}
	
	/**
	 * Sets the serialnumber
	 * @param serialnumber
	 */
	public void setSerialNumber(int serialKey) {
		this.serialNumber = serialKey;
	}
	
	/**
	 * Returns the serialnumber
	 * @return
	 */
	public int getSerialNumber() {
		return this.serialNumber;
	}
	
	/**
	 * Returns this registered work in a string-form	
	 */
	public String toString() {
		Calendar date     = this.getDate();
		String dateString = date.get(Calendar.DATE)+"/"+(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.YEAR);
		String timeFrom   = this.getStartTime().get(Calendar.HOUR_OF_DAY)+":"+this.getStartTime().get(Calendar.MINUTE);
		String timeTo     = this.getEndTime().get(Calendar.HOUR_OF_DAY)+":"+this.getEndTime().get(Calendar.MINUTE);
		return "Activity: "+this.activity.getName()+"; Date: "+dateString+"; Time: "+timeFrom+" - "+timeTo;
	}
	
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
        return getFairHours(diffHalfHours, startTime.get(Calendar.MINUTE), endTime.get(Calendar.MINUTE));
	}
}
