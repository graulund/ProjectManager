package projectmanager.app;

import java.io.IOException;

public class ProjectManagerApp {
	private static Company company = new Company();
	private static Employee loggedInEmployee; // Currently logged in employee
	private static boolean isEmployeeLoggedIn = false;
	
	/**
	 * The current auto-incrementing serial number used to reference various objects
	 */
	private static int currentSerialNumber = 1;

	
	public static Company getCompany(){
		return company;
	}

	public static boolean employeeLogin(String username) {
		loggedInEmployee = company.employeeByUsername(username);
		if (loggedInEmployee != null) {
			isEmployeeLoggedIn = true;
		}
		return isEmployeeLoggedIn;
	}

	public static boolean isEmployeeLoggedIn() {
		return isEmployeeLoggedIn;
	}

	public static Employee getEmployeeLoggedIn() {
		return loggedInEmployee;
	}

	public static boolean employeeLogout() {
		loggedInEmployee = null;
		isEmployeeLoggedIn = false;
		return isEmployeeLoggedIn;
	}
	
	public static void registerWork(Employee employee, RegisteredWork regWork1) throws RegisterWorkException {
		if (employee == getEmployeeLoggedIn()) employee.addRegisteredWork(regWork1);
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
	
	public static int newSerialNumber(){
		return currentSerialNumber++;
	}
	
	public static int getCurrentSerialNumber(){
		return currentSerialNumber;
	}

}
