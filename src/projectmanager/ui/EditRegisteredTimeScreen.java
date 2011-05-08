package projectmanager.ui;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import projectmanager.app.Activity;
import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;
import projectmanager.app.RegisteredWork;


public class EditRegisteredTimeScreen extends Screen {
//	private String[] choices;
//	private RegisteredWork[] regworks;
//	
//	int weekStart, weekEnd;
//	int yearStart, yearEnd;
//
//	public EditRegisteredTimeScreen(int weekStart, int yearStart, int weekEnd,int yearEnd) {
//		this.weekStart = weekStart;
//		this.weekEnd   = weekEnd;
//		this.yearStart = yearStart;
//		this.yearEnd   = yearEnd;
//	}
	private RegisteredWork regwork;

	public EditRegisteredTimeScreen(RegisteredWork regwork) {
		this.regwork = regwork;
	}

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
				this.formatTitle("Edit Registered Time") +
				"Time format: HH:MM"
			);
		String start = this.regwork.getStartTime().get(Calendar.HOUR_OF_DAY)+":"
					  		+ this.regwork.getStartTime().get(Calendar.MINUTE);
		String end   = this.regwork.getEndTime().get(Calendar.HOUR_OF_DAY)+":"
  							+ this.regwork.getEndTime().get(Calendar.MINUTE);
		String[] in = { "-1" };
		try {
			 in = this.inputSequence(
				new String[]{
					"Start time",
					"End time"
				}, 
				new String[]{
					start,
					end
				}
			);
		} catch (IOException e) {}
		System.out.println(Arrays.toString(in));
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		return false;
	}
}
