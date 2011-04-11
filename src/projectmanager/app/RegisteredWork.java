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
}
