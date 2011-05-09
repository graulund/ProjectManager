package projectmanager.app;

/**
 * Delegated Work is created when a project-leader
 * delegates a work to a given employee
 */
public class DelegatedWork extends Work {
	/**
	 * The expected half hours that the given
	 * employee shall work
	 */
	private int halfHoursWorked;
	
	/**
	 * Constructor of Delegated work
	 * @param halfhours
	 * @param activity
	 */
	public DelegatedWork(int halfhours, Activity activity){
		this(halfhours, activity, ProjectManagerApp.newSerialNumber());
	}
	
	/**
	 * Constructor of Delegated Work with given serialNumber
	 * @param halfhours
	 * @param activity
	 * @param serialNumber
	 */
	public DelegatedWork(int halfhours, Activity activity, int serialNumber){
		this.serialNumber = serialNumber;
		this.halfHoursWorked = halfhours;
		this.activity = activity;
	}
	
	/**
	 * Returns the expected half hours to
	 * work on this activity
	 * @return half hours expected to work
	 */
	public int getHalfHoursWorked() {
		return halfHoursWorked;
	}
	
	/**
	 * Sets the expected half hours to
	 * work on this activity
	 * @param halfhours
	 */
	public void setHalfHoursWorked(int halfhours) {
		this.halfHoursWorked = halfhours;
	}
	
	/**
	 * Returns the activity
	 * @return activity
	 */
	public Activity getActivity() {
		return activity;
	}
	
	/**
	 * Sets the activity
	 * @param activity
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	/**
	 * Sets the serialnumber
	 * @param serialKey
	 */
	public void setSerialNumber(int serialKey) {
		this.serialNumber = serialKey;
	}
	
	/**
	 * Returns the serialnumber
	 * @return serialnumber
	 */
	public int getSerialNumber() {
		return this.serialNumber;
	}
}
