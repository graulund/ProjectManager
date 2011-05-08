package projectmanager.ui;
import java.io.PrintWriter;
import java.util.Calendar;

import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;


public class SeeYourTimeScreen extends Screen {
	//private String[] choices;
	//private int[] numbers;
	int startWeek, endWeek;

	public SeeYourTimeScreen(int startWeek, int endWeek) {
		this.startWeek = startWeek;
		this.endWeek   = endWeek;
	}

	@Override
	void printMenu(PrintWriter out) {
		Calendar now = Calendar.getInstance();
		this.println(out, 
			this.formatTitle("Your Time Overview") +
			this.employeeTimeTable(new Employee[]{ ProjectManagerApp.getEmployeeLoggedIn() }, now.get(Calendar.YEAR), this.startWeek, this.endWeek) +
			this.menuString(new String[]{}, "Back")
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		switch(selection) {
			case 0:
				this.ui.setScreen(new TimeMenuScreen());
				break;
		}
		return false;
	}

}
