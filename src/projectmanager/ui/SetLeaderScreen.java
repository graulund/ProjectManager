package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class SetLeaderScreen extends Screen {

	private Project project;
	
	public SetLeaderScreen(Project project) {
		this.project = project;
	}

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, this.formatTitle("Set leader of "+project.getName()) + "Current leader is "+project.getLeader().getUsername());
		
		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
						"Username of new leader"
					}
				);
		} catch (IOException e) {}
		
		// Set leader
		Employee employee = ProjectManagerApp.getCompany().employeeByUsername(in[0]);
		if (employee == null) {
			this.wrongInputMessage(out);
		} else {
			project.addLeader(employee);
			this.println(out, employee.getUsername()+" is now the leader of "+project.getName()+".\n");
		}

		// Go back
		this.ui.setScreen(new ProjectMenuScreen());
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
