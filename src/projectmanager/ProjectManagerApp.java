package projectmanager;

import java.util.List;
import java.util.ArrayList;

public class ProjectManagerApp {
	private Company company = new Company();
	private Employee loggedInEmployee; // Currently logged in employee
	private boolean isEmployeeLoggedIn = false;
	
	public Company getCompany(){
		return this.company;
	}

	public boolean employeeLogin(String username) {
		this.loggedInEmployee = this.company.employeeByUsername(username);
		if (this.loggedInEmployee != null) {
			this.isEmployeeLoggedIn = true;
		}
		return this.isEmployeeLoggedIn;
	}

	public boolean isEmployeeLoggedIn() {
		return this.isEmployeeLoggedIn;
	}

	public Employee getEmployeeLoggedIn() {
		return this.loggedInEmployee;
	}

	public boolean employeeLogout() {
		this.loggedInEmployee = null;
		this.isEmployeeLoggedIn = false;
		return this.isEmployeeLoggedIn;
	}
}
