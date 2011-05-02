package projectmanager.app;

public class CreatingActivityException extends Exception {
	public CreatingActivityException(String message) {
		super("An activity with the desired name already exists.");
	}
}
