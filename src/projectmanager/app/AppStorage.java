package projectmanager.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import projectmanager.app.StoredData.StoredActivity;
import projectmanager.app.StoredData.StoredEmployee;
import projectmanager.app.StoredData.StoredProject;
import projectmanager.app.StoredData.StoredWork;
import projectmanager.app.StoredData.StoredWorkWeek;

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
			
			ArrayList<StoredData.StoredWorkWeek> weeks = new ArrayList<StoredData.StoredWorkWeek>();
			for(WorkWeek week: employee.getWorkWeeks()){
				StoredData.StoredWorkWeek w = data.new StoredWorkWeek();
				w.week = week.getWeekNumber();
				w.year = week.getYear();
				w.workIds = this.getWorkIds(week.getWork(), data, works);
			}
			e.workWeeks = (StoredWorkWeek[]) weeks.toArray();
			
			employees.add(e);
		}
		data.projects  = (StoredProject[])  projects.toArray();
		data.employees = (StoredEmployee[]) employees.toArray();
		data.works     = (StoredWork[])     works.toArray();
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
	public void printState(StoredData data){
		System.out.println(this.stateString(data));
	}
	public String stateString(StoredData data){
		StringBuilder s = new StringBuilder("{ STORED DATA OBJECT\n");
		s.append("-- Current serial number: " + data.currentSerialNumber + "\n");
		s.append("-- Projects:\n");
		for(StoredData.StoredProject project: data.projects){
			s.append("   { PROJECT\n");
			s.append("   -- Name: "); s.append(project.name);
			s.append("\n   -- Client: "); s.append(project.client);
			s.append("\n   -- Serial Number: "); s.append(project.serialNumber);
			s.append("\n   -- Project Leader: "); s.append(project.projectLeaderName);
			s.append("\n   -- Employees: "); s.append(Arrays.toString(this.parseStringArray(project.employeeNames)));
			s.append("\n   -- Activities:\n");
			for(StoredData.StoredActivity activity: project.activities){
				s.append("      { ACTIVITY\n");
				s.append("      -- Name: "); s.append(activity.name);
				s.append("\n      -- Start time: "); s.append(activity.startTime);
				s.append("\n      -- End time: "); s.append(activity.endTime);
				s.append("\n      -- Employees: "); s.append(Arrays.toString(this.parseStringArray(activity.employeeNames)));
				s.append("\n      -- Work: "); s.append(Arrays.toString(activity.workIds));
				s.append("\n      }\n");
			}
			s.append("\n   }\n");
		}
		s.append("-- Employees: ");
		for(StoredData.StoredEmployee employee: data.employees){
			s.append("   { EMPLOYEE\n");
			s.append("   -- Username: "); s.append(employee.username);
			s.append("\n   -- Full name: "); s.append(employee.fullname);
			s.append("\n   -- Work weeks:\n");
			for(StoredData.StoredWorkWeek workweek: employee.workWeeks){
				s.append("      { WORK WEEK\n");
				s.append("      -- Week number: "); s.append(workweek.week);
				s.append("\n      -- Year: "); s.append(workweek.year);
				s.append("\n      -- Work: "); s.append(Arrays.toString(workweek.workIds));
				s.append("\n      }\n");
			}
			s.append("\n   }\n");
		}
		s.append("-- Works: ");
		for(StoredData.StoredWork work: data.works){
			s.append("   { WORK\n");
			s.append("   -- Work ID: "); s.append(work.workId);
			if(work.halfHours > 0){
				s.append("   -- Half hours: "); s.append(work.halfHours);
			} else {
				s.append("   -- Start time: "); s.append(work.startTime);
				s.append("\n   -- End time: "); s.append(work.endTime);
			}
			s.append("\n   }\n");
		}
		s.append("\n}");
		return s.toString();
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
	public String[] parseStringArray(char[][] array){
		String[] s = new String[array.length];
		for(int i = 0; i < array.length; i++){
			s[i] = new String(array[i]);
		}
		return s;
	}
}
