package projectmanager.app;

public class OperationNotAllowedException extends Exception {
	String operation;
	
	public OperationNotAllowedException(String operation) {
		super(operation + " not allowed if not projectmanager.");
		this.operation = operation;
	}
	
	public String getOperation() {
		return this.operation;
	}
}
