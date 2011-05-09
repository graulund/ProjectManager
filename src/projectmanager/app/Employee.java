package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A class that defines an employee.
 * The key of the employee is his username, but
 * he can also contain a full name
 */
public class Employee {
	/**
	 * The username of the employee
	 */
	private String username;
	
	/**
	 * A list of workweeks for the employee.
	 * There exists a workweek for each week, if the week
	 * contains any work.
	 */
	private List<WorkWeek> workWeeks = new ArrayList<WorkWeek>();
	
	/**
	 * List of projects which this employee is a leader of
	 */
	private List<Project> leaderOfProjects = new ArrayList<Project>();
	
	/**
	 * The full name of the employee
	 */
	private String fullname;
	
	/**
	 * Employee constructor only with username
	 * @param username
	 */
	public Employee(String username){
		this.username = username;
	}
	
	/**
	 * Employee constructor with full informations
	 * @param username
	 * @param fullname
	 */
	public Employee(String username, String fullname) {
		this.username = username;
		this.fullname = fullname;
	}
	
	/**
	 * Sets the employees full name
	 * @param newName
	 */
	public void setFullname(String newName)  {
		this.fullname = newName;
	}
	
	/**
	 * Returns the employee's full nameif it exists
	 * @return full name of employee
	 */
	public String getFullname() {
		if (this.fullname != null) 
			return this.fullname;
		else 
			return "";
		
	}
	
	/**
	 * Returns the employee's username
	 * @return username of employee
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Sets the username of the employee
	 * @param username
	 */
	public void setUsername(String name) {
		this.username = name;
	}
	
