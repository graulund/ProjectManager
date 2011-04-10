package projectmanager;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class RegisteredWork {
	private Employee employee; // owner of object
	private Calendar date = new GregorianCalendar(); // default date = today!
	
	
	public RegisteredWork(Employee employee) {
		this.employee = employee;
	}
	
	public RegisteredWork(Employee employee, GregorianCalendar date) {
		this(employee);
		this.date = date;
	}
}
