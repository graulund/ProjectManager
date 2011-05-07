package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import projectmanager.app.Activity;
import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class ChooseActivityScreen extends Screen {
	private String[] choices;
	private Activity[] activities;
	
	String operation;
	int weekStart;
	int weekEnd;
	
	public ChooseActivityScreen(String operation, int weekStart, int weekEnd) {
		this.operation  = operation;
		this.weekStart  = weekStart;
		this.weekEnd    = weekEnd;
	}

	@Override
	void printMenu(PrintWriter out) {
		StringBuilder s = new StringBuilder(this.formatTitle("Activities"));
		Employee you = ProjectManagerApp.getEmployeeLoggedIn();
		List<Activity> activities = you.getActivities(this.weekStart, this.weekEnd);
		int size = activities.size();
		if (size > 0) {
			s.append("You are working on following activies: \n");
			this.choices    = new String[size];
			this.activities = new Activity[size];
			for (int i = 0; i < size; i++) {
				Activity activity = activities.get(i);
				this.choices[i]    = activity.getName();
				this.activities[i] = activity;
			}
		} else {
			s.append("You aren't working on any activities in the given week(s)");
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
				this.ui.setScreen(new EditRegisteredTimeScreen(this.activities[selection-1]));
			} else {
				this.wrongInputMessage(out);
			}
		return false;
		}
		return false;
	}
}
