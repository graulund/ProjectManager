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

	
	public Project(String name, String client) {
		this(name, client, ProjectManagerApp.newSerialNumber());
	}
	public Project(String name, String client, int serialNumber){
		this.serialNumber = serialNumber;
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
	public void addActivity(Activity activity) throws OperationNotAllowedException, CreatingActivityException {
		if (ProjectManagerApp.getEmployeeLoggedIn() != this.projectLeader)
			throw new OperationNotAllowedException("Create activity");
		else if (this.activityByName(activity.getName()) != null)
			throw new CreatingActivityException("You have already created an activity by this name.");
		this.activities.add(activity);
	}
	public void removeActivity(Activity activity) {
		this.activities.remove(activity);
	}
	public List<Activity> getActivities() {
		return activities;
	}

	public String getReport() {
		double registeredHours = 0;
		int delegatedHours = 0;
		for (Activity activity: this.activities) {
			registeredHours += activity.getRegisteredHours();
			delegatedHours += activity.getDelegatedHours();
		}
		return "Project: "+name+". Client: "+client+". Project leader: "+projectLeader.getUsername()+". Work status: "+Math.round(registeredHours)+"/"+delegatedHours+".";
	}
	public Activity activityByName(String name) {
		for (Activity activity: this.activities) {
			if (activity.getName().equals(name)) {
				return activity;
			}
		}
		return null;
	}
	
	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}
	public void removeEmployee(Employee employee) {
		this.employees.remove(employee);
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public boolean hasProjectManager() {
		if (projectLeader == null) return false;
		else return true;
	}
}
