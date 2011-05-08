package projectmanager.ui;
import java.io.PrintWriter;
import java.util.List;

import projectmanager.app.Activity;
import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;
import projectmanager.app.RegisteredWork;


public class EditRegisteredTimeScreen extends Screen {
	private String[] choices;
	private RegisteredWork[] regworks;
	
	int weekStart, weekEnd;
	int yearStart, yearEnd;

	public EditRegisteredTimeScreen(int weekStart, int yearStart, int weekEnd,int yearEnd) {
		this.weekStart = weekStart;
		this.weekEnd   = weekEnd;
		this.yearStart = yearStart;
		this.yearEnd   = yearEnd;
	}

	@Override
	void printMenu(PrintWriter out) {
		StringBuilder s = new StringBuilder(this.formatTitle("Activities"));
		Employee you = ProjectManagerApp.getEmployeeLoggedIn();
		List<RegisteredWork> regworks = you.getRegisteredWork(this.weekStart, this.yearStart, this.weekEnd, this.yearEnd);
		int size = regworks.size();
		if (size > 0) {
			s.append("You are working on following activies \n");
			this.regworks = new RegisteredWork[size];
			this.choices    = new String[size];
			for (int i = 0; i < size; i++) {
				RegisteredWork regwork = regworks.get(i);
				this.choices[i]        = regwork.toString();
				this.regworks[i]       = regwork;
			}
			s.append(this.menuString(this.choices, "Back"));
		} else {
			s.append("You aren't working on any activities in the given week(s):\n");
			s.append(this.menuString(new String[] { "-1" }, "Back"));
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
		if (this.choices == null) this.choices = new String[] { "-1" };
		if(this.choices.length > 0){
			if(selection < (this.choices.length+1) && selection >= 0){
				//this.ui.setScreen(this.getNextScreen(this.regworks[selection-1]));
			} else {
				this.wrongInputMessage(out);
			}
		return false;
		}
		return false;
	}
}
