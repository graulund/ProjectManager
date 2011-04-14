package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Activity {
	private String name;
	private List<Employee> employees = new ArrayList<Employee>();
	private List<RegisteredWork> reg_works = new ArrayList<RegisteredWork>();
	private Calendar start;
	private Calendar end;
	private int estimatedWorkHours;
	
	public Activity(String name){
		this.setName(name);
	}
	
	public void addEmployee(Employee employee) {
		this.employees.add(employee);
		employee.addActivity(this);
	}
	
	public void removeEmployee(Employee employee) {
		this.employees.remove(employee);
	}
	
	public void addRegisteredWork(RegisteredWork rw) {
		this.reg_works.add(rw);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setStart(int week) {
		this.start.set(Calendar.WEEK_OF_YEAR, week);
	}
	
	public void setEnd(int week) {
		this.start.set(Calendar.WEEK_OF_YEAR, week);
	}
	
	public Calendar getStart() {
		return start;
	}
	
	public Calendar getEnd() {
		return end;
	}
	
	public void setWorkHours(int hours) {
		estimatedWorkHours = hours;
	}
	
	public int getWorkHours() {
		return estimatedWorkHours;
	}
	
}
