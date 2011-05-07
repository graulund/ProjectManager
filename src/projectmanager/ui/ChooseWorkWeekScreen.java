package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;

public class ChooseWorkWeekScreen extends Screen {
	String operation;
	
	public ChooseWorkWeekScreen(String operation) {
		this.operation = operation;
	}
	
	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
				this.formatTitle("Select week(s)")
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		this.clearScreen(out);
		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
						"Starting week",
						"Ending week"
					}, 
					new String[]{
						null,
						"Starting week only"
					}
				);
		} catch (IOException e) {}
		int size = in.length;
		int startWeek = -1, endWeek = -1;
		if (size == 1) {
			startWeek = this.parseNumberInput(in[0], out);
			endWeek = startWeek;
		} else if (size == 2) {
			startWeek = this.parseNumberInput(in[0], out);
			endWeek   = this.parseNumberInput(in[1], out);
		} else {
			// TODO: Insert error-message
		}
		
		// directing to next screen
		if (this.isValidWorkWeeks(startWeek, endWeek)) {
			this.ui.setScreen(new ChooseActivityScreen(this.operation, startWeek, endWeek));
		} else {
			this.ui.setScreen(new TimeMenuScreen());
		}
		return false;
		
	}
	
	private boolean isValidWorkWeeks(int week1, int week2) {
		if ((week1 > 0 && week1 < 54) && (week2 > 0 && week2 < 54))
			return true;
		else
			return false;
	}

}
