package projectmanager.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.ProjectManagerApp;

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
	protected void println(String s, PrintWriter out){
		this.println(out, s);
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
	protected void exit(PrintWriter out){
		out.println("Goodbye!");
		ProjectManagerApp.exit();
	}
	protected int parseNumberInput(String input, PrintWriter out){
		int selection = -1;
		try {
			selection = Integer.parseInt(input);
		} catch(NumberFormatException e){
			this.wrongInputMessage(out);
		}
		return selection;
	}
	protected String[] inputSequence(String[] inputs, String[] defaults) throws IOException {
		if(inputs.length <= 0){ return new String[]{ null }; }
		String[] in = new String[inputs.length];
		String def  = null;
		for(int i = 0; i < inputs.length; i++){
			def   = (defaults.length > i) ? defaults[i] : null;
			System.out.print("> " + inputs[i] + (def != null ? " [" + def + "]" : "") + ": ");
			in[i] = this.readInput(this.ui.in);
			if(in[i].isEmpty() && def != null){ 
				in[i] = def;
			}
		}
		return in;
	}
	protected String[] inputSequence(String[] inputs) throws IOException {
		return this.inputSequence(inputs, new String[]{ null });
	}
	public String menuString(String[] choices, String cancel){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < choices.length; i++){
			s.append((i+1) + ") " + choices[i] + "\n");
		}
		if(cancel != null && !cancel.isEmpty()){
			s.append("0) " + cancel);
		}
		return s.toString();
	}
	public String menuString(String[] choices){
		return menuString(choices, "Cancel");
	}
	public String formatTitle(String title){
		return "== " + title.toUpperCase() + "\n";
	}
}
