package projectmanager.ui;

import java.io.PrintWriter;
import java.util.List;

import projectmanager.app.Project;
import projectmanager.app.Activity;
import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;

public class ManageActivitiesScreen extends Screen {
	private String name;
	private Activity activity;
	private Project project;
	

	public ManageActivitiesScreen(String name, Project project) {
		this.name = name;
		this.activity = project.activityByName(name);
		this.project = project;
	}

	@Override
	void printMenu(PrintWriter out) {
//		List<Employee> employees = activity.getEmployees();  // For some reason this line gives NullPointerException...
//		String empNames = "";
//		for (Employee e: employees) {
//			empNames = empNames + e.getUsername() + " ";
//		}
		
		this.println(out, 
			this.formatTitle(name) +
//			"Employees on this activity: " + empNames + "\n" +
			this.menuString(new String[]{ "Delete this activity", "Set start date", "Set end date", "Add employee" }, "Back")
		);
		
		
		
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		switch(selection){
			case 0:
				this.ui.setScreen(new ManageProjectScreen(project.getSerialNumber()));
				break;
			
		}
		return false;
	}

}