package projectmanager.ui;

import java.io.PrintWriter;

public class ManageProjectScreen extends Screen {
	private int serialNumber;

	public ManageProjectScreen(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	void printMenu(PrintWriter out) {
		// TODO Auto-generated method stub

	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		// TODO Auto-generated method stub
		return false;
	}

}
