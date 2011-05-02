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
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
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
	
	public void setStart(int week, int year) {
		this.start = new GregorianCalendar();
		this.start.set(Calendar.YEAR, year);
		this.start.set(Calendar.WEEK_OF_YEAR, week);
	}
	
	public void setEnd(int week, int year) {
		this.end = new GregorianCalendar();
		this.end.set(Calendar.YEAR, year);
		this.end.set(Calendar.WEEK_OF_YEAR, week);
	}
	
	public Calendar getStart() {
		return this.start;
	}
	
	public Calendar getEnd() {
		return this.end;
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
