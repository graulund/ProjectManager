package projectmanager.ui;
import java.io.PrintWriter;

import projectmanager.app.Activity;


public class EditRegisteredTimeScreen extends Screen {
	Activity activity;
	
	public EditRegisteredTimeScreen(Activity activity) {
		this.activity = activity;
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
		switch(selection){
			case 0:
				this.ui.setScreen(new TimeMenuScreen());
				break;
		}
		return false;
	}
}
