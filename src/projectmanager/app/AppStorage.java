package projectmanager.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import projectmanager.app.StoredData.StoredActivity;

public class AppStorage {
	public static final String default_filename = "pmapp.data";
	private String filename;
	public AppStorage(){
		this(default_filename);
	}
	public AppStorage(String filename){
		this.filename = filename;
	}
	public String getFilename(){
		return filename;
	}
	public void saveCurrentState(){
		StoredData data = new StoredData();
		// Save information for:
		
		// --> Company
		data.currentSerialNumber = Company.c.getCurrentSerialNumber();
		
		// --> Works
		ArrayList<StoredData.StoredWork> works = new ArrayList<StoredData.StoredWork>();
		
		// --> Projects
		ArrayList<StoredData.StoredProject> projects = new ArrayList<StoredData.StoredProject>();
		for(Project project: Company.c.getProjects()){
			StoredData.StoredProject p = data.new StoredProject();
			p.name              = project.getName().toCharArray();
			p.client            = project.getClient().toCharArray();
			p.serialNumber      = project.getSerialNumber();
			p.projectLeaderName = project.getLeader().getUsername().toCharArray();
			p.employeeNames     = this.getEmployeeNames(project.getEmployees());
			ArrayList<StoredData.StoredActivity> activities = new ArrayList<StoredData.StoredActivity>();
			for(Activity activity: project.getActivities()){
				StoredData.StoredActivity a = data.new StoredActivity();
				a.name          = activity.getName().toCharArray();
				a.employeeNames = this.getEmployeeNames(activity.getEmployees());
				//a.workIds     = ;
				a.startTime     = this.formatCalendarString(activity.getStart());
				a.endTime       = this.formatCalendarString(activity.getEnd());
			}
			p.activities        = (StoredActivity[]) activities.toArray();
			projects.add(p);
		}
		
		// --> Employees
		
	}
	public void loadState(){
		
	}
	public char[][] getEmployeeNames(List<Employee> employees){
		ArrayList<char[]> names = new ArrayList<char[]>();
		for(Employee e: employees){
			names.add(e.getUsername().toCharArray());
		}
		return (char[][]) names.toArray();
	}
//	public int[] getWorkIds(List<Work> works){
//		
//	}
	public char[] formatCalendarString(Calendar c){
		return c.toString().toCharArray();
	}
}
