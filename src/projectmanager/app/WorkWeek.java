package projectmanager.app;

import java.util.ArrayList;

public class WorkWeek {
	private Employee employee;
	private int weekNumber;
	private int year;
	private ArrayList<DelegatedWork> delegatedWork   = new ArrayList<DelegatedWork>();
	private ArrayList<RegisteredWork> registeredWork = new ArrayList<RegisteredWork>();
	public WorkWeek(Employee employee, int weekNumber, int year) {
		this.employee   = employee;
		this.weekNumber = weekNumber;
		this.year       = year;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public int getWeekNumber() {
		return weekNumber;
	}
	public int getYear() {
		return year;
	}
	public void setTime(int weekNumber, int year) {
		this.weekNumber = weekNumber;
		this.year       = year;
	}
	public ArrayList<DelegatedWork> getDelegatedWork() {
		return delegatedWork;
	}
	public void setDelegatedWork(ArrayList<DelegatedWork> delegatedWork) {
		this.delegatedWork = delegatedWork;
	}
	public ArrayList<RegisteredWork> getRegisteredWork() {
		return registeredWork;
	}
	public void setRegisteredWork(ArrayList<RegisteredWork> registeredWork) {
		this.registeredWork = registeredWork;
	}
	public void addDelegatedWork(DelegatedWork work){
		// TODO: What if it's already in the list?
		this.delegatedWork.add(work);
	}
	public void addRegisteredWork(RegisteredWork work){
		// TODO: What if it's already in the list?
		this.registeredWork.add(work);
	}
	
	public int getDelegatedHours(){
		int total = 0;
		for(DelegatedWork work: this.delegatedWork){
			total += work.getHours();
		}
		return total;
	}
	
//	public int getRegisteredHours(){
//		int total = 0;
//		for(RegisteredWork work: this.registeredWork){
//			total += work.getHours();
//		}
//		return total;
//	}
}
