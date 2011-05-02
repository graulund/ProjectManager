package projectmanager.app;

public class DelegatedWork extends Work {
	private int halfHoursWorked;
	
	public DelegatedWork(int hours, Activity activity){
		this.serialNumber = Company.c.newSerialNumber();
		this.halfHoursWorked = hours;
		this.activity = activity;
	}
	public int getHalfHoursWorked() {
		return halfHoursWorked;
	}
	public void setHalfHoursWorked(int hours) {
		this.halfHoursWorked = hours;
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
