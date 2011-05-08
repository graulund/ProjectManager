package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;

public class CreateEmployeeScreen extends Screen {

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, this.formatTitle("Add employee to company"));
		
		String[] in = new String[] {"-1"};
		try {
			in = this.inputSequence(
					new String[]{
						"Name of employee",
						"Username (2 to 4 letters)"
					}
				);
		} catch (IOException e) {}
		
		// Create employee
		Employee employee = new Employee(in[1], in[0]);
		ProjectManagerApp.getCompany().addEmployee(employee);
		
		this.println(out, "Employee hired.\n");
		
		
		// Back to MainMenuScreen
		this.ui.setScreen(new MainMenuScreen());
		try {
			this.ui.printMenu(out);
		} catch (IOException e) {}

	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		return false;
	}

}
