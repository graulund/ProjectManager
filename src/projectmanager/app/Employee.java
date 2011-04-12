package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Employee {
	private String name; // 4 letters
	private List<WorkWeek> workWeeks = new ArrayList<WorkWeek>();
	private List<Project> leader_of_projects = new ArrayList<Project>(); // hmm.. do we need a list of projects where the employee has activities?
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
	
	public void addRegisteredWork(RegisteredWork regwork) {
		this.workWeeks.add(
				new WorkWeek(regwork.getDate().get(Calendar.WEEK_OF_YEAR), 
						   	 regwork.getDate().get(Calendar.YEAR)));
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
