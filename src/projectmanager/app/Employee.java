package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Employee {
	private String name; // 4 letters
	private ArrayList<WorkWeek> workWeeks = new ArrayList<WorkWeek>();
	private Company employedAtCompany;
	private List<Project> leader_of_projects = new ArrayList<Project>();
	private List<Activity> activities = new ArrayList<Activity>();
	private List<RegisteredWork> reg_works = new ArrayList<RegisteredWork>();

	
	public Employee(String name){
		this.name   = name;
	}

	public String getUsername() {
		return this.name;
	}
	public void setUsername(String name) {
		this.name = name;
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
	
		// Checks if the time of the registered work is outside of the time-interval of the activity
		if (timeOutsideActivity(regwork, regwork.getActivity())) {
			throw new RegisterWorkException("You have registered time outside the timer-interval of the given activity");
			
		// Checks if workweek for the registered work already exists
		} else if (workWeek == null) {
			WorkWeek newWorkWeek = new WorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), 
												regwork.getDate().get(Calendar.YEAR)
				   	 							);
			this.workWeeks.add(newWorkWeek);
			newWorkWeek.addRegisteredWork(regwork);
			this.reg_works.add(regwork);
		
		// The workweek does exist, and registered work is added
		} else {
			// checks if the employee already has registered work at the given time
			if (timeAlreadyRegistered(regwork, workWeek)) {
				throw new RegisterWorkException("You have already registered a work at the given time");
			} else {
				workWeek.addRegisteredWork(regwork);
				this.reg_works.add(regwork);
			}
		}
				
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

	public void removeRegisteredWork(RegisteredWork reg_work) {
		this.reg_works.remove(reg_work);
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
	
}
