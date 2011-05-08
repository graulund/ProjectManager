package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.Activity;
import projectmanager.app.DelegatedWork;
import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class AddDelegatedWorkToActivityScreen extends Screen {
	private Project project;
	private Activity activity;
	
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
						"Hours of work to delegate"
					}
				);
		} catch (IOException e) {}

		// Add delegated work to activity
		int input = this.parseNumberInput(in[0], out);
		if (input == -1) {
			this.println(out, "");
		} else {
			DelegatedWork dw = new DelegatedWork(input, this.activity);
			activity.addDelegatedWork(dw);

			this.println(out, "Delegated work added to activity.\n");
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
