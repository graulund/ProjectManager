package projectmanager.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

abstract class Screen {
	public ProjectManagerUI ui;
	protected int chars = 0;
	public String readInput(BufferedReader in) throws IOException {
		return in.readLine();
	}
	abstract void printMenu(PrintWriter out);
	abstract boolean processInput(String input, PrintWriter out);
	
	// General screen utilities
	protected void println(PrintWriter out, String s){
		out.println(s);
		chars += s.length();
	}
	protected void clearScreen(PrintWriter out){
		StringBuilder clear = new StringBuilder();
		for(int i = 0; i < chars; i++){
			clear.append("\b");
		}
		out.println(clear.toString());
	}
	protected String wrong = "Wrong selection; please repeat.";
	protected void wrongInputMessage(PrintWriter out){
		out.println(this.wrong);
	}
}
