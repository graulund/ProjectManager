package projectmanager;

import java.util.List;
import java.util.ArrayList;

public class ProjectManagerApp {
	private Company company = new Company();
	private Employee loggedInEmployee;
	private boolean employeeLoggedIn = false;
	
	public Company getCompany(){
		return this.company;
	}

	public boolean employeeLogin(String username) {
		this.loggedInEmployee = this.employeeByUsername(username);
		if (this.loggedInEmployee != null) {
			this.employeeLoggedIn = true;
		}
		return this.employeeLoggedIn;
	}

	private Employee employeeByUsername(String username) {
		for (Employee employee : company.getEmployees()) {
			if (username.equals(employee.getUsername())) {
				return employee;
			}
		}
		return null;
	}

	public boolean employeeLoggedIn() {
		return this.employeeLoggedIn;
	}

	public Employee getEmployeeLoggedIn() {
		return this.loggedInEmployee;
	}

	public boolean employeeLogout() {
		this.loggedInEmployee = null;
		this.employeeLoggedIn = false;
		return this.employeeLoggedIn;
	}
}
