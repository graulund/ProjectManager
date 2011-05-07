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
		
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		this.clearScreen(out);
		String[] in = { "-1" };
		try {
			 in = this.inputSequence(
				new String[]{
					"Date",
					"Start time",
					"End time"
				}, 
				new String[]{
					null,
					null,
					"Now"
				}
			);
			System.out.println(Arrays.toString(in));
		} catch (IOException e) {}
		RegisteredWork regwork = createRegWork(in[0], in[1], in[2], out);
		registerWork(regwork, out);
		this.ui.setScreen(new TimeMenuScreen());
		return false;
	}
	
	private RegisteredWork createRegWork(String dateIn, String startTime, String endTime, PrintWriter out) {
		// splitter inputtet
		int[] date  = this.parseDateInput(dateIn, out);
		int[] start = this.parseTimeInput(startTime, out);
		int[] end   = this.parseTimeInput(endTime, out);
		
		// registrerer tiden
		Calendar startCal = ProjectManagerApp.createCalendar(
				date[2], date[1], date[0], start[0], start[1]			                                         
		);
		Calendar endCal = ProjectManagerApp.createCalendar(
				date[2], date[1], date[0], end[0], end[1]
		);
		return new RegisteredWork(activity, startCal, endCal);
	}

	private void registerWork(RegisteredWork regwork, PrintWriter out) {
		// pr¿ver at registrere tiden. returnere false hvis den er invalid. 
		try {
			ProjectManagerApp.registerWork(regwork);
		} catch (ProjectManagerException e) {
			this.println(out,
					e.getMessage()
			);
		}
	}
	
	
}
