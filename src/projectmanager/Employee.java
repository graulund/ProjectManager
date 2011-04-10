package projectmanager;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	private String name;
	private List<Project> leader_of_projects = new ArrayList<Project>(); // hmm
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
	
	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}
	public void removeActivity(Activity activity) {
		this.activities.remove(activity);
	}
	
	public void addRegisteredWork(RegisteredWork reg_work) {
		this.reg_works.add(reg_work);
	}
	public void removeRegisteredWork(RegisteredWork reg_work) {
		this.reg_works.remove(reg_work);
	}
	
}
