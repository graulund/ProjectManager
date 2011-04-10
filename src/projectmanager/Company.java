package projectmanager;

import java.util.ArrayList;
import java.util.List;

public class Company {
	private ArrayList<Project> projects = new ArrayList<Project>();
	private ArrayList<Employee> employees = new ArrayList<Employee>();
	
	
	public List<Project> getProjects() {
		return this.projects;
	}
	public void addProject(Project project) {
		// TODO: What if it already exists in the list?
		this.projects.add(project);
	}
	public void addEmployee(Employee employee) {
		// TODO: What if it already exists in the list?
		this.employees.add(employee);
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	
}
