package projectmanager.ui;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import projectmanager.app.Activity;
import projectmanager.app.ProjectManagerApp;
import projectmanager.app.ProjectManagerException;
import projectmanager.app.RegisteredWork;


public class RegisterTimeScreen extends Screen {
	Activity activity;
	
	public RegisterTimeScreen(Activity activity) {
		this.activity = activity;
	}

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
			this.formatTitle("Register Time") +
			"Date format: DD/MM/YYYY. Time format: HH:MM"
		);
		String[] in = { "-1" };
		try {
			 in = this.inputSequence(
				new String[]{
					"Date",
					"Start time",
					"End time"
				}, 
				new String[]{
					"Now",
					null,
					"Now"
				}
			);
		} catch (IOException e) {}
		if (this.isValidWorkInput(in[0], in[1], in[2], out)) {
			String endTime = in[2];
			String date    = in[0];
			if (date.toLowerCase().equals("now")) {
				Calendar now = GregorianCalendar.getInstance();
				date = now.get(Calendar.DATE)+"/"+(now.get(Calendar.MONTH)+1)+"/"+now.get(Calendar.YEAR);
			}
			if (endTime.toLowerCase().equals("now")) {
				Calendar now = GregorianCalendar.getInstance();
				endTime = ""
					+ now.get(Calendar.HOUR_OF_DAY)+":"
					+ now.get(Calendar.MINUTE);
			}
			RegisteredWork regwork = createRegWork(date, in[1], endTime, out);
			if (registerWork(regwork, out) == true) {
				this.println(out, 
						"You've registered work the "+date+" (week "+regwork.getDate().get(Calendar.WEEK_OF_YEAR)+")"
					  + " from "+in[1]+" to "+endTime
					  + " at the activity \""+regwork.getActivity().getName()+"\".");
			}
		}
		this.clearScreen(out);
		this.ui.setFlow();
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		if (selection == 0) {
			this.ui.setScreen(new TimeMenuScreen());
		}
		return false;
	}
	
	private RegisteredWork createRegWork(String dateIn, String startTime, String endTime, PrintWriter out) {
		// splitter inputtet
		int[] date  = this.parseDateInput(dateIn, out);
		int[] start = this.parseTimeInput(startTime, out);
		int[] end   = this.parseTimeInput(endTime, out);
		
		// registrerer tiden
		Calendar startCal = ProjectManagerApp.createCalendar(
				date[0], date[1], date[2], start[0], start[1]			                                         
		);
		Calendar endCal = ProjectManagerApp.createCalendar(
				date[0], date[1], date[2], end[0], end[1]
		);
		return new RegisteredWork(activity, startCal, endCal);
	}

	private boolean registerWork(RegisteredWork regwork, PrintWriter out) {
		// pr�ver at registrere tiden. returnere false hvis den er invalid. 
		try {
			ProjectManagerApp.registerWork(regwork);
			return true;
		} catch (ProjectManagerException e) {
			this.println(out,
					e.getMessage()
			);
		}
		return false;
	}
	
	private boolean isValidWorkInput(String dateIn, String startTime, String endTime, PrintWriter out) {
		int[] date  = this.parseDateInput(dateIn, out);
		int[] start = this.parseTimeInput(startTime, out);
		int[] end   = this.parseTimeInput(endTime, out);
		
		if (date[0] == -1 || start[0] == -1 || end[0] == -1) {
			this.println(out, this.wrong);
			return false;
		} else {
			return true;
		}	
	}
	
	
}
