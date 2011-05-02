package projectmanager.app;

public class DelegatedWork {
	
	private int hours;
	private Activity activity;
	private int serialNumber;
	
	public DelegatedWork(int hours, Activity activity){
		this.serialNumber = Company.c.newSerialNumber();
		this.hours   = hours;
		this.activity = activity;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
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
