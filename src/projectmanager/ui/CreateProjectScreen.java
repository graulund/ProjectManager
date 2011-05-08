package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class CreateProjectScreen extends Screen {

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, this.formatTitle("Create project"));
		
		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
						"Name of project",
						"Client"
					}
				);
		} catch (IOException e) {}
		
		// Create project
		Project project = new Project(in[0], in[1]);
		ProjectManagerApp.getCompany().addProject(project);
		project.addLeader(ProjectManagerApp.getEmployeeLoggedIn());
		
		this.println(out, "Project created.\n");
		
		
		// Set screen to the new project
		this.ui.setScreen(new ManageProjectScreen(project.getSerialNumber()));
		try {
			this.ui.printMenu(out);
		} catch (IOException e) {}
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		return false;
	}

}
