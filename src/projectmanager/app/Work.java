package projectmanager.app;

/**
 * Class that holds any given amount of work that has been registered from or delegated to an employee.
 */
public abstract class Work {
	/**
	 * Link to Activity object on which this work has been performed.
	 */
	protected Activity activity;
	
	/**
	 * The serial number to identify this work.
	 */
	protected int serialNumber;
}
