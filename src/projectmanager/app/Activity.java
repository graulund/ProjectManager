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
	
	public void addDelegatedWork(DelegatedWork dw) {
		this.del_works.add(dw);
	}
	
	public void addWork(Work w){
		if(w != null){
			if(w.getClass() == RegisteredWork.class){
				this.addRegisteredWork((RegisteredWork) w);
			}
			if(w.getClass() == DelegatedWork.class){
				this.addDelegatedWork((DelegatedWork) w);
			}
		}
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

	public void setStart(Calendar start) {
		this.start = start;
	}

	public void setEnd(Calendar end) {
		this.end = end;
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
			total += del_work.getHalfHoursWorked();
		}
		return total;
	}
	
	public double getRegisteredHours() {
		double total = 0;
		for (RegisteredWork reg_work: this.reg_works) {
			total += reg_work.getHalfHoursWorked();
		}
		return total;
	}

	public List<DelegatedWork> getDelegatedWork() {
		return del_works;
	}

	public void setDelegatedWork(List<DelegatedWork> del_works) {
		this.del_works = del_works;
	}

	public List<RegisteredWork> getRegisteredWork() {
		return reg_works;
	}

	public void setRegisteredWork(List<RegisteredWork> reg_works) {
		this.reg_works = reg_works;
	}
	
	public List<Work> getWork(){
		List<Work> works = new ArrayList<Work>();
		for(DelegatedWork w: this.del_works){
			works.add(w);
		}
		for(RegisteredWork w: this.reg_works){
			works.add(w);
		}
		return works;
	}
}
