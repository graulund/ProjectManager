package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class Employee {
	private String username; // 4 letters
	private List<WorkWeek> workWeeks = new ArrayList<WorkWeek>();
	private List<Project> leaderOfProjects = new ArrayList<Project>();
	private String fullname;

	public Employee(String username){
		this.username = username;
	}
	
	public Employee(String username, String fullname) {
		this.username = username;
		this.fullname = fullname;
	}
	
	public void setFullname(String newName)  {
		this.fullname = newName;
	}
	
	public String getFullname() {
		return this.fullname;
	}

	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
	
//	public void setProjectLeaderOf(Project project) {
//		this.leader_of_projects.add(project);
//	}
//	public List<Project> getProjectLeader() {
//		return leader_of_projects;
//	}
//	
//	public List<Activity> getActivities() {
//		return this.activities;
//	}
//	
//	public void addActivity(Activity activity) {
//		this.activities.add(activity);
//	}
//	public void removeActivity(Activity activity) {
//		this.activities.remove(activity);
//	}
	
	public void addRegisteredWork(RegisteredWork regwork) throws RegisterWorkException {		
		WorkWeek workWeek = this.getWorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), regwork.getDate().get(Calendar.YEAR));
		
		// TODO: Der mangler et statement for, hvis det er registreret arbejde uden dato (standard-dato)
		
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
				return false;
		} else {
			return false;
		}
		
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

	public void removeRegisteredWork(RegisteredWork regwork) {
		WorkWeek workweek = this.getWorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), regwork.getDate().get(Calendar.YEAR));
		workweek.getRegisteredWork(regwork);
	}
	
	public List<WorkWeek> getWorkWeeks() {
		return this.workWeeks;
	}

	public WorkWeek getWorkWeek(int weekNumber, int year) {
		for (WorkWeek workweek: this.workWeeks) {
			if (workweek.getWeekNumber() == weekNumber &&
				workweek.getYear() == year) {
				return workweek;
			}
		}
		return null;
	}
	
	public void addWorkWeek(WorkWeek workweek){
		this.workWeeks.add(workweek);
	}

	public void addDelegatedWork(int weekFrom, int weekTo, int yearFrom, int yearTo, Activity activity, int hours) {
		// det deligerede arbejde str¾kker sig kun over 1 uge
		if (weekTo - weekFrom == 0 && yearFrom == yearTo) {
			WorkWeek workweek = this.getWorkWeek(weekFrom, yearFrom);
			if (workweek == null) {
				WorkWeek newWorkWeek = new WorkWeek(weekFrom, yearFrom);
				this.workWeeks.add(newWorkWeek);
				newWorkWeek.addDelegatedWork(new DelegatedWork(hours*2, activity));
			} else {
				workweek.addDelegatedWork(new DelegatedWork(hours*2, activity));
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
					newWorkWeek.addDelegatedWork(new DelegatedWork(halfHoursPerWork, activity));
				} else {
					workweek.addDelegatedWork(new DelegatedWork(halfHoursPerWork, activity));
				}
				dateRun.add(Calendar.WEEK_OF_YEAR, 1);
				i++;
			}
		}
	}
	
	public void addDelegatedWork(int week, int year, Activity activity, int hours) {
		this.addDelegatedWork(week, week, year, year, activity, hours);
	}

//	public Activity getActivity(Activity activityChosen) {
//		for (Activity activity: this.activities) {
//			if (activityChosen == activity) {
//				return activity;
//			}
//		}
//		return null;
//	}
	
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

	public void setProjectLeaderOf(Project project) {
		this.leaderOfProjects.add(project);		
	}

	public List<Project> getProjectLeader() {
		return this.leaderOfProjects;
	}
	

	public void setRegisteredWork(RegisteredWork regwork, int newHourStart,
								  int newMinutesStart, int newHourEnd, int newMinutesEnd) throws ProjectManagerException {
		// TODO: Test hvis den nye tid kollidere med en anden registreret tid.
		//		 Derved kast exception.
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
	
	public void setRegisteredWorkStart(RegisteredWork regwork, int newHourStart, int newMinutesStart) throws ProjectManagerException {
		Calendar endTime = regwork.getEndTime();
		this.setRegisteredWork(regwork, 
							   newHourStart, 
							   newMinutesStart, 
							   endTime.get(Calendar.HOUR_OF_DAY), 
							   endTime.get(Calendar.MINUTE));
	}
	
	public void setRegisteredWorkEnd(RegisteredWork regwork, int newHourEnd, int newMinutesEnd) throws ProjectManagerException {
		Calendar startTime = regwork.getStartTime();
		this.setRegisteredWork(regwork, 
							   startTime.get(Calendar.HOUR_OF_DAY),
							   startTime.get(Calendar.MINUTE), 
							   newHourEnd, 
							   newMinutesEnd);
	}
}
