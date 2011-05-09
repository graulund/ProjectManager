package projectmanager.app;

import java.util.ArrayList;
import java.util.List;

/**
 * The Company object keeps hold of projects and employees
 * in this application.
 */
public class Company {
	/**
	 * List of projects which has been created by this company.
	 */
	private List<Project> projects = new ArrayList<Project>();
	
	/**
	 * List of employees hired.
	 */
	private List<Employee> employees = new ArrayList<Employee>();
	
	/**
	 * Reference to the current Company object.
	 */
	public static Company c;
	
	/**
	 * Company constructor.
	 */
	public Company(){
		c = this;
	}
	
	/**
	 * Returns the list of all projects by this company.
	 * @return list of Project objects.
	 */
	public List<Project> getProjects() {
		return this.projects;
	}
	
	/**
	 * Sets the list of projects.
	 * @param list of Project objects.
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	/**
	 * Adds a project to the company
	 * @param project
	 */
	public void addProject(Project project) {
		for(Project p: this.projects){
			if(p == project){ // This project already present
				return;
			}
		}
		this.projects.add(project);
	}
	
	/**
	 * Adds an employee to the company
	 * @param employee
	 */
	public void addEmployee(Employee employee) {
		for(Employee e: this.employees){
			if(e == employee){ // This employee already present
				return;
			}
		}
		this.employees.add(employee);
	}
	
	/**
	 * Returns the list of employees
	 * @return list of employees
	 */
	public List<Employee> getEmployees() {
		return employees;
	}
	
	/**
	 * Sets the list of employees
	 * @param employees
	 */
	public void setEmployees(ArrayList<Employee> employees){
		this.employees = employees;
		//this.currentSerialNumber = this.employees.size() + 1;
	}
	
	/**
	 * Returns an employee by the given username
	 * @param username
	 * @return employee
	 */
	public Employee employeeByUsername(String username) {
		for (Employee employee: this.employees) {
			if (username.equals(employee.getUsername())) {
				return employee;
			}
		}
		return null;
	}
	
	/**
	 * Returns a project by the given serialnumber
	 * @param serialNumber
	 * @return project
	 */
	public Project projectBySerialNumber(int serialNumber) {
		for (Project project: this.projects) {
			if (serialNumber == project.getSerialNumber()) {
				return project;
			}
		}
		return null;
	}

	/**
	 * Returns a report for this company
	 * @return report (String)
	 */
	public String getReport() {
		String report = "";
		for (Project p: projects) {
			report = report + p.getReport() + "\n";
		}
		return report;
	}
}
