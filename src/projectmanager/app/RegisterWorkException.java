package projectmanager.app;

public class RegisterWorkException extends Exception {
	String operation;
	
	public RegisterWorkException(String operation) {
		super("You cannot register this work: "+operation);
		this.operation = operation;
	}
	
	public String getOperation() {
		return this.operation;
	}
	
	
	
	
}
