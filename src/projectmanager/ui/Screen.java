package projectmanager.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

abstract class Screen {
	public ProjectManagerUI ui;
	public String readInput(BufferedReader in) throws IOException {
		return in.readLine();
	}
	abstract void printMenu(PrintWriter out);
	abstract boolean processInput(String input, PrintWriter out);
	
	// General screen utilities
	protected String wrong = "Wrong selection; please repeat.";
	protected void wrongInputMessage(PrintWriter out){
		out.println(this.wrong);
	}
}
