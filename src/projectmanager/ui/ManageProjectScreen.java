package projectmanager.ui;

import java.io.PrintWriter;

import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class ManageProjectScreen extends Screen {
	private int serialNumber;
	private Project project;

	public ManageProjectScreen(int serialNumber) {
		this.serialNumber = serialNumber;
		this.project      = ProjectManagerApp.getCompany().projectBySerialNumber(serialNumber);
	}

	@Override
	void printMenu(PrintWriter out) {
		Project p = this.project;
		this.println(out, 
			this.formatTitle(p.getName()) +
			p.getReport() + "\n" +
			this.menuString(new String[]{ "Browse activities", "Add activity", "Add project leader", "Print report" }, "Back")
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		switch(selection){
			case 0:
				this.ui.setScreen(new ProjectMenuScreen());
				break;
			
		}
		return false;
	}

}
