package projectmanager.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
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
	
	public void registerWork(Employee employee, RegisteredWork regWork1) throws RegisterWorkException {
		if (employee == this.getEmployeeLoggedIn()) employee.addRegisteredWork(regWork1);
	}

	/*public void assignEmployeeToActivity(Employee employee, Activity activity,int hours) {
		// mangler at tjekke, om medarbejder, der "assigner" er projektleder
		activity.addEmployee(employee);
		
		employee.addDelegatedWork(new DelegatedWork(hours/weeks, activity));
	}*/
	
	/*private int hoursParseHalfhours(int hours, Activity activity) {
		int week = activity.getEnd().get(Calendar.WEEK_OF_YEAR) - activity.getStart().get(Calendar.WEEK_OF_YEAR);
		int halfHoursPerWork = (hours*2)/;
		while (halfHoursPerWork % hours == 0) {
			
		}
	}*/
	
	// The app itself
	public static void main(String[] args) throws IOException {
//		ProjectManagerUI ui = new ProjectManagerUI();
//		ui.in = new BufferedReader(new InputStreamReader(System.in));
//		PrintWriter out = new PrintWriter(System.out, true);
//		ui.basicLoop(ui.in, out);
		
		AppStorage storage = new AppStorage();
		StoredData obj     = storage.storeCurrentState();
		storage.printState(obj);
	}

}
