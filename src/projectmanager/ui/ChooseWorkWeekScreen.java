package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
		System.out.println(Arrays.toString(in));
		String[] startSplit = in[0].split("/");
		String[] endSplit	= in[1].split("/");
		if (startSplit.length == 2) {
			startWeek = this.parseNumberInput(startSplit[0], out);
			startYear = this.parseNumberInput(startSplit[1], out);
			if (endSplit.length == 2) {
				endWeek = this.parseNumberInput(endSplit[0], out);
				endYear = this.parseNumberInput(endSplit[1], out);
			} else if (endSplit[0].equals("Only 1 week")) {
				endWeek = startWeek;
				endYear = startYear;
			} else {
				this.println(out, this.wrong);
				this.clearScreen(out);
				this.ui.setScreen(new TimeMenuScreen());
			}
		} 
		if (this.isValidWorkWeeks(startWeek, endWeek)) {
			this.clearScreen(out);
			this.ui.setScreen(this.getNextScreen());
		} else {
			this.clearScreen(out);
			this.println(out, this.wrong);
			this.ui.setScreen(new TimeMenuScreen());
		}
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		return false;
	}
	
	private Screen getNextScreen() {
		if (this.operation.equals("Register")) {
			return new ChooseActivityScreen("Register", startWeek, startYear, endWeek, endYear);
		} else if (this.operation.equals("SeeTime")) {
			return new SeeYourTimeScreen(startWeek, endWeek);
		} else if (this.operation.equals("Edit")) {
			return new EditRegisteredTimeScreen(startWeek, endYear, endWeek, endYear);
		} else {
			return new TimeMenuScreen();
		}
	}
	
	private boolean isValidWorkWeeks(int week1, int week2) {
		if ((week1 > 0 && week1 < 54) && (week2 > 0 && week2 < 54))
			return true;
		else
			return false;
	}

}
