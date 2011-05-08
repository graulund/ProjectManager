package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import projectmanager.app.Activity;
import projectmanager.app.DelegatedWork;
import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class AddDelegatedWorkToActivityScreen extends Screen {
	private Project project;
	private Activity activity;
	Calendar calendar = GregorianCalendar.getInstance();
	
	public AddDelegatedWorkToActivityScreen(Project project, Activity activity) {
		this.project = project;
		this.activity = activity;
	}

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, this.formatTitle("Add delegated work to activity: "+this.activity.getName()) + "Some text.");
		
		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
							"Username of employee",
							"Hours of work to delegate",
							"Start week (1-52)",
							"Start year",
							"End week (1-52)",
							"End year"
					}, 
					new String[]{
							null,
							null,
							""+this.calendar.get(Calendar.WEEK_OF_YEAR),
							""+this.calendar.get(Calendar.YEAR),
							""+this.calendar.get(Calendar.WEEK_OF_YEAR),
							""+this.calendar.get(Calendar.YEAR)
					}
			);
		} catch (IOException e) {}

		// Getting employee
		Employee employee = ProjectManagerApp.getCompany().employeeByUsername(in[0]);
		//activity.getEmployees().contains(employee);
		
		// Parsing work hours
		int hours = -1;
		int weekFrom = -1;
		int weekTo = -1;
		int yearFrom = -1;
		int yearTo = -1;
		try {
			hours = Integer.parseInt(in[1]);
			weekFrom = Integer.parseInt(in[2]);
			weekTo = Integer.parseInt(in[4]);
			yearFrom = Integer.parseInt(in[3]);
			yearTo = Integer.parseInt(in[5]);
		} catch (Exception e) {}
		
		// Check if all values are alright
		if (employee == null || hours <= 0 || !this.isValidWorkWeeks(weekFrom, weekTo)
				|| !this.isValidYear(yearFrom, yearTo)) {
			this.wrongInputMessage(out);
		} else {
			employee.addDelegatedWork(weekFrom, weekTo, yearFrom, yearTo, activity, hours);
			this.println(out, "Delegated work added to employee.\n");
		}
		
		// Go back
		this.ui.setScreen(new ManageActivitiesScreen(this.activity.getName(), this.project));
		try {
			this.ui.printMenu(out);
		} catch (IOException e) {}
	}	


	@Override
	boolean processInput(String input, PrintWriter out) {
		// TODO Auto-generated method stub
		return false;
	}

}
