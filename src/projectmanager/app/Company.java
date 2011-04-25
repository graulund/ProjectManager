package projectmanager.app;

import java.util.ArrayList;
import java.util.List;

public class Company {
	private List<Project> projects = new ArrayList<Project>();
	private List<Employee> employees = new ArrayList<Employee>();
	private int currentSerialNumber = 1;
	
	
	public List<Project> getProjects() {
		return this.projects;
	}
	public void addProject(Project project) {
		// TODO: What if it already exists in the list?
		this.projects.add(project);
		project.setSerialNumber(currentSerialNumber++);
	}
	
	public void addEmployee(Employee employee) {
		// TODO: What if it already exists in the list?
		this.employees.add(employee);
		employee.setEmployedAtCompany(this);
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}
	
	public void setEmployees(ArrayList<Employee> employees){
		this.employees = employees;
		this.currentSerialNumber = this.employees.size() + 1;
	}
	
	public Employee employeeByUsername(String username) {
		for (Employee employee: this.employees) {
			if (username.equals(employee.getUsername())) {
				return employee;
			}
		}
		return null;
	}
	
	public Project projectBySerialNumber(int serialNumber) {
		for (Project project: this.projects) {
			if (serialNumber == project.getSerialNumber()) {
				return project;
			}
		}
		return null;
	}
	
	public String getEmployeeAvailability() {
		return "On coffee break."; // TODO: FIX THIS!
		
		// Should return a list of all employees and their (near) future availability
	}
}
