package projectmanager.app;

/**
 * This exception is a generel exception
 * for most of the exceptions in this application
 */
public class ProjectManagerException extends Exception {
	String message;
	public ProjectManagerException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
