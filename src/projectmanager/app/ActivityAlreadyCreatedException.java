package projectmanager.app;

public class ActivityAlreadyCreatedException extends Exception {
	public ActivityAlreadyCreatedException(String message) {
		super("An activity with the desired name already exists.");
	}
}
