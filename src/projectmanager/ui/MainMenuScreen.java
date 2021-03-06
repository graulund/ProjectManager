package projectmanager.ui;

import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class MainMenuScreen extends Screen {

	@Override
	void printMenu(PrintWriter out) {
		Employee you = ProjectManagerApp.getEmployeeLoggedIn();
		this.println(out, 
			this.formatTitle("Project Manager", "=") + 
			"You are logged in as " + you.getUsername() + ".\n" +
			this.menuString(
				new String[]{ "Manage projects", "Manage time", "Add employee to company", "Print company report" }, 
			"Exit")
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		switch(selection){
			case 0:
				this.exit(out);
				break;
			case 1:
				this.ui.setScreen(new ProjectMenuScreen());
				break;
			case 2:
				this.ui.setScreen(new TimeMenuScreen());
				break;
			case 3:
				this.ui.setScreen(new CreateEmployeeScreen());
				break;
			case 4:
				this.println(out, ProjectManagerApp.getCompany().getReport());
				break;
			default:
				this.wrongInputMessage(out);
		}
		return false;
	}

}
