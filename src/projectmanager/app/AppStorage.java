package projectmanager.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import projectmanager.app.StoredData.StoredActivity;
import projectmanager.app.StoredData.StoredEmployee;
import projectmanager.app.StoredData.StoredProject;

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
	public StoredData storeCurrentState(){
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
				a.workIds       = this.getWorkIds(activity.getWork(), data, works);
				a.startTime     = this.formatCalendarString(activity.getStart());
				a.endTime       = this.formatCalendarString(activity.getEnd());
				activities.add(a);
			}
			p.activities        = (StoredActivity[]) activities.toArray();
			projects.add(p);
		}
		
		// --> Employees
		ArrayList<StoredData.StoredEmployee> employees = new ArrayList<StoredData.StoredEmployee>();
		for(Employee employee: Company.c.getEmployees()){
			StoredData.StoredEmployee e = data.new StoredEmployee();
			e.username = employee.getUsername().toCharArray();
			e.fullname = employee.getFullname().toCharArray();
			e.workIds  = this.getWorkIds(employee.getWork(), data, works);
			employees.add(e);
		}
		data.projects  = (StoredProject[])  projects.toArray();
		data.employees = (StoredEmployee[]) employees.toArray();
		return data;
	}
	public void saveCurrentState(){
		StoredData data = this.storeCurrentState();
		this.saveState(data);
	}
	public void saveState(StoredData data) {
		try {
			ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(this.filename));
			objOut.writeObject(data);
			objOut.flush();
			objOut.close();
		} catch (IOException e) {
			System.out.println("Error: Could not save file\n" + e);
		}
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
	public boolean hasWorkId(int workId, ArrayList<StoredData.StoredWork> storedworks){
		for(StoredData.StoredWork work: storedworks){
			if(work.workId == workId){
				return true;
			}
		}
		return false;
	}
	public int[] getWorkIds(List<Work> works, StoredData data, ArrayList<StoredData.StoredWork> storedworks){
		ArrayList<Integer> workIds = new ArrayList<Integer>();
		for(Work work: works){
			if(data != null && storedworks != null && !this.hasWorkId(work.serialNumber, storedworks)){
				StoredData.StoredWork swork = data.new StoredWork();
				swork.workId = work.serialNumber;
				if(work.getClass() == DelegatedWork.class){
					swork.halfHours = ((DelegatedWork) work).getHalfHoursWorked();
				}
				if(work.getClass() == RegisteredWork.class){
					swork.startTime = this.formatCalendarString(((RegisteredWork) work).getStartTime());
					swork.endTime   = this.formatCalendarString(((RegisteredWork) work).getEndTime());
				}
				storedworks.add(swork);
			}
			workIds.add(work.serialNumber);
		}
		int[] ids = new int[workIds.size()];
		for(int i = 0; i < workIds.size(); i++){
			ids[i] = workIds.get(i);
		}
		return ids;
	}
	public int[] getWorkIds(List<Work> works){
		return this.getWorkIds(works, null, null);
	}
	public char[] formatCalendarString(Calendar c){
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return f.format(c.getTime()).toCharArray();
	}
//	public Calendar parseCalendarString(String s){
//		
//	}
}
