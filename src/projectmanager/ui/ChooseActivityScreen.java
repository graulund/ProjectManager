package projectmanager.ui;

import java.io.PrintWriter;

public class ChooseActivityScreen extends Screen {
	Screen screen;
	
	public ChooseActivityScreen(Screen screen) {
		this.screen = screen;
	}
	
	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
			this.formatTitle("Choose activity")
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		switch(selection){
			case 0:
				this.ui.setScreen(new MainMenuScreen());
				break;
			case 1:
				this.ui.setScreen(new RegisterTimeScreen());
				break;
			case 2:
				this.ui.setScreen(new EditRegisteredTimeScreen());
				break;
			case 3:
				this.ui.setScreen(new SeeYourTimeScreen());
				break;
		}
		return false;
	}

}
