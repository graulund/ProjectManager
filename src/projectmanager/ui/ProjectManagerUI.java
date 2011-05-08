package projectmanager.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import projectmanager.app.ProjectManagerApp;

/**
 * Class that controls the UI of the Project Manager application.
 */
public class ProjectManagerUI {
	/**
	 * Link to the main App object.
	 */
	private ProjectManagerApp app = new ProjectManagerApp();
	
	/**
	 * Link to the current Screen object.
	 */
	private Screen screen;
	
	/**
	 * Link to the current BufferedReader object from which to get input from the user.
	 */
	public BufferedReader in;
	
	/**
	 * Boolean value determining whether we're in a flow that needs to change screen automatically.
	 */
	private boolean flow = false;
	
	/**
	 * Creates a new UI with the log in screen displayed.
	 */
	public ProjectManagerUI(){
		this.setScreen(new LoginScreen());
	}
	
	/**
	 * Creates a new UI.
	 * @param screen Screen object to be displayed.
	 */
	public ProjectManagerUI(Screen screen){
		this.setScreen(screen);
	}
	
	/**
	 * Prints the menu/information in the current screen.
	 * @param out PrintWriter object being written to.
	 * @throws IOException
	 */
	public void printMenu(PrintWriter out) throws IOException {
		this.screen.printMenu(out);
	}
	
	/**
	 * Reads user input into the current screen.
	 * @param in BufferedReader object being read from.
	 * @return String
	 * @throws IOException
	 */
	public String readInput(BufferedReader in) throws IOException {
		return this.screen.readInput(in);
	}
	
	/**
	 * Processes the input from the user in the screen.
	 * @param input Input from the user given as a String.
	 * @param out PrintWriter object that can be written to.
	 * @return boolean describing whether it's okay to quit the app.
	 * @throws IOException
	 */
	public boolean processInput(String input, PrintWriter out) throws IOException {
		return this.screen.processInput(input, out);
	}
	
	/**
	 * Runs the basic UI loop that prints the current menu and waits for input.
	 * @param in BufferedReader object being read from.
	 * @param out PrintWriter object being written to.
	 * @throws IOException
	 */
	public void basicLoop(BufferedReader in, PrintWriter out) throws IOException {
		String selection;
		do {
			this.printMenu(out);
			if(!this.flow){
				System.out.print("> ");
				selection = this.readInput(in);
			} else {
				this.flow = false;
				selection = "0";
			}
		} while (!this.processInput(selection, out));
	}
	
	/**
	 * Sets the current screen.
	 * @param screen Screen object.
	 */
	void setScreen(Screen screen) {
		this.screen    = screen;
		this.screen.ui = this;
		this.flow      = true;
	}
	
	/**
	 * Gets the current screen.
	 * @return Screen object.
	 */
	Screen getScreen() {
		return this.screen;
	}
	
	/**
	 * Gets the current input object.
	 * @return BufferedReader object being read from.
	 */
	public BufferedReader getIn() {
		return in;
	}
	
	/**
	 * Sets the current input object.
	 * @param in BufferedReader object being read from.
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
	/**
	 * Gets the App object.
	 * @return ProjectManagerApp object.
	 */
	public ProjectManagerApp getApp() {
		return app;
	}
}
