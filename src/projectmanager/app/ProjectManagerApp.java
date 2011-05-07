package projectmanager.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.Calendar;

import projectmanager.ui.ProjectManagerUI;

public class ProjectManagerApp {
	private static Company company = new Company();
	private static AppStorage storage = new AppStorage();
	private static Employee loggedInEmployee; // Currently logged in employee
	private static boolean isEmployeeLoggedIn = false;
	
	/**
	 * The current auto-incrementing serial number used to reference various objects
	 */
	private static int currentSerialNumber = 1;

	/**
	 * Get the Company object instance in use at the moment
	 * @return Company instance
	 */
	public static Company getCompany(){
		return company;
	}
	
	/**
	 * Log in to the app with the given username
	 * @param username Employee username
	 * @return Boolean defining whether or not you're now logged in
	 */
	public static boolean employeeLogin(String username) {
		loggedInEmployee = company.employeeByUsername(username);
		if (loggedInEmployee != null) {
			isEmployeeLoggedIn = true;
		}
		return isEmployeeLoggedIn;
	}
	
	/**
	 * Am I logged in?
	 * @return Boolean
	 */
	public static boolean isEmployeeLoggedIn() {
		return isEmployeeLoggedIn;
	}
	
	/**
	 * Who's logged in?
	 * @return Employee object (you)
	 */
	public static Employee getEmployeeLoggedIn() {
		return loggedInEmployee;
	}
	
	/**
	 * Log out from the app
	 * @return Boolean defining whether or not you're now logged in
	 */
	public static boolean employeeLogout() {
		loggedInEmployee = null;
		isEmployeeLoggedIn = false;
		return isEmployeeLoggedIn;
	}
	
	public static void registerWork(RegisteredWork regwork) throws ProjectManagerException {
		getEmployeeLoggedIn().addRegisteredWork(regwork);
	}

	/*public static void assignEmployeeToActivity(Employee employee, Activity activity,int hours) {
		// mangler at tjekke, om medarbejder, der "assigner" er projektleder
		activity.addEmployee(employee);
		
		employee.addDelegatedWork(new DelegatedWork(hours/weeks, activity));
	}*/
	
	/*private static int hoursParseHalfhours(int hours, Activity activity) {
		int week = activity.getEnd().get(Calendar.WEEK_OF_YEAR) - activity.getStart().get(Calendar.WEEK_OF_YEAR);
		int halfHoursPerWork = (hours*2)/;
		while (halfHoursPerWork % hours == 0) {
			
		}
	}*/
	
	/**
	 * Generate and return a new serial number for use for identifying various objects around the app
	 * @return A new serial number ready for use
	 */
	public static int newSerialNumber(){
		return currentSerialNumber++;
	}
	
	/**
	 * Return the upcoming to-be-used ("current") serial number
	 * @return Current serial number
	 */
	public static int getCurrentSerialNumber(){
		return currentSerialNumber;
	}
	
	/**
	 * Set what number will be used as the next serial number
	 * @param currentSerialNumber Number to be used
	 */
	public static void setCurrentSerialNumber(int currentSerialNumber) {
		ProjectManagerApp.currentSerialNumber = currentSerialNumber;
	}
	
	/**
	 * This function is only for testing! Resets the APP!
	 */
	public static void reset() {
		ProjectManagerApp.company = new Company();
		ProjectManagerApp.loggedInEmployee = null; // Employee currently logged in
		ProjectManagerApp.isEmployeeLoggedIn = false;
	}
	
	/**
	 * Exits the app and saves the current state.
	 */
	public static void exit(){
		storage.saveCurrentState();
		System.exit(0);
	}
	
	/**
	 * Main function; the main method starting the app.
	 */
	public static void main(String[] args) throws IOException {
		// Load data
		storage.restoreState();
		//storage.printState(storage.storeCurrentState()); //DEBUG
		// Start CLI
		ProjectManagerUI ui = new ProjectManagerUI();
		ui.in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out, true);
		ui.basicLoop(ui.in, out);
	}

	public static Calendar createCalendar(int year, int month,
			int day, int hour, int minutes) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, month-1, day, hour, minutes, 0);
		return cal;
	}

}
