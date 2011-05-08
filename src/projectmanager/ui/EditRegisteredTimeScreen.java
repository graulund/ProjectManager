package projectmanager.ui;
import java.io.PrintWriter;

import projectmanager.app.Activity;
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
		this.println(out, 
				this.formatTitle("Edit Registered Time") +
				this.menuString(new String[]{ "" })
			);

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
			if(selection < this.choices.length && selection >= 0){
				this.ui.setScreen(this.getNextScreen(this.regworks[selection-1]));
			} else {
				this.wrongInputMessage(out);
			}
		return false;
		}
		return false;
	}
}
