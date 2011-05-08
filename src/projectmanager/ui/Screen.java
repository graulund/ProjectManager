package projectmanager.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import projectmanager.app.DelegatedWork;
import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;
import projectmanager.app.RegisteredWork;
import projectmanager.app.WorkWeek;

/**
 * Class that represents any screen being displayed to the user in the UI. Subclasses must be created for every screen.
 */
abstract class Screen {
	/**
	 * Link to the main UI object.
	 */
	public ProjectManagerUI ui;
	
	/**
	 * Number of characters that has been outputted.
	 */
	protected int chars = 0;
	
	/**
	 * Reads input from the user.
	 * @param in BufferedReader object being read from.
	 * @return String
	 * @throws IOException
	 */
	public String readInput(BufferedReader in) throws IOException {
		return in.readLine();
	}
	
	/**
	 * Prints the menu/information on this screen.
	 * @param out PrintWriter object being written to.
	 */
	abstract void printMenu(PrintWriter out);
	
	/**
	 * Processes the input from the user.
	 * @param input Input from the user given as a String.
	 * @param out PrintWriter object that can be written to.
	 * @return boolean describing whether it's okay to quit the app.
	 */
	abstract boolean processInput(String input, PrintWriter out);
	
	// GENERAL SCREEN UTILITIES ---------------------------------------------------------------------------------
	
	/**
	 * Prints a line to the PrintWriter and increments the number of characters printed.
	 * @param out PrintWriter object being written to.
	 * @param s String being written.
	 */
	protected void println(PrintWriter out, String s){
		out.println(s);
		chars += s.length();
	}
	
	/**
	 * Prints a line to the PrintWriter and increments the number of characters printed.
	 * @param s String being written.
	 * @param out PrintWriter object being written to.
	 */
	protected void println(String s, PrintWriter out){
		this.println(out, s);
	}
	
	/**
	 * Clears the screen.
	 * @param out PrintWriter object being written to.
	 */
	protected void clearScreen(PrintWriter out){
		StringBuilder clear = new StringBuilder();
		for(int i = 0; i < this.chars; i++){
			clear.append("\b");
		}
		out.println(clear.toString());
		this.chars = 0;
	}
	
	/**
	 * Message being displayed when input selection is wrong
	 */
	protected String wrong = "Wrong selection; please repeat.";
	
	/**
	 * Displays wrong input selection message.
	 * @param out PrintWriter object being written to.
	 */
	protected void wrongInputMessage(PrintWriter out){
		out.println(this.wrong);
	}
	
	/**
	 * Exits the UI and the application.
	 * @param out PrintWriter object being written to.
	 */
	protected void exit(PrintWriter out){
		out.println("Goodbye!");
		ProjectManagerApp.exit();
	}
	
	/**
	 * Parses any number being input by the user.
	 * @param input String being input by the user.
	 * @param out PrintWriter object that can be written to.
	 * @return Parsed integer selection number; returns -1 if not a number.
	 */
	protected int parseNumberInput(String input, PrintWriter out){
		int selection = -1;
		try {
			selection = Integer.parseInt(input);
		} catch(NumberFormatException e){
			this.wrongInputMessage(out);
		}
		return selection;
	}
	
	/**
	 * Parses any date being input by the user.
	 * @param date String being input by the user.
	 * @param out PrintWriter object that can be written to.
	 * @return Integer array with year, month and day.
	 */
	protected int[] parseDateInput(String date, PrintWriter out) {
		String theDate = date;
		if (theDate.toLowerCase().equals("now")) {
			Calendar now = GregorianCalendar.getInstance();
			theDate = now.get(Calendar.DATE)+"/"+(now.get(Calendar.MONTH)+1)+"/"+now.get(Calendar.YEAR);
		}
		String[] dateSplit = theDate.split("/");
		if (dateSplit.length == 3) {
			int year  = this.parseNumberInput(dateSplit[2], out);
			int month = this.parseNumberInput(dateSplit[1], out);
			int day   = this.parseNumberInput(dateSplit[0], out);
			if (isValidDate(year, month, day))
				return new int[] { year, month, day };
		} 
		return new int[] { -1, -1, -1 };
		
	}
	
