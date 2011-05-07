package projectmanager.app;

public class DelegatedWork extends Work {
	private int halfHoursWorked;
	
	public DelegatedWork(int halfhours, Activity activity){
		this(halfhours, activity, ProjectManagerApp.newSerialNumber());
	}
	public DelegatedWork(int halfhours, Activity activity, int serialNumber){
		this.serialNumber = serialNumber;
		this.halfHoursWorked = halfhours;
		this.activity = activity;
	}
	public int getHalfHoursWorked() {
		return halfHoursWorked;
	}
	public void setHalfHoursWorked(int halfhours) {
		this.halfHoursWorked = halfhours;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public void setSerialNumber(int serialKey) {
		this.serialNumber = serialKey;
	}
	public int getSerialNumber() {
		return this.serialNumber;
	}
}
