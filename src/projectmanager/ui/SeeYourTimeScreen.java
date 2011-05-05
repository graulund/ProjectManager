package projectmanager.ui;
import java.io.PrintWriter;


public class SeeYourTimeScreen extends Screen {

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
				this.formatTitle("See your time") +
				this.menuString(new String[]{ "" }, "Back")
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
