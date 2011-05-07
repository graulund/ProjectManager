package projectmanager.ui;

import java.io.PrintWriter;

import projectmanager.app.Activity;

public class TimeMenuScreen extends Screen {
	

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
			this.formatTitle("Manage time") +
			this.menuString(new String[]{ "Register time", "Edit registered time", "See when you've got time" }, "Back")
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		switch(selection){
			case 0:
				this.ui.setScreen(new MainMenuScreen());
				break;
			case 1:
				//this.ui.setScreen(new RegisterTimeScreen(new Activity("lulz")));
				this.ui.setScreen(new ChooseWorkWeekScreen("Register"));
				break;
			case 2:
				this.ui.setScreen(new ChooseWorkWeekScreen("Edit"));
				break;
			case 3:
				this.ui.setScreen(new ChooseWorkWeekScreen("SeeTime"));
				break;
		}
		return false;
	}

}
