package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import projectmanager.app.Activity;
import projectmanager.app.Project;

public class SetDateStartOnActivityScreen extends Screen {
	private Project project;
	private Activity activity;
	Calendar calendar = GregorianCalendar.getInstance();
	
	public SetDateStartOnActivityScreen(Project project, Activity activity) {
		this.project = project;
		this.activity = activity;
	}

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, this.formatTitle("Set start date on "+this.activity.getName()) + "Some text.");

		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
							"Start week",
							"Start year"
					}, 
					new String[]{
							""+this.calendar.get(Calendar.WEEK_OF_YEAR),
							""+this.calendar.get(Calendar.YEAR)
					}
			);
		} catch (IOException e) {}
		
		// Parsing
		int week = -1;
		int year = -1;
		try {
			week = Integer.parseInt(in[0]);
			year= Integer.parseInt(in[1]);
		} catch (Exception e) {}
		
		// Check if all values are alright
		if (!this.isValidWorkWeeks(week, week) || !this.isValidYear(year, year)) {
			this.wrongInputMessage(out);
			this.println(out, "");
		} else {
			this.activity.setStart(week, year);
			this.println(out, "Start date set.\n");
		}
		
		// Go back
		this.ui.setScreen(new ManageActivitiesScreen(this.activity.getName(), this.project));
		try {
			this.ui.printMenu(out);
		} catch (IOException e) {}
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		return false;
	}

}
