package projectmanager.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.Calendar;

import projectmanager.ui.ProjectManagerUI;

/**
 * Main class for the Project Manager Application. The class holds essential information related to the application logic.
 */
public class ProjectManagerApp {
	/**
	 * Link to the current Company object.
	 */
	private static Company company = new Company();
	
	/**
	 * Link to the current AppStorage object.
	 */
	private static AppStorage storage = new AppStorage();
	
	/**
	 * Link to the current logged in Employee object, if any.
	 */
	private static Employee loggedInEmployee = null;
	
	/**
	 * Boolean determining whether an employee is logged in or not.
	 */
	private static boolean isEmployeeLoggedIn = false;
	
	/**
	 * The current auto-incrementing serial number used to reference various objects.
	 */
	private static int currentSerialNumber = 1;

	/**
	 * Gets the Company object instance in use at the moment.
	 * @return Company instance
	 */
	public static Company getCompany(){
		return company;
	}
	
	/**
	 * Logs in to the app with the given username.
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
	 * Logs out from the app.
	 * @return Boolean defining whether or not you're now logged in
	 */
	public static boolean employeeLogout() {
		loggedInEmployee = null;
		isEmployeeLoggedIn = false;
		return isEmployeeLoggedIn;
	}
	
	/**
	 * 
	 * @param regwork
	 * @throws ProjectManagerException
	 */
	public static void registerWork(RegisteredWork regwork) throws ProjectManagerException {
		getEmployeeLoggedIn().addRegisteredWork(regwork);
	}
	
	
	/**
	 * Generates and returns a new serial number for use for identifying various objects around the app.
	 * @return A new serial number ready for use
	 */
	public static int newSerialNumber(){
		return currentSerialNumber++;
	}
	
	/**
	 * Returns the upcoming to-be-used ("current") serial number.
	 * @return Current serial number
	 */
	public static int getCurrentSerialNumber(){
		return currentSerialNumber;
	}
	
	/**
	 * Sets what number will be used as the next serial number.
	 * @param currentSerialNumber Number to be used
	 */
	public static void setCurrentSerialNumber(int currentSerialNumber) {
		ProjectManagerApp.currentSerialNumber = currentSerialNumber;
	}
	
	/**
	 * Resets the application memory. Only to be used for testing.
	 */
	public static void reset() {
		ProjectManagerApp.company = new Company();
		ProjectManagerApp.loggedInEmployee = null;
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
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minutes
	 * @return
	 */
	public static Calendar createCalendar(int year, int month,
			int day, int hour, int minutes) {
		Calendar cal = new GregorianCalendar();
		cal.set(year, month-1, day, hour, minutes, 0);
		return cal;
	}
	
	/**
	 * Main method; this method starts the app and loads the UI.
	 */
	public static void main(String[] args) throws IOException {
		// Load data
		storage.restoreState();
		
		// Start CLI
		ProjectManagerUI ui = new ProjectManagerUI();
		ui.in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out, true);
		ui.basicLoop(ui.in, out);
	}

	// NO NEW METHODS BELOW THE MAIN METHOD! THIS SHOULD ALWAYS BE THE LAST ONE
}
