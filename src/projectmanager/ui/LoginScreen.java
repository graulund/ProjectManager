package projectmanager.ui;

import java.io.PrintWriter;

import projectmanager.app.ProjectManagerApp;

public class LoginScreen extends Screen {

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
			this.formatTitle("Welcome to the Project Manager") +
			"You need to log in to continue.\n" + 
			"Please type your initials below to proceed:"
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		//System.out.println("Trying to log in as user '" + input + "'");
		if(ProjectManagerApp.employeeLogin(input)){
			//this.println(out, "You are now logged in!");
			this.clearScreen(out);
			this.ui.setScreen(new MainMenuScreen());
		} else {
			this.println(out, "Could not log you in. Please restart the app to try again.");
			System.exit(0);
		}
		return false;
	}
}
