package projectmanager.ui;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import projectmanager.app.Activity;
import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;
import projectmanager.app.ProjectManagerException;
import projectmanager.app.RegisteredWork;

public class ChooseRegisteredWork extends Screen {
	private String[] choices;
	private RegisteredWork[] regworks;
	
	int weekStart, weekEnd;
	int yearStart, yearEnd;

	public ChooseRegisteredWork(int weekStart, int yearStart, int weekEnd,int yearEnd) {
		this.weekStart = weekStart;
		this.weekEnd   = weekEnd;
		this.yearStart = yearStart;
		this.yearEnd   = yearEnd;
	}

	@Override
	void printMenu(PrintWriter out) {
		StringBuilder s = new StringBuilder(this.formatTitle("Activities"));
		if (this.weekStart == this.weekEnd) 
			s.append("Week: "+this.weekStart+"/"+this.yearStart+"\n");
		else
			s.append("Weeks: "+this.weekStart+"/"+this.yearStart+" - "+this.weekEnd+"/"+this.yearEnd+"\n");
		Employee you = ProjectManagerApp.getEmployeeLoggedIn();
		List<RegisteredWork> regworks = you.getRegisteredWork(this.weekStart, this.yearStart, this.weekEnd, this.yearEnd);
		int size = regworks.size();
		if (size > 0) {
			s.append("You have registered the following work: \n");
			this.regworks = new RegisteredWork[size];
			this.choices    = new String[size];
			for (int i = 0; i < size; i++) {
				RegisteredWork regwork = regworks.get(i);
				this.choices[i]        = regwork.toString();
				this.regworks[i]       = regwork;
			}
			s.append(this.menuString(this.choices, "Back"));
		} else {
			s.append("You haven't registered any work in the given week(s):\n");
			s.append(this.menuString(new String[] {}, "Back"));
		}
		this.println(out, s.toString());
		
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		if(selection == 0){
			this.ui.setScreen(new MainMenuScreen());
			return false;
		}
		
		if(this.choices == null) this.choices = new String[] { "-1" }; // else compiler error
		if(this.choices.length > 0){
			if(selection < (this.choices.length + 1) && selection >= 0){
				this.ui.setScreen(new EditRegisteredTimeScreen(this.regworks[selection-1]));
			} else {
				this.wrongInputMessage(out);
			}
		return false;
		}
		return false;
	}

}