	/**
	 * Determines whether or not the given year, month and day are correct.
	 * @param year 
	 * @param month
	 * @param day
	 * @return Boolean
	 */
	private boolean isValidDate(int year, int month, int day) {
		if (year < 1900 || year > 2500) return false;
		if (month < 0 || month > 11) return false;
		if (day <= 0 || day > 31) return false;
		return true;
	}
	
	/**
	 * Parses any time being input by the user.
	 * @param time String being input by the user.
	 * @param out PrintWriter object that can be written to.
	 * @return Integer array with hours and minutes.
	 */
	protected int[] parseTimeInput(String time, PrintWriter out) {
		String[] timeSplit = time.split(":");
		if (timeSplit.length == 2) {
			int hour = this.parseNumberInput(timeSplit[0], out);
			int min  = this.parseNumberInput(timeSplit[1], out);
			if (isValidTime(hour, min))
				return new int[] { hour, min };
		} else if (time.toLowerCase().equals("now")) {
			Calendar now = GregorianCalendar.getInstance();
			return new int[] { now.get(Calendar.HOUR_OF_DAY), 
							   now.get(Calendar.MINUTE) };
		}
		return new int[] { -1, -1 };
	}
	
	/**
	 * Determines whether or not the given hour and minute constitutes a valid time.
	 * @param hour
	 * @param min
	 * @return Boolean
	 */
	private boolean isValidTime(int hour, int min) {
		if (hour < 0 || hour > 24) return false;
		if (min  < 0 || min  > 60) return false;
		return true;
	}
	
	/**
	 * Asks for the user's input in response to the given string array of input names.
	 * @param inputs Array of input names as Strings.
	 * @param defaults Array of default values if any (null is no default, "" is empty default).
	 * @return The user's input in response to all the input requests.
	 * @throws IOException
	 */
	protected String[] inputSequence(String[] inputs, String[] defaults) throws IOException {
		if(inputs.length <= 0){ return new String[]{ null }; }
		String[] in = new String[inputs.length];
		String def  = null;
		for(int i = 0; i < inputs.length; i++){
			def   = (defaults.length > i) ? defaults[i] : null;
			System.out.print("" + inputs[i] + (def != null ? " [Default: " + def + "]" : "") + ": ");
			in[i] = this.readInput(this.ui.in);
			if(in[i].isEmpty() && def != null){ 
				in[i] = def;
			}
		}
		return in;
	}
	
	/**
	 * Asks for the user's input in response to the given string array of input names.
	 * @param inputs Array of input names as Strings.
	 * @return The user's input in response to all the input requests.
	 * @throws IOException
	 */
	protected String[] inputSequence(String[] inputs) throws IOException {
		return this.inputSequence(inputs, new String[]{ null });
	}
	
	/**
	 * Builds a menu based on the given string array of choices.
	 * @param choices Array of choices as Strings.
	 * @param cancel Name for the "cancel" menu item.
	 * @return Menu as string, ready to be outputted.
	 */
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
	
	/**
	 * Builds a menu based on the given string array of choices.
	 * @param choices Array of choices as Strings.
	 * @return Menu as string, ready to be outputted.
	 */
	public String menuString(String[] choices){
		return menuString(choices, "Cancel");
	}
	
