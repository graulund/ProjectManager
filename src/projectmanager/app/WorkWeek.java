package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class WorkWeek {
	
	private int weekNumber;
	private int year;
	private List<DelegatedWork> delegatedWork   = new ArrayList<DelegatedWork>();
	private List<RegisteredWork> registeredWork = new ArrayList<RegisteredWork>();
	
	public WorkWeek(int weekNumber, int year) {
		//this.employee   = employee;
		this.weekNumber = weekNumber;
		this.year       = year;
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
	public List<DelegatedWork> getDelegatedWork() {
		return delegatedWork;
	}
	public void setDelegatedWork(ArrayList<DelegatedWork> delegatedWork) {
		this.delegatedWork = delegatedWork;
	}
	public void setRegisteredWork(ArrayList<RegisteredWork> registeredWork) {
		this.registeredWork = registeredWork;
	}
	public void addDelegatedWork(DelegatedWork work){
		// TODO: What if it's already in the list?
		this.delegatedWork.add(work);
	}
	public void addRegisteredWork(RegisteredWork work){
		this.registeredWork.add(work);
	}
	
	public int getDelegatedHours(){
		int total = 0;
		for(DelegatedWork work: this.delegatedWork){
			total += work.getHalfHoursWorked();
		}
		return total;
	}
	public List<RegisteredWork> getRegisteredWork() {
		return registeredWork;
	}
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
	
	public RegisteredWork getRegisteredWork(RegisteredWork regwork) {
		for (RegisteredWork r: this.registeredWork) {
			if (r == regwork) return r;
		}
		return null;
	}

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
	
//	public int getRegisteredHours(){
//		int total = 0;
//		for(RegisteredWork work: this.registeredWork){
//			total += work.getHours();
//		}
//		return total;
//	}
	
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
	
	public List<String> getWeekSchedule() {
		List<String> schedule = new ArrayList<String>();
		
		for (DelegatedWork dw: this.delegatedWork) {
			Activity currentActivity = dw.getActivity();
			int totalRegHours = 0;
			for (RegisteredWork rw: this.registeredWork) {
				if (rw.getActivity().equals(currentActivity)) {
					totalRegHours += rw.getHalfHoursWorked(); // why u zero?
				}
			}
			totalRegHours /= 2;
			schedule.add(currentActivity.getName() + ": " + totalRegHours + "/" + dw.getHalfHoursWorked()/2);
		}
		return schedule;
	}
	
	public RegisteredWork registeredWorkBySerialNumber(int serialNumber) {
		for (RegisteredWork rw: this.registeredWork) {
			if (rw.getSerialNumber() == serialNumber) return rw;
		}
		return null;
	}
}
