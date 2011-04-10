package projectmanager;

import java.util.ArrayList;
import java.util.List;

public class Company {
	private ArrayList<Project> projects = new ArrayList<Project>();
	private ArrayList<Employee> employees = new ArrayList<Employee>();
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
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	
	public Employee employeeByUsername(String username) {
		for (Employee employee : this.employees) {
			if (username.equals(employee.getUsername())) {
				return employee;
			}
		}
		return null;
	}
	
	public Project projectBySerialNumber(int serialNumber) {
		for (Project project : this.projects) {
			if (serialNumber == project.getSerialNumber()) {
				return project;
			}
		}
		return null;
	}	
}
