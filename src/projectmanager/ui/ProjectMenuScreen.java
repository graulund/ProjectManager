package projectmanager.ui;

import java.io.PrintWriter;
import java.util.List;

import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class ProjectMenuScreen extends Screen {
	private String[] choices;
	private int[] numbers;

	@Override
	void printMenu(PrintWriter out) {
		StringBuilder s = new StringBuilder(this.formatTitle("Projects"));
		String create = "Create a new project";
		Employee you = ProjectManagerApp.getEmployeeLoggedIn();
		List<Project> projects = you.getProjectLeader();
		int size = projects.size();
		if(size > 0){
			s.append("You are a leader of the following projects:\n");
			this.choices = new String[size + 1];
			this.numbers = new int[size];
			for(int i = 0; i < size; i++){
				Project project = projects.get(i);
				this.choices[i] = "* " + project.getName();
				this.numbers[i] = project.getSerialNumber();
			}
			this.choices[size] = create;
		} else {
			s.append("You aren't currently a leader of any projects.");
			this.choices = new String[] { create };
		}
		s.append(this.menuString(this.choices, "Back"));
		this.println(out, s.toString());
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		if(selection == 0){
			this.ui.setScreen(new MainMenuScreen());
			return false;
		}
		if(this.choices.length > 0){
			if(selection < this.choices.length && selection >= 0){
				this.ui.setScreen(new ManageProjectScreen(this.numbers[selection-1]));
			} else if(selection == this.choices.length){
				this.ui.setScreen(new CreateProjectScreen());
			} else {
				this.wrongInputMessage(out);
			}
		} else {
			// 1 means create a project
			if(selection == 1){
				this.ui.setScreen(new CreateProjectScreen());
			} else {
				this.wrongInputMessage(out);
			}
		}
		return false;
	}

}
