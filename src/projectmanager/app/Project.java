package projectmanager.app;

import java.util.ArrayList;
import java.util.List;

public class Project {
	private String name;
	private String client;
	private int serialNumber;
	private Employee projectLeader;
	private List<Employee> employees = new ArrayList<Employee>();
	private List<Activity> activities = new ArrayList<Activity>();
	// private Company company; ?
	
	public Project(String name, String client) {
		this.name = name;
		this.client = client;
	}
	public String getName(){
		return this.name;
	}
	public String getClient(){
		return this.client;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setClient(String client){
		this.client = client;
	}
	public void setSerialNumber(int serialKey) {
		this.serialNumber = serialKey;
	}
	public int getSerialNumber() {
		return this.serialNumber;
	}
	public void addLeader(Employee employee) {
		this.projectLeader = employee;
		employee.setProjectLeaderOf(this);
	}
	public Employee getLeader() {
		return this.projectLeader;
	}
	public void addActivity(Activity activity) {
		this.activities.add(activity);
	}
	public List<Activity> getActivities() {
		return activities;
	}
}
