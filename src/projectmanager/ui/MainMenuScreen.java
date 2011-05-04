package projectmanager.ui;

import java.io.PrintWriter;

public class MainMenuScreen extends Screen {

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
			this.formatTitle("Project Manager") + 
			this.menuString(
				new String[]{ "Manage Projects", "Manage Time" }, 
			"Exit")
		);
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		switch(selection){
			case 0:
				System.exit(0);
				break;
			case 1:
				System.out.println("PROJECTS!");
				break;
			case 2:
				System.out.println("TIME!");
		}
		return false;
	}

}
