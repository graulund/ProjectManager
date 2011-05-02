package projectmanager.app;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import projectmanager.app.StoredData.StoredActivity;
import projectmanager.app.StoredData.StoredEmployee;
import projectmanager.app.StoredData.StoredProject;
import projectmanager.app.StoredData.StoredWork;
import projectmanager.app.StoredData.StoredWorkWeek;

public class AppStorage {
	public static final String default_filename = "pmapp.data";
	private String filename;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
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
		data.currentSerialNumber = ProjectManagerApp.getCurrentSerialNumber();
		
		// --> Work
		ArrayList<StoredData.StoredWork> works = new ArrayList<StoredData.StoredWork>();
		
		// --> Projects
		ArrayList<StoredData.StoredProject> projects = new ArrayList<StoredData.StoredProject>();
		for(Project project: Company.c.getProjects()){
			StoredData.StoredProject p = data.new StoredProject();
			p.name              = project.getName().toCharArray();
			p.client            = project.getClient().toCharArray();
			p.serialNumber      = project.getSerialNumber();
			if(project.getLeader() != null){
				p.projectLeaderName = project.getLeader().getUsername().toCharArray();
			}
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
			p.activities        = activities.toArray(new StoredActivity[activities.size()]);
			
			projects.add(p);
		}
		
		// --> Employees
		ArrayList<StoredData.StoredEmployee> employees = new ArrayList<StoredData.StoredEmployee>();
		for(Employee employee: Company.c.getEmployees()){
			StoredData.StoredEmployee e = data.new StoredEmployee();
			e.username = employee.getUsername().toCharArray();
			if(employee.getFullname() != null){
				e.fullname = employee.getFullname().toCharArray();
			}
			
			ArrayList<StoredData.StoredWorkWeek> weeks = new ArrayList<StoredData.StoredWorkWeek>();
			for(WorkWeek week: employee.getWorkWeeks()){
				StoredData.StoredWorkWeek w = data.new StoredWorkWeek();
				w.week = week.getWeekNumber();
				w.year = week.getYear();
				w.workIds = this.getWorkIds(week.getWork(), data, works);
			}
			e.workWeeks = weeks.toArray(new StoredWorkWeek[weeks.size()]);
			
			employees.add(e);
		}
		data.projects  = projects.toArray(new StoredProject[projects.size()]);
		data.employees = employees.toArray(new StoredEmployee[employees.size()]);
		data.works     = works.toArray(new StoredWork[works.size()]);
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
	public StoredData loadState(){
		try {
			ObjectInputStream objIn   = new ObjectInputStream(new FileInputStream(this.filename));
			StoredData obj = (StoredData) objIn.readObject();
			objIn.close();
			return obj;
		} catch (Exception e) {
			System.out.println("Error: Could not load file\n" + e);
		}
		return null;
	}
	public void restoreState(){
		this.restoreState(this.loadState());
	}
	public void restoreState(StoredData data){
		// Restoring information for:
		// --> Company
		ProjectManagerApp.setCurrentSerialNumber(data.currentSerialNumber);
		// --> Work
		ArrayList<Work> work = new ArrayList<Work>();
		/*for(StoredData.StoredWork w: data.works){
			if(w.halfHours > 0){
				// Delegated
				//DelegatedWork(int hours, Activity activity, int serialNumber)
				//work.add(new DelegatedWork())
			} else {
				// Registered
				//RegisteredWork(Activity activity, Calendar startCalendar, Calendar endCalendar, int serialNumber)
				work.add(new RegisteredWork())
			}
		}*/
		
		// I will have to add the work once I stumble upon them
		// --> Employees
		ArrayList<Employee> employees = new ArrayList<Employee>();
		for(StoredData.StoredEmployee e: data.employees){
			Employee employee = new Employee(new String(e.username), new String(e.fullname));
			employees.add(employee);
		}
		// --> Projects
		ArrayList<Project> projects = new ArrayList<Project>();
		for(StoredData.StoredProject p: data.projects){
			Project project = new Project(new String(p.name), new String(p.client), p.serialNumber);
			if(p.projectLeaderName.length > 0){
				project.addLeader(this.getEmployeeObject(new String(p.projectLeaderName), employees));
			}
			projects.add(project);
		}
		
		
		Company c = ProjectManagerApp.getCompany();
		c.setProjects(projects);
		c.setEmployees(employees);
		
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
			s.append("   }\n");
		}
		s.append("-- Employees:\n");
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
			s.append("   }\n");
		}
		s.append("-- Work:\n");
		for(StoredData.StoredWork work: data.works){
			s.append("   { WORK\n");
			s.append("   -- Work ID: "); s.append(work.workId);
			if(work.halfHours > 0){
				s.append("\n   -- Half hours: "); s.append(work.halfHours);
			} else {
				s.append("\n   -- Start time: "); s.append(work.startTime);
				s.append("\n   -- End time: "); s.append(work.endTime);
			}
			s.append("\n   }\n");
		}
		s.append("}");
		return s.toString();
	}
	public char[][] getEmployeeNames(List<Employee> employees){
		ArrayList<char[]> names = new ArrayList<char[]>();
		for(Employee e: employees){
			names.add(e.getUsername().toCharArray());
		}
		return names.toArray(new char[names.size()][]);
	}
	public boolean hasWorkId(int workId, ArrayList<StoredData.StoredWork> storedworks){
		return this.hasWorkId(workId, storedworks.toArray(new StoredData.StoredWork[storedworks.size()]));
	}
	public boolean hasWorkId(int workId, StoredData.StoredWork[] storedworks){
		for(StoredData.StoredWork work: storedworks){
			if(work.workId == workId){
				return true;
			}
		}
		return false;
	}
	public StoredData.StoredWork getWorkInfo(int workId, StoredData.StoredWork[] storedworks){
		for(StoredData.StoredWork work: storedworks){
			if(work.workId == workId){
				return work;
			}
		}
		return null;
	}
	public int[] getWorkIds(List<Work> works, StoredData data, ArrayList<StoredData.StoredWork> storedworks){
		ArrayList<Integer> workIds = new ArrayList<Integer>();
		for(Work work: works){
			if(work != null){
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
	public Work getWorkObject(int serialNumber, List<Work> works){
		for(Work work: works){
			if(work.serialNumber == serialNumber){
				return work;
			}
		}
		return null;
	}
	public Employee getEmployeeObject(String username, List<Employee> employees){
		for(Employee employee: employees){
			if(employee.getUsername().equals(username)){
				return employee;
			}
		}
		return null;
	}
	public char[] formatCalendarString(Calendar c){
		if(c != null){
			return this.sdf.format(c.getTime()).toCharArray();
		}
		return new char[]{};
	}
	public Calendar parseCalendarString(String s){
		GregorianCalendar c = new GregorianCalendar();
		try {
			c.setTime(this.sdf.parse(s));
		} catch (ParseException e) {
			return null;
		}
		return c;
	}
	public String[] parseStringArray(char[][] array){
		String[] s = new String[array.length];
		for(int i = 0; i < array.length; i++){
			s[i] = new String(array[i]);
		}
		return s;
	}
}
