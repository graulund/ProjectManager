package projectmanager.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

import projectmanager.ui.ProjectManagerUI;

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
	
	// The app itself
	public static void main(String[] args) throws IOException {
		ProjectManagerUI ui = new ProjectManagerUI();
		ui.in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out, true);
		ui.basicLoop(ui.in, out);
	}
}
