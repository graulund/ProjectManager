package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.Activity;
import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class AddEmployeeToActivityScreen extends Screen {
	private Project project;
	private Activity activity;
	
	public AddEmployeeToActivityScreen(Project project, Activity activity) {
		this.project = project;
		this.activity = activity;
	}

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, this.formatTitle("Add employee to activity: "+this.activity.getName()) + "Some text.");
		
		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
						"Username of employee"
					}
				);
		} catch (IOException e) {}
		
		// Add employee to activity
		Employee employee = ProjectManagerApp.getCompany().employeeByUsername(in[0]);
		if (employee == null) {
			this.println(out, "No such employee.\n");
			
			// Go back
			this.ui.setScreen(new ManageActivitiesScreen(this.activity.getName(), this.project));
			try {
				this.ui.printMenu(out);
			} catch (IOException e) {}
		} else {
			project.addEmployee(employee, this.activity);
			
			this.println(out, "Employee added to activity.\n");
			
			// Go back
			this.ui.setScreen(new ManageActivitiesScreen(this.activity.getName(), this.project));
			try {
				this.ui.printMenu(out);
			} catch (IOException e) {}
		}	
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		return false;
	}
}
