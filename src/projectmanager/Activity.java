package projectmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Activity {
	private String name;
	private Project project; // Cannot be changed after initialization
	private List<Employee> employees = new ArrayList<Employee>();
	private List<RegisteredWork> reg_works = new ArrayList<RegisteredWork>();
	
	private int start;  // week no. or calendar?
	private int end;    // -||-
	private int estimatedWorkHours;
	
	public Activity(String name, Project project){
		this.setName(name);
		this.project = project;
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
		this.start = week;
	}
	
	public void setEnd(int week) {
		this.end = week;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setWorkHours(int hours) {
		estimatedWorkHours = hours;
	}
	
	public int getWorkHours() {
		return estimatedWorkHours;
	}
	
}
