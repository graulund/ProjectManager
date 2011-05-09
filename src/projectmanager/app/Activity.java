package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
/**
 * A class that defines an activity.
 * An activity is a part of a project. It contains a list of employees assigned to it, and lists of the work they do.
 */
public class Activity {
	/**
	 * Name of activity.
	 */
	private String name;
	/**
	 * List of employees assigned to the activity.
	 */
	private List<Employee> employees = new ArrayList<Employee>();
	/**
	 * List of delegated work.
	 */
	private List<DelegatedWork> del_works = new ArrayList<DelegatedWork>();
	/**
	 * List of registered work.
	 */
	private List<RegisteredWork> reg_works = new ArrayList<RegisteredWork>();
	/**
	 * Start of activity.
	 */
	private Calendar start;
	/**
	 * End of activity.
	 */
	private Calendar end;
	
	/**
	 * Constructor.
	 * @param name
	 */
	public Activity(String name){
		this.setName(name);
	}
	/**
	 * Adds employee to activity.
	 * @param employee
	 */
	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}
	/**
	 * Removes employee from activity.
	 * @param employee
	 */
	public void removeEmployee(Employee employee) {
		this.employees.remove(employee);
	}
	/**
	 * Returns a list of employees assigned to activity.
	 * @return
	 */
	public List<Employee> getEmployees() {
		return employees;
	}
	/**
	 * Overwrites the list of employees.
	 * @param employees
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	/**
	 * Adds registered work to activity.
	 * @param rw
	 */
	public void addRegisteredWork(RegisteredWork rw) {
		this.reg_works.add(rw);
	}
	/**
	 * Adds delegated work to activity.
	 * @param dw
	 */
	public void addDelegatedWork(DelegatedWork dw) {
		this.del_works.add(dw);
	}
	/**
	 * Adds any kind of work to activity.
	 * @param w
	 */
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
	/**
	 * Sets the name of activity.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets name of activity.
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets start date on activity.
	 * @param week
	 * @param year
	 * @throws ProjectManagerException
	 */
	public void setStart(int week, int year) throws ProjectManagerException {
		if (this.end != null) {
			if (this.end.get(Calendar.YEAR) == year && this.end.get(Calendar.WEEK_OF_YEAR) < week) {
				throw new CreatingActivityException("Den givne tid er invalid.");
			} else if (this.end.get(Calendar.YEAR) < year) {
				throw new CreatingActivityException("Den givne tid er invalid.");
			}
		}
		this.start = new GregorianCalendar();
		this.start.set(Calendar.YEAR, year);
		this.start.set(Calendar.WEEK_OF_YEAR, week);
	}
	/**
	 * Sets end date on activity.
	 * @param week
	 * @param year
	 * @throws ProjectManagerException
	 */
	public void setEnd(int week, int year) throws ProjectManagerException {
		if (this.start != null) {
			if (this.start.get(Calendar.YEAR) == year && this.start.get(Calendar.WEEK_OF_YEAR) > week) {
				throw new CreatingActivityException("Den givne tid er invalid.");
			} else if (this.start.get(Calendar.YEAR) > year) {
				throw new CreatingActivityException("Den givne tid er invalid.");
			}	
		} 
		this.end = new GregorianCalendar();
		this.end.set(Calendar.YEAR, year);
		this.end.set(Calendar.WEEK_OF_YEAR, week);	
			
	}
	/**
	 * Sets start date on activity.
	 * @param start
	 */
	public void setStart(Calendar start) {
		this.start = start;
	}
	/**
	 * Sets end date on activity.
	 * @param end
	 */
	public void setEnd(Calendar end) {
		this.end = end;
	}
	/**
	 * Returns start date.
	 * @return
	 */
	public Calendar getStart() {
		return this.start;
	}
	/**
	 * Returns end date.
	 * @return
	 */
	public Calendar getEnd() {
		return this.end;
	}
	/**
	 * Returns number of delegated hours assigned to activity.
	 * @return
	 */
	public int getDelegatedHours() {
		int total = 0;
		for (DelegatedWork del_work: this.del_works) {
			total += del_work.getHalfHoursWorked();
		}
		return total;
	}
	/**
	 * Returns number of registered hours on activity.
	 * @return
	 */
	public double getRegisteredHours() {
		double total = 0;
		for (RegisteredWork reg_work: this.reg_works) {
			total += reg_work.getHalfHoursWorked();
		}
		return total;
	}
	/**
	 * Returns the list of delegated work.
	 * @return
	 */
	public List<DelegatedWork> getDelegatedWork() {
		return del_works;
	}
	/**
	 * Sets the list of delegated work.
	 * @param del_works
	 */
	public void setDelegatedWork(List<DelegatedWork> del_works) {
		this.del_works = del_works;
	}
	/**
	 * Returns the list of registered work.
	 * @return
	 */
	public List<RegisteredWork> getRegisteredWork() {
		return reg_works;
	}
	/**
	 * Sets the list of registered work.
	 * @param reg_works
	 */
	public void setRegisteredWork(List<RegisteredWork> reg_works) {
		this.reg_works = reg_works;
	}
	/**
	 * Gets a list of all Work objects.
	 * @return
	 */
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
