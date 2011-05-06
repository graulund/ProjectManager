package projectmanager.ui;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


public class RegisterTimeScreen extends Screen {

	@Override
	void printMenu(PrintWriter out) {
		this.println(out, 
			this.formatTitle("Register Time")
		);
		try {
			String[] in = this.inputSequence(
				new String[]{
					"Date",
					"Start time",
					"End time"
				}, 
				new String[]{
					null,
					null,
					"Now"
				}
			);
			System.out.println(Arrays.toString(in));
		} catch (IOException e) {}
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
