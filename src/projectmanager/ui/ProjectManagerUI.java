package projectmanager.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.ProjectManagerApp;

public class ProjectManagerUI {
	private ProjectManagerApp app = new ProjectManagerApp();
	private Screen screen;
	public BufferedReader in;
	
	public ProjectManagerUI(){
		this.setScreen(new LoginScreen());
	}
	
	public ProjectManagerUI(Screen screen){
		this.setScreen(screen);
	}
	
	public void printMenu(PrintWriter out) throws IOException {
		this.screen.printMenu(out);
	}

	public String readInput(BufferedReader in) throws IOException {
		return this.screen.readInput(in);
	}

	public boolean processInput(String input, PrintWriter out) throws IOException {
		return this.screen.processInput(input, out);
	}

	public void basicLoop(BufferedReader in, PrintWriter out) throws IOException {
		String selection;
		do {
			printMenu(out);
			selection = readInput(in);
		} while (!processInput(selection, out));
	}

	void setScreen(Screen screen) {
		this.screen    = screen;
		this.screen.ui = this;
	}

	Screen getScreen() {
		return this.screen;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public ProjectManagerApp getApp() {
		return app;
	}
}
