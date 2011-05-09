package projectmanager.app;

/**
 * This exception is thrown when a error related
 * to a operation which is not allowed by the employee
 */
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
