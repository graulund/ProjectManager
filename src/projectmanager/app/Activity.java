package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Activity {
	private String name;
	private List<Employee> employees = new ArrayList<Employee>();
	private List<DelegatedWork> del_works = new ArrayList<DelegatedWork>();
	private List<RegisteredWork> reg_works = new ArrayList<RegisteredWork>();
	private Calendar start;
	private Calendar end;
	
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
	
	public int getDelegatedHours() {
		int total = 0;
		for (DelegatedWork del_work: this.del_works) {
			total += del_work.getHours();
		}
		return total;
	}
	
	public double getRegisteredHours() {
		double total = 0;
		for (RegisteredWork reg_work: this.reg_works) {
			total += reg_work.getHoursWorked();
		}
		return total;
	}
	
}
