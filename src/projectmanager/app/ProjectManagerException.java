package projectmanager.app;

public class ProjectManagerException extends Exception {
	String message;
	public ProjectManagerException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
