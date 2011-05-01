package projectmanager.ui;

import java.io.PrintWriter;

public class LoginScreen extends Screen {

	@Override
	void printMenu(PrintWriter out) {
		out.println(
		"== WELCOME TO THE PROJECT MANAGER\n" +
		"You need to log in to continue.\n" + 
		"Please type your initials below to proceed:"
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		System.out.println("Trying to log in as user '" + input + "'");
		System.exit(0); //DEBUG
		return false;
	}
}
