package projectmanager.ui;
import java.io.PrintWriter;
import java.util.List;

import projectmanager.app.Employee;
import projectmanager.app.Project;


public class SeePeopleTimeScreen extends Screen {
	Project project;
	int startWeek, endWeek;
	int startYear, endYear;

	public SeePeopleTimeScreen(Project project, int startWeek, int startYear, int endWeek, int endYear) {
		this.project   = project;
		this.startWeek = startWeek;
		this.endWeek   = endWeek;
		this.startYear = startYear;
		this.endYear   = endYear;
	}

	@Override
	void printMenu(PrintWriter out) {
		//Calendar now = GregorianCalendar.getInstance();
		List<Employee> employees = project.getEmployees();
		this.println(out, 
			this.formatTitle("Employees Time Overview") +
			this.employeeTimeTable(employees.toArray(new Employee[employees.size()]), this.startWeek, this.startYear, this.endWeek, this.endYear) +
			this.menuString(new String[]{}, "Back")
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		switch(selection) {
			case 0:
				this.ui.setScreen(new ManageProjectScreen(project.getSerialNumber()));
				break;
		}
		return false;
	}
}
