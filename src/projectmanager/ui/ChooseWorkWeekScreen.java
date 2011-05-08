package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ChooseWorkWeekScreen extends Screen {
	String operation;
	int startWeek, endWeek;
	int startYear, endYear;
	
	public ChooseWorkWeekScreen(String operation) {
		this.operation = operation;
	}
	
	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
				this.formatTitle("Select week(s)")
			  + "Week/Year format: WW/YYYY"
		);
		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
						"Starting week",
						"Ending week"
					}, 
					new String[]{
						null,
						"Only 1 week"
					}
				);
		} catch (IOException e) {}
		String[] startSplit = in[0].split("/");
		String[] endSplit	= in[1].split("/");
		if (startSplit.length == 2) {
			startWeek = this.parseNumberInput(startSplit[0], out);
			startYear = this.parseNumberInput(startSplit[1], out);
			
			
		} else if (startSplit.length == 1) {
			Calendar now = GregorianCalendar.getInstance();
			startWeek = this.parseNumberInput(startSplit[0], out);
			startYear = now.get(Calendar.YEAR);
			if (endSplit.length == 2) {
				endWeek = this.parseNumberInput(endSplit[0], out);
				endYear = this.parseNumberInput(endSplit[1], out);
			} else if (endSplit[0].equals("Only 1 week")) {
				endWeek = startWeek;
				endYear = startYear;
			} else if (endSplit.length == 1) {
				endWeek = this.parseNumberInput(endSplit[0], out);
				endYear = startYear;
			}
		}
		this.ui.setFlow();
		
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		if (selection == 0) {
			if (this.isValidWeekInput(startWeek, startYear, endWeek, endYear)) {
				this.clearScreen(out);
				this.ui.setScreen(this.getNextScreen());
			} else {
				this.clearScreen(out);
				this.println(out, this.wrong);
				this.ui.setScreen(new TimeMenuScreen());
			}
		}
		return false;
	}

	private Screen getNextScreen() {
		if (this.operation.equals("Register")) {
			return new ChooseActivityScreen("Register", startWeek, startYear, endWeek, endYear);
		} else if (this.operation.equals("SeeTime")) {
			return new SeeYourTimeScreen(startWeek, startYear, endWeek, endYear);
		} else if (this.operation.equals("Edit")) {
			return new ChooseRegisteredWork(startWeek, endYear, endWeek, endYear);
		} else {
			return new TimeMenuScreen();
		}
	}
	
	private boolean checkWeekInput() {
		if (startYear == endYear && endWeek < startWeek) return false;
		else return true;
	}
}
