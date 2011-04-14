package projectmanager.app;

public class AlreadyRegisteredWorkException extends Exception {
	String operation;
	
	public AlreadyRegisteredWorkException(String operation) {
		super(operation + " operation not allowed if not admin.");
		this.operation = operation;
	}

	public String getOperation() {
		return this.operation;
	}

}
