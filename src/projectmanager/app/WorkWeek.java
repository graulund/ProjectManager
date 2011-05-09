package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * WorkWeeks is attached to employees. A WorkWeek is
 * created when work is added to a weeknumber, which
 * doesn't have a related WorkWeek.
 */
public class WorkWeek {
	
	/**
	 * The weeknumber
	 */
	private int weekNumber;
	
	/**
	 * The year
	 */
	private int year;
	
	/**
	 * List of all the delegated work
	 */
	private List<DelegatedWork> delegatedWork   = new ArrayList<DelegatedWork>();
	
	/**
	 * List of all the registered work
	 */
	private List<RegisteredWork> registeredWork = new ArrayList<RegisteredWork>();
	
	/**
	 * WorkWeek constructor
	 * @param weekNumber
	 * @param year
	 */
	public WorkWeek(int weekNumber, int year) {
		this.weekNumber = weekNumber;
		this.year       = year;
	}
	
	/**
	 * Returns the weeknumber
	 * @return weeknumber
	 */
	public int getWeekNumber() {
		return weekNumber;
	}
	
	/**
	 * Returns the year
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Returns a list of all the delegated work
	 * @return list of delegated work
	 */
	public List<DelegatedWork> getDelegatedWork() {
		return delegatedWork;
	}
	
	/**
	 * Adds delegated work
	 * @param delegated work
	 */
	public void addDelegatedWork(DelegatedWork work){
		this.delegatedWork.add(work);
	}
	
	/**
	 * Adds registered work
	 * @param registered work
	 */
	public void addRegisteredWork(RegisteredWork work){
		this.registeredWork.add(work);
	}
	
	/**
	 * Adds work
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
	 * Returns the total number of delegated hours
	 * @return total number of delegated hours
	 */
	public int getDelegatedHours(){
		int total = 0;
		for(DelegatedWork work: this.delegatedWork){
			total += work.getHalfHoursWorked();
		}
		return total;
	}
	
	/**
	 * Returns the list of registered work
	 * @return list of registered work
	 */
	public List<RegisteredWork> getRegisteredWork() {
		return registeredWork;
	}
	
	/**
	 * Returns a list of registered work on a given date
	 * @param date
	 * @return list of registered work
	 */
	public List<RegisteredWork> getRegisteredWork(Calendar date) {
		List<RegisteredWork> regworks = new ArrayList<RegisteredWork>();
		for (RegisteredWork regwork: this.registeredWork) {
			if (regwork.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
				regwork.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
				regwork.getDate().get(Calendar.DATE) == date.get(Calendar.DATE)			
				) {
				regworks.add(regwork);
			}
		}
		return Collections.unmodifiableList(regworks);
	}
	
	/**
	 * Returns a given registered work
	 * @param regwork
	 * @return registered work
	 */
	public RegisteredWork getRegisteredWork(RegisteredWork regwork) {
		for (RegisteredWork r: this.registeredWork) {
			if (r == regwork) return r;
		}
		return null;
	}
	
	/**
	 * Returns a list of all the work
	 * @return list of work
	 */
	public List<Work> getWork(){
		List<Work> works = new ArrayList<Work>();
		for(DelegatedWork w: this.delegatedWork){
			works.add(w);
		}
		for(RegisteredWork w: this.registeredWork){
			works.add(w);
		}
		return works;
	}
	
	/**
	 * Returns a list of strings, where each string
	 * is a relation between registered hours and delegated hours
	 * for each activity
	 * @return list of strings with activity: registeredHours/delegatedHours
	 */
	public List<String> getWeekSchedule() {
		List<String> schedule = new ArrayList<String>();
		for (DelegatedWork dw: this.delegatedWork) {
			Activity currentActivity = dw.getActivity();
			int totalRegHours = 0;
			for (RegisteredWork rw: this.registeredWork) {
				if (rw.getActivity().equals(currentActivity)) {
					totalRegHours += rw.getHalfHoursWorked();
				}
			}
			totalRegHours /= 2;
			schedule.add(currentActivity.getName() + ": " + totalRegHours + "/" + dw.getHalfHoursWorked()/2);
		}
		return schedule;
	}
	
	/**
	 * Returns a registered work by a given serial number
	 * @param serialNumber
	 * @return registered work
	 */
	public RegisteredWork registeredWorkBySerialNumber(int serialNumber) {
		for (RegisteredWork rw: this.registeredWork) {
			if (rw.getSerialNumber() == serialNumber) return rw;
		}
		return null;
	}
	
	/**
	 * Returns registered work by a given activity and date.
	 * @param activity
	 * @param calendarDate
	 * @return registered work
	 */
	public RegisteredWork getRegisteredWork(Activity activity, GregorianCalendar calendarDate) {
		for (RegisteredWork regwork: this.registeredWork) {
			if (regwork.getDate().get(Calendar.DATE) == calendarDate.get(Calendar.DATE) &&
				regwork.getDate().get(Calendar.MONTH) == calendarDate.get(Calendar.MONTH) &&
				regwork.getDate().get(Calendar.YEAR) == calendarDate.get(Calendar.YEAR) &&
				regwork.getActivity() == activity) {
				return regwork;
			}
		}
		return null;
	}
}
