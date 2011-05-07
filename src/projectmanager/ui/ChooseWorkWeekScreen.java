package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class ChooseWorkWeekScreen extends Screen {
	String operation;
	int startWeek, endWeek;
	
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
		
		startWeek = this.parseNumberInput(in[0], out);
		if (in[1].equals("Only 1 week")) endWeek = startWeek;
		else endWeek   = this.parseNumberInput(in[1], out);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		if (this.isValidWorkWeeks(startWeek, endWeek)) {
			this.clearScreen(out);
			this.ui.setScreen(this.getNextScreen());
		} else {
			this.clearScreen(out);
			this.println(out, this.wrong);
			this.ui.setScreen(new TimeMenuScreen());
		}
		return false;
	}
	
	private Screen getNextScreen() {
		if (this.operation.equals("Register")) {
			return new ChooseActivityScreen(this.operation, startWeek, endWeek);
		} else if (this.operation.equals("SeeTime")) {
			return new SeeYourTimeScreen(startWeek, endWeek);
		} else if (this.operation.equals("Edit")) {
			return new ChooseActivityScreen(this.operation, startWeek, endWeek);
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
