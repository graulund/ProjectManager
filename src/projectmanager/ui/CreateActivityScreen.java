package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.Project;
import projectmanager.app.Activity;


public class CreateActivityScreen extends Screen {
	private Project project;

	public CreateActivityScreen(Project project) {
		this.project = project;
	}
	
	
	@Override
	void printMenu(PrintWriter out) {
		this.println(out, this.formatTitle("Create activity"));
		
		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
						"Name of activity"
					}
				);
		} catch (IOException e) {}
		
		// Create activity
		Activity activity = new Activity(in[0]);
		try {
			project.addActivity(activity);
		} catch (Exception e) {}
		
		this.println(out, "Activity added.\n");
		
		// perhaps send to just created activity instead ?
		this.ui.setScreen(new ManageActivitiesScreen(in[0], project));
		try {
			this.ui.printMenu(out);
		} catch (IOException e) {}
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		return false;
	}

}
