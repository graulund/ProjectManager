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
	int weekStart, weekEnd;
	int yearStart, yearEnd;
	
	public ChooseActivityScreen(String operation, int weekStart, int yearStart, int weekEnd, int yearEnd) {
		this.operation  = operation;
		this.weekStart  = weekStart;
		this.weekEnd    = weekEnd;
		this.yearStart  = yearStart;
		this.yearEnd    = yearEnd;
	}

	@Override
	void printMenu(PrintWriter out) {
		StringBuilder s = new StringBuilder(this.formatTitle("Activities"));
		if (this.weekStart == this.weekEnd) 
			s.append("Week: "+this.weekStart+"/"+this.yearStart+"\n");
		else
			s.append("Weeks: "+this.weekStart+"/"+this.yearStart+" - "+this.weekEnd+"/"+this.yearEnd+"\n");
		
		Employee you = ProjectManagerApp.getEmployeeLoggedIn();
		Activity act = new Activity("TestActivity");
		you.addDelegatedWork(27, 2011, act, 10);
		List<Activity> activities = you.getActivities(this.weekStart, this.yearStart, this.weekEnd, this.yearEnd);
		int size = activities.size();
		if (size > 0) {
			s.append("You are working on following activies \n");
			this.activities = new Activity[size];
			this.choices    = new String[size];
			for (int i = 0; i < size; i++) {
				Activity activity = activities.get(i);
				this.choices[i]    = activity.getName();
				this.activities[i] = activity;
			}
			s.append(this.menuString(this.choices, "Back"));
		} else {
			s.append("You aren't working on any activities in the given week(s):\n");
			s.append(this.menuString(new String[] { "-1" }, "Back"));
		}
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
		
		if(this.choices == null) this.choices = new String[] { "-1" }; // else compiler error
		if(this.choices.length > 0){
			if(selection < (this.choices.length + 1) && selection >= 0){
				this.ui.setScreen(this.getNextScreen(this.activities[selection-1]));
				this.ui.setFlow();
			} else {
				this.wrongInputMessage(out);
			}
		return false;
		}
		return false;
	}
	
	private Screen getNextScreen(Activity activity) {
		if (this.operation.equals("Register")) {
			return new RegisterTimeScreen(activity);
		} else if (this.operation.equals("Edit")) {
			return new EditRegisteredTimeScreen(this.weekStart, this.yearStart, this.weekEnd, this.yearEnd);
		} else {
			return new TimeMenuScreen();
		}
	}
}
