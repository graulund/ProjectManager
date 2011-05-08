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
		List<Employee> employees = activity.getEmployees();
		String empNames = "";
		for (Employee e: employees) {
			empNames = empNames + e.getUsername() + " ";
		}
		if (empNames.length() == 0) {
			empNames = "none";
		}
		
		this.println(out, 
			this.formatTitle(project.getName() + " > " + activity.getName()) +
			"Employees on this activity: " + empNames + "\n" +
			this.menuString(new String[]{ "Add employee", "Add delegated work", "Set start date", "Set end date", "Delete this activity" }, "Back")
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
			case 1:
				this.ui.setScreen(new AddEmployeeToActivityScreen(this.project, this.activity));
				break;
			case 2:
				this.ui.setScreen(new AddDelegatedWorkToActivityScreen(this.project, this.activity));
				break;
			case 3:
				this.ui.setScreen(new SetDateStartOnActivityScreen(this.project, this.activity));
				break;
			case 4:
				this.ui.setScreen(new SetDateEndOnActivityScreen(this.project, this.activity));
				break;
			case 5:
				this.project.removeActivity(activity);
				this.println(out, "Activity removed.\n");
				this.ui.setScreen(new ManageProjectScreen(project.getSerialNumber()));
				break;
			default:
				this.wrongInputMessage(out);
		}
		return false;
	}

}