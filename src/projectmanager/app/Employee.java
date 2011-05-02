package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class Employee {
	private String username; // 4 letters
	private ArrayList<WorkWeek> workWeeks = new ArrayList<WorkWeek>();
	private Company employedAtCompany;
	private List<Project> leader_of_projects = new ArrayList<Project>();
	private List<Activity> activities = new ArrayList<Activity>(); // ??
	private String fullname;

	public Employee(String username){
		this.username   = username;
	}
	
	public Employee(String username, String fullname) {
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
	
	public void setEmployedAtCompany(Company company) {
		this.employedAtCompany = company;
	}
	
	public void setProjectLeaderOf(Project project) {
		this.leader_of_projects.add(project);
	}
	public List<Project> getProjectLeader() {
		return leader_of_projects;
	}
	
	public List<Activity> getActivities() {
		return this.activities;
	}
	
	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}
	public void removeActivity(Activity activity) {
		this.activities.remove(activity);
	}
	
	public void addRegisteredWork(RegisteredWork regwork) throws RegisterWorkException {		
		WorkWeek workWeek = this.getWorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), regwork.getDate().get(Calendar.YEAR));
		
		// Checks if the entered time is valid for registered work
		if (!isValidRegwork(regwork)) {
			throw new RegisterWorkException("Invalid time registration: the start time is after the end time.");
		
		// Checks if the time of the registered work is outside of the time-interval of the activity			
		} else if (timeOutsideActivity(regwork, regwork.getActivity())) {
			throw new RegisterWorkException("You have registered time outside the timer-interval of the given activity");
			
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
		for (RegisteredWork regworkCompare: workweek.getRegisteredWork(regwork.getStartTime())) {
			if (regwork.getStartTime().before(regworkCompare.getEndTime()) &&
				regwork.getEndTime().after(regworkCompare.getStartTime())) {
				return true;
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

	public void addDelegatedWork(DelegatedWork delwork) {
		Calendar run = delwork.getActivity().getStart();
		Calendar end = delwork.getActivity().getEnd();
		end.add(Calendar.WEEK_OF_YEAR, 1);
		while (run.before(end)) {
			WorkWeek workweek = this.getWorkWeek(run.get(Calendar.WEEK_OF_YEAR), Calendar.YEAR);
			if (workweek == null) {
				WorkWeek newWorkWeek = new WorkWeek(run.get(Calendar.WEEK_OF_YEAR),run.get(Calendar.YEAR));
				this.workWeeks.add(newWorkWeek);
				newWorkWeek.addDelegatedWork(delwork);
			} else {
				workweek.addDelegatedWork(delwork);
			}
			run.add(Calendar.WEEK_OF_YEAR, 1);
		}
		
	}

	public Activity getActivity(Activity activityChosen) {
		for (Activity activity: this.activities) {
			if (activityChosen == activity) {
				return activity;
			}
		}
		return null;
	}
	
}
