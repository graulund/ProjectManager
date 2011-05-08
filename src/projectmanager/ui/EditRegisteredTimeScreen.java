package projectmanager.ui;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import projectmanager.app.Activity;
import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;
import projectmanager.app.ProjectManagerException;
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
		Calendar date = regwork.getDate();
		String dateString = date.get(Calendar.DATE)+"/"+(date.get(Calendar.MONTH)+1)+"/"+date.get(Calendar.YEAR);
		if (this.isValidWorkInput(dateString, in[0], in[1], out)) {
			int[] startTime = this.parseTimeInput(in[0], out);
			int[] endTime   = this.parseTimeInput(in[1], out);
			if (this.editRegisteredWork(regwork, startTime[0], startTime[1], endTime[0], endTime[1], out)) {
				this.println(out, 
						"You've edited a work on the "+dateString
					  + " at the activity \""+regwork.getActivity().getName()+"\""
					  + " from "+in[0]+" to "+in[1]
					  + ".");
			}
		}
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
	
	private boolean editRegisteredWork(RegisteredWork regwork, 
			int hourStart, int minStart, int hourEnd, int minEnd, PrintWriter out) {
		Employee you = ProjectManagerApp.getEmployeeLoggedIn();
		try {
			you.setRegisteredWork(regwork, hourStart, minStart, hourEnd, minEnd);
			return true;
		} catch (ProjectManagerException e) {
			this.println(out, e.getMessage());
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