	/**
	 * Builds a time table based on the given array of employees and time factors.
	 * @param employees Array of employee objects.
	 * @param year The year of the given weeks.
	 * @param startWeek The starting week.
	 * @param endWeek The ending week.
	 * @return The time table as an ASCII-formatted multiline string.
	 */
	public String employeeTimeTable(Employee[] employees, int startWeek, int yearStart, int endWeek, int endYear){
		if(endWeek < startWeek){ return ""; }
		StringBuilder s  = new StringBuilder();
		StringBuilder p1 = new StringBuilder("+----+");
		StringBuilder p2 = new StringBuilder("|Week|");
		String sep;
		for(Employee e: employees){
			p1.append(this.stringRepeat("-", e.getUsername().length()) + "+");
			p2.append(e.getUsername() + "|");
		}
		sep = p1.toString() + "\n";
		s.append(sep);
		s.append(p2.toString() + "\n");
		s.append(sep);
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.WEEK_OF_YEAR, startWeek);
		cal.set(Calendar.YEAR, yearStart);
		Calendar cal2 = new GregorianCalendar();
		cal2.set(Calendar.WEEK_OF_YEAR, endWeek);
		cal2.set(Calendar.YEAR, endYear);
		cal2.add(Calendar.WEEK_OF_YEAR, 1);
		while (cal.before(cal2)) {
			int weekRun = cal.get(Calendar.WEEK_OF_YEAR);
			s.append("|" + weekRun + (weekRun < 10 ? "   " : "  ") + "|");
			for(Employee e: employees){
				WorkWeek w = e.getWorkWeek(weekRun, cal.get(Calendar.YEAR));
				int dHours = 0;
				if (w != null) {
					for (DelegatedWork dw: w.getDelegatedWork()) {
						dHours += dw.getHalfHoursWorked();
					}
				} 
				if (dHours > 0) {
					s.append(dHours + (dHours < 10 ? "   " : "  ") + "|");
				} else {
					s.append("    |");
				}
			}
			cal.add(Calendar.WEEK_OF_YEAR, 1);
			s.append("\n" + sep);
		}
		s.append("Empty cells are clear, filled cells represent delegated work (half hours)\n");
		return s.toString();
	}
	
	public String employeeTimeTable(Employee[] employees, int startWeek, int endWeek, int year){
		return this.employeeTimeTable(employees, startWeek, year, endWeek, year);
	}
	
	/**
	 * Formats a title to be displayed as part of the screen layout.
	 * @param title
	 * @return Formatted title
	 */
	public String formatTitle(String title){
		return this.formatTitle(title, "-");
	}
	
	public String formatTitle(String title, String sepchar){
		String t = title.trim().toUpperCase();
		if(sepchar.length() > 1){ sepchar.charAt(0); }
		return sepchar + sepchar + " " + t + " " + this.stringRepeat(sepchar, 65 - 4 - t.length()) + "\n";
	}
	
	/**
	 * Utility method that makes it easier to repeat a string a certain amount of times.
	 * @param str String (usually one character) to be repeated.
	 * @param times Times the string should be repeated.
	 * @return The repeated string.
	 */
	private String stringRepeat(String str, int times){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < times; i++){
			s.append(str);
		}
		return s.toString();
	}
	
	/**
	 * Method that returns true or false determited by whether
	 * the inputs are correct year-inputs
	 * @param yearStart
	 * @param endYear
	 * @return
	 */
	protected boolean isValidYear(int yearStart, int endYear) {
		return !(yearStart < 1900 || yearStart > 2100 ||
				 endYear   < 1900 || endYear   > 2100 ||
				 endYear < yearStart);
	}
	
	/**
	 * Method that return true or false determited by whether
	 * the inputs are correct week-inputs
	 * @param week1
	 * @param week2
	 * @return
	 */
	protected boolean isValidWorkWeeks(int week1, int week2) {
		return !(week1 < 1 || week1 > 53 || week2 < 1 || week2 > 53);
	}
	
	protected boolean isValidWeekInput(int startWeek, int startYear, int endWeek, int endYear) {
		if (startYear == endYear && endWeek < startWeek) {
			return false;
		}
		if (this.isValidWorkWeeks(startWeek, endWeek) &&
			this.isValidYear(startYear, endYear)) {
			return true;
		} else {
			return false;
		}
	}
}
