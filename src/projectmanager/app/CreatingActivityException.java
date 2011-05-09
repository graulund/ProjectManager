package projectmanager.app;

/**
 * This exception is thrown when an error
 * related to creating or editing an activity occures
 */
public class CreatingActivityException extends ProjectManagerException {
	public CreatingActivityException(String message) {
		super(message);
	}
}