	/**
	 * Adds a given registered work to this employee.
	 * Creates a new workweek to add it to, if it doesn't exist
	 * @param regwork the registered work
	 * @throws ProjectManagerException
	 */
	public void addRegisteredWork(RegisteredWork regwork) throws ProjectManagerException {		
		WorkWeek workWeek = this.getWorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), regwork.getDate().get(Calendar.YEAR));
		
		// Checks if the entered time is valid for registered work
		if (!isValidRegwork(regwork)) {
			throw new RegisterWorkException("The starting time is after the ending time.");
		
		// Checks if the time of the registered work is outside of the time-interval of the activity			
		} else if (timeOutsideActivity(regwork, regwork.getActivity())) {
			throw new RegisterWorkException("You have registered time outside the timer-interval of the given activity.");
			
		// Checks if workweek for the registered work already exists
		} else if (workWeek == null) {
			WorkWeek newWorkWeek = new WorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), 
												regwork.getDate().get(Calendar.YEAR)
				   	 							);
			this.workWeeks.add(newWorkWeek);
			newWorkWeek.addRegisteredWork(regwork);
		
		// The workweek does exist, and registered work is added
		} else {
			// checks if the employee already has registered work at the given time
			if (timeAlreadyRegistered(regwork, workWeek)) {
				throw new RegisterWorkException("You have already registered a work at the given time");
			} else {
				workWeek.addRegisteredWork(regwork);
			}
		}
				
	}
	
	/**
	 * Removes registered work from this employee
	 * @param regwork the registered work
	 */
	public void removeRegisteredWork(RegisteredWork regwork) {
		WorkWeek workweek = this.getWorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), regwork.getDate().get(Calendar.YEAR));
		workweek.getRegisteredWork(regwork);
	}
	
	/**
	 * Returns a list of all the workweeks for this employee
	 * @return list of WorkWeeks
	 */
	public List<WorkWeek> getWorkWeeks() {
		return this.workWeeks;
	}
	
	/**
	 * Returns a WorkWeek determined by a given weeknumber and year.
	 * @param weekNumber
	 * @param year
	 * @return a WorkWeek
	 */
	public WorkWeek getWorkWeek(int weekNumber, int year) {
		for (WorkWeek workweek: this.workWeeks) {
			if (workweek.getWeekNumber() == weekNumber &&
				workweek.getYear() == year) {
				return workweek;
			}
		}
		return null;
	}
	
	/**
	 * Adds a WorkWeek to this employee
	 * @param workweek
	 */
	public void addWorkWeek(WorkWeek workweek){
		this.workWeeks.add(workweek);
	}

	/**
	 * Adds delegated work that stretches from the given weekFrom (and yearFrom)
	 * to the given weekTo (yearTo).
	 * @param weekFrom
	 * @param weekTo
	 * @param yearFrom
	 * @param yearTo
	 * @param activity
	 * @param hours
	 */
	public void addDelegatedWork(int weekFrom, int weekTo, int yearFrom, int yearTo, Activity activity, int hours) {
		// det deligerede arbejde str¾kker sig kun over 1 uge
		if (weekTo - weekFrom == 0 && yearFrom == yearTo) {
			WorkWeek workweek = this.getWorkWeek(weekFrom, yearFrom);
			if (workweek == null) {
				WorkWeek newWorkWeek = new WorkWeek(weekFrom, yearFrom);
				this.workWeeks.add(newWorkWeek);
				DelegatedWork dw = new DelegatedWork(hours*2, activity);
				newWorkWeek.addDelegatedWork(dw);
				activity.addDelegatedWork(dw);
			} else {
				DelegatedWork dw = new DelegatedWork(hours*2, activity);
				workweek.addDelegatedWork(dw);
				activity.addDelegatedWork(dw);
			}
			
		// det deligerede arbejde str¾kker sig over flere uger
		// --> timerne deles op til flere uger
		} else {
			Calendar dateRun = new GregorianCalendar();
			Calendar dateTo = new GregorianCalendar();
			dateRun.set(Calendar.YEAR, yearFrom);
			dateRun.set(Calendar.WEEK_OF_YEAR, weekFrom);
			dateTo.set(Calendar.YEAR, yearTo);
			dateTo.set(Calendar.WEEK_OF_YEAR, weekTo);
			int weeks = dateTo.get(Calendar.WEEK_OF_YEAR) - dateRun.get(Calendar.WEEK_OF_YEAR) + 1;
			dateTo.add(Calendar.WEEK_OF_YEAR, 1);
			int week, year, i = 1, halfHoursPerWork = (hours*2)/weeks;
			while (dateRun.before(dateTo)) {
				if (i == 1) halfHoursPerWork = halfHoursPerWork + ((hours*2) % (halfHoursPerWork*weeks));
				else halfHoursPerWork = (hours*2)/weeks;
				week = dateRun.get(Calendar.WEEK_OF_YEAR);
				year = dateRun.get(Calendar.YEAR);
				WorkWeek workweek = this.getWorkWeek(week, year);
				if (workweek == null) {
					WorkWeek newWorkWeek = new WorkWeek(week, year);
					this.workWeeks.add(newWorkWeek);
					DelegatedWork dw = new DelegatedWork(halfHoursPerWork, activity);
					newWorkWeek.addDelegatedWork(dw);
					activity.addDelegatedWork(dw);
				} else {
					DelegatedWork dw = new DelegatedWork(halfHoursPerWork, activity);
					workweek.addDelegatedWork(dw);
					activity.addDelegatedWork(dw);
				}
				dateRun.add(Calendar.WEEK_OF_YEAR, 1);
				i++;
			}
		}
	}
	
	/**
	 * Adds delegated work that only stretches over 1 week
	 * @param week
	 * @param year
	 * @param activity
	 * @param hours
	 */
	public void addDelegatedWork(int week, int year, Activity activity, int hours) {
		this.addDelegatedWork(week, week, year, year, activity, hours);
	}
	
	/**
	 * Returns a list of all the delegated work and all the registered work
	 * for this employee
	 * @return list of delegated- and registered work
	 */
	public List<Work> getWork(){
		List<Work> works = new ArrayList<Work>();
		for(WorkWeek week: this.workWeeks){
			for(DelegatedWork w: week.getDelegatedWork()){
				works.add(w);
			}
			for(RegisteredWork w: week.getRegisteredWork()){
				works.add(w);
			}
		}
		return works;
	}

	/**
	 * Sets this employee to project leader on a given project
	 * @param project
	 */
	public void setProjectLeaderOf(Project project) {
		this.leaderOfProjects.add(project);		
	}

	/**
	 * Returns the list of projects which this employee is a leader of
	 * @return list of projects
	 */
	public List<Project> getProjectLeader() {
		return this.leaderOfProjects;
	}
	
	/**
	 * Sets a new timeFrom and timeTo for a given registered work
	 * @param regwork
	 * @param newHourStart
	 * @param newMinutesStart
	 * @param newHourEnd
	 * @param newMinutesEnd
	 * @throws ProjectManagerException
	 */
	public void setRegisteredWork(RegisteredWork regwork, int newHourStart,
								  int newMinutesStart, int newHourEnd, int newMinutesEnd) throws ProjectManagerException {
		WorkWeek workWeek = this.getWorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), 
				 							 regwork.getDate().get(Calendar.YEAR));
		
		Calendar oldStart = regwork.getStartTime();
		Calendar oldEnd	  = regwork.getEndTime();
		regwork.setStartTime(newHourStart, newMinutesStart);
		regwork.setEndTime(newHourEnd, newMinutesEnd);
		if (!isValidRegwork(regwork)) {
			regwork.setStartTime(oldStart.get(Calendar.HOUR_OF_DAY), oldStart.get(Calendar.MINUTE));
			regwork.setEndTime(oldEnd.get(Calendar.HOUR_OF_DAY), oldEnd.get(Calendar.MINUTE));
			throw new RegisterWorkException("The starting time is after the ending time.");
			
		} else if (timeAlreadyRegistered(regwork, workWeek)) {
			throw new RegisterWorkException("You have already registered a work at the given time");
		
		// new values are valid
		} else {
			Calendar startTime = regwork.getStartTime();
			Calendar endTime   = regwork.getEndTime();
			startTime.set(Calendar.HOUR_OF_DAY, newHourStart);
			startTime.set(Calendar.MINUTE, newMinutesStart);
			endTime.set(Calendar.HOUR_OF_DAY, newHourEnd);
			endTime.set(Calendar.MINUTE, newMinutesEnd);
			regwork.updateHalfHoursWorked();
		}
	}
	
	/**
	 * Sets only the start-time of a given registered work
	 * @param regwork
	 * @param newHourStart
	 * @param newMinutesStart
	 * @throws ProjectManagerException
	 */
	public void setRegisteredWorkStart(RegisteredWork regwork, int newHourStart, int newMinutesStart) throws ProjectManagerException {
		Calendar endTime = regwork.getEndTime();
		this.setRegisteredWork(regwork, 
							   newHourStart, 
							   newMinutesStart, 
							   endTime.get(Calendar.HOUR_OF_DAY), 
							   endTime.get(Calendar.MINUTE));
	}
	
	/**
	 * Sets only the end-time of a given registered work
	 * @param regwork
	 * @param newHourEnd
	 * @param newMinutesEnd
	 * @throws ProjectManagerException
	 */
	public void setRegisteredWorkEnd(RegisteredWork regwork, int newHourEnd, int newMinutesEnd) throws ProjectManagerException {
		Calendar startTime = regwork.getStartTime();
		this.setRegisteredWork(regwork, 
							   startTime.get(Calendar.HOUR_OF_DAY),
							   startTime.get(Calendar.MINUTE), 
							   newHourEnd, 
							   newMinutesEnd);
	}
	
	/**
	 * Returns a list of activities in which this employee has delegated work
	 * @param weekStart
	 * @param yearStart
	 * @param weekEnd
	 * @param yearEnd
	 * @return
	 */
	public List<Activity> getActivities(int weekStart, int yearStart, int weekEnd, int yearEnd) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.WEEK_OF_YEAR, weekStart);
		cal.set(Calendar.YEAR, yearStart);
		Calendar cal2 = new GregorianCalendar();
		cal2.set(Calendar.WEEK_OF_YEAR, weekEnd);
		cal2.set(Calendar.YEAR, yearEnd);
		cal2.add(Calendar.WEEK_OF_YEAR, 1);
		List<Activity> activities = new ArrayList<Activity>();
		while (cal.before(cal2)) {
			WorkWeek workweek = this.getWorkWeek(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR));
			if (workweek != null) {
				List<DelegatedWork> delworks = workweek.getDelegatedWork();
				for (DelegatedWork dw: delworks) {
					if (activities.size() > 0) {
						for (Activity a: activities) {
							if (!a.equals(dw.getActivity()))
								activities.add(dw.getActivity());
						}
					} else {
						activities.add(dw.getActivity());
					}
				}
			}
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return activities;
	}

	/**
	 * Returns a list of registered work from a given week to another given week
	 * @param weekStart
	 * @param yearStart
	 * @param weekEnd
	 * @param yearEnd
	 * @return
	 */
	public List<RegisteredWork> getRegisteredWork(int weekStart, int yearStart,int weekEnd, int yearEnd) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.WEEK_OF_YEAR, weekStart);
		cal.set(Calendar.YEAR, yearStart);
		Calendar cal2 = new GregorianCalendar();
		cal2.set(Calendar.WEEK_OF_YEAR, weekEnd);
		cal2.set(Calendar.YEAR, yearEnd);
		cal2.add(Calendar.WEEK_OF_YEAR, 1);
		List<RegisteredWork> regworks = new ArrayList<RegisteredWork>();
		while (cal.before(cal2)) {
			WorkWeek workweek = this.getWorkWeek(cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.YEAR));
			if (workweek != null) {
				for (RegisteredWork rw: workweek.getRegisteredWork()) {
					regworks.add(rw);
				}
			}
			cal.add(Calendar.WEEK_OF_YEAR, 1);
		}
		return regworks;
	}

	/**
	 * Removes the title as projectleader for a given project
	 * @param project
	 */
	public void removeProjectLeaderOf(Project project) {
		this.leaderOfProjects.remove(project);
		
	}
	
	private boolean isValidRegwork(RegisteredWork regwork) {
		if (regwork.getStartTime().after(regwork.getEndTime())) return false;
		else return true;
	}

	private boolean timeOutsideActivity(RegisteredWork regwork, Activity activity) {
		if (activity.getStart() != null && activity.getEnd() != null) {
			if (regwork.getDate().before(activity.getStart()) ||
					regwork.getDate().after(activity.getEnd())) {
					return true;
				}	
		} 
		return false;
	}

	private boolean timeAlreadyRegistered(RegisteredWork regwork, WorkWeek workweek) {
		// if no date has been entered
		if (regwork.getDate().get(Calendar.YEAR) == 2000 && 
			regwork.getDate().get(Calendar.MONTH) == 0   &&
			regwork.getDate().get(Calendar.DATE) == 1) {
			return false;
		}
		
		for (RegisteredWork regworkCompare: workweek.getRegisteredWork(regwork.getStartTime())) {
			if (regwork != regworkCompare) {
				if (regwork.getStartTime().before(regworkCompare.getEndTime()) &&
						regwork.getEndTime().after(regworkCompare.getStartTime())) {
						return true;
					} 
			}
		}
		return false;
	}
}
