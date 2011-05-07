package projectmanager.ui;

import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.ProjectManagerApp;

public class ChooseActivityScreen extends Screen {
	Screen screen;
	
	public ChooseActivityScreen(Screen screen) {
		this.screen = screen;
	}

	@Override
	void printMenu(PrintWriter out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
