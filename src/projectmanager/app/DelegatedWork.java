package projectmanager.app;

public class DelegatedWork {
	private int hours;
	private Activity activity;
	public DelegatedWork(int hours, Activity activity){
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
}
