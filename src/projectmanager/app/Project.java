package projectmanager.app;

import java.util.ArrayList;
import java.util.List;
/**
 * A class that defines a project.
 * A project contains a number of activities and employees, but only a single project leader.
 */
public class Project {
	/**
	 * Name of project.
	 */
	private String name;
	/**
	 * Name of client.
	 */
	private String client;
	/**
	 * Serial number used mainly by AppStorage class.
	 */
	private int serialNumber;
	/**
	 * The Employee object that acts as a leader on the project.
	 */
	private Employee projectLeader;
	/**
	 * List of Employees assigned to the project.
	 */
	private List<Employee> employees = new ArrayList<Employee>();
	/**
	 * List of Activities in the project.
	 */
	private List<Activity> activities = new ArrayList<Activity>();

	/**
	 * Constructor.
	 * @param name
	 * @param client
	 */
	public Project(String name, String client) {
		this(name, client, ProjectManagerApp.newSerialNumber());
	}
	/**
	 * Constructor used by AppStorage when loading a state.
	 * @param name
	 * @param client
	 * @param serialNumber
	 */
	public Project(String name, String client, int serialNumber){
		this.serialNumber = serialNumber;
		this.name = name;
		this.client = client;
	}
	/**
	 * Gets name of project.
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * Gets name of client.
	 * @return
	 */
	public String getClient(){
		return this.client;
	}
	/**
	 * Sets name of project.
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * Sets name of client.
	 * @param client
	 */
	public void setClient(String client){
		this.client = client;
	}
	/**
	 * Sets serial number.
	 * @param serialKey
	 */
	public void setSerialNumber(int serialKey) {
		this.serialNumber = serialKey;
	}
	/**
	 * Gets serial number.
	 * @return
	 */
	public int getSerialNumber() {
		return this.serialNumber;
	}
	/**
	 * Sets employee as leader on project.
	 * @param employee
	 */
	public void addLeader(Employee employee) {
		if (this.projectLeader != null) {
			this.projectLeader.removeProjectLeaderOf(this);
		}
		this.projectLeader = employee;
		employee.setProjectLeaderOf(this);
	}
	/**
	 * Gets leader of project.
	 * @return
	 */
	public Employee getLeader() {
		return this.projectLeader;
	}
	/**
	 * Adds activity to project.
	 * @param activity
	 * @throws OperationNotAllowedException
	 * @throws CreatingActivityException
	 */
	public void addActivity(Activity activity) throws OperationNotAllowedException, CreatingActivityException {
		if (this.projectLeader != null) {
			if (ProjectManagerApp.getEmployeeLoggedIn() != this.projectLeader)
				throw new OperationNotAllowedException("Create activity");
			else if (this.activityByName(activity.getName()) != null)
				throw new CreatingActivityException("You have already created an activity by this name.");
			this.activities.add(activity);
		}	
	}
	/**
	 * Removes activity from project.
	 * @param activity
	 */
	public void removeActivity(Activity activity) {
		this.activities.remove(activity);
	}
	/**
	 * Gets list of activities.
	 * @return
	 */
	public List<Activity> getActivities() {
		return activities;
	}
	/**
	 * Returns a report on the project.
	 * @return
	 */
	public String getReport() {
		double registeredHours = 0;
		int delegatedHours = 0;
		for (Activity activity: this.activities) {
			registeredHours += activity.getRegisteredHours();
			delegatedHours += activity.getDelegatedHours();
		}
		return "Project: "+name+". Client: "+client+". Project leader: "+projectLeader.getUsername()+". Work status: "+Math.round(registeredHours/2)+"/"+delegatedHours/2+".";
	}
	/**
	 * Checks if project has an activity with a certain name and returns it.
	 * @param name
	 * @return
	 */
	public Activity activityByName(String name) {
		for (Activity activity: this.activities) {
			if (activity.getName().equals(name)) {
				return activity;
			}
		}
		return null;
	}
	/**
	 * Adds employee to project and activity.
	 * @param employee
	 * @param activity
	 */
	public void addEmployee(Employee employee, Activity activity) {
		this.employees.add(employee);
		activity.addEmployee(employee);
	}
	/**
	 * Adds employee to project.
	 * Used mainly by AppStorage.
	 * @param employee
	 */
	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}
	/**
	 * Removes employee from project.
	 * @param employee
	 */
	public void removeEmployee(Employee employee) {
		this.employees.remove(employee);
	}
	/**
	 * Returns a list of employees on project.
	 * @return
	 */
	public List<Employee> getEmployees() {
		return employees;
	}
	/**
	 * Overwrites the projects list of employees with the given one.
	 * @param employees
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	/**
	 * Checks if project has a leader.
	 * @return
	 */
	public boolean hasProjectManager() {
		if (projectLeader == null) return false;
		else return true;
	}
}
