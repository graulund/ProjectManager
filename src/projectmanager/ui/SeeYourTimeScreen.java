package projectmanager.ui;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;


public class SeeYourTimeScreen extends Screen {
	//private String[] choices;
	//private int[] numbers;
	int startWeek, endWeek;
	int startYear, endYear;

	public SeeYourTimeScreen(int startWeek, int startYear, int endWeek, int endYear) {
		this.startWeek = startWeek;
		this.endWeek   = endWeek;
		this.startYear = startYear;
		this.endYear   = endYear;
	}

	@Override
	void printMenu(PrintWriter out) {
		//Calendar now = GregorianCalendar.getInstance();
		this.println(out, 
			this.formatTitle("Your Time Overview") +
			this.employeeTimeTable(new Employee[]{ ProjectManagerApp.getEmployeeLoggedIn() }, this.startWeek, this.startYear, this.endWeek, this.endYear) +
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
