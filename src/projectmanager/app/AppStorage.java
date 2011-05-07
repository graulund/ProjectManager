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

/**
 * Class that takes care of permanent storage for the app.
 */
public class AppStorage {
	/**
	 * Default filename to store data in.
	 */
	public static final String default_filename = "pmapp.data";
	
	/**
	 * Current filename being used to store data in.
	 */
	private String filename;
	
	/**
	 * Date format used in storage.
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	/**
	 * Create a new storage object with the default filename.
	 */
	public AppStorage(){
		this(default_filename);
	}
	
	/**
	 * Create a new storage object with the given filename.
	 * @param filename Name (and path) of file to store in
	 */
	public AppStorage(String filename){
		this.filename = filename;
	}
	
	/**
	 * Get current filename being used to store data in.
	 * @return String
	 */
	public String getFilename(){
		return filename;
	}
	
	/**
	 * Stores the current state in a StoredData object.
	 * @return StoredData object with the current state
	 */
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
	
	/**
	 * Saves the current state to permanent storage.
	 */
	public void saveCurrentState(){
		StoredData data = this.storeCurrentState();
		this.saveState(data);
	}
	
	/**
	 * Saves the given StoredData object to permanent storage.
	 * @param data StoredData object
	 */
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
	
	/**
	 * Loads the stored data from permanent storage into a StoredData object.
	 * @return StoredData object
	 */
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
	
	/**
	 * Loads the stored data from permanent storage and restores the current state.
	 */
	public void restoreState(){
		StoredData data = this.loadState();
		if(data != null){
			this.restoreState(data);
		}
	}
	
	/**
	 * Restores the current from the given StoredData object.
	 * @param data StoredData object
	 */
	public void restoreState(StoredData data){
		// Restoring information for:
		// --> Company
		ProjectManagerApp.setCurrentSerialNumber(data.currentSerialNumber);
		// --> Work
		ArrayList<Work> work = new ArrayList<Work>();
		
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
			for(char[] name: p.employeeNames){
				project.addEmployee(this.getEmployeeObject(new String(name), employees));
			}
			// --> Activities
			for(StoredData.StoredActivity a: p.activities){
				Activity activity = new Activity(new String(a.name));
				for(char[] name: a.employeeNames){
					activity.addEmployee(this.getEmployeeObject(new String(name), employees));
				}
				for(int workId: a.workIds){
					activity.addWork(this.getWorkObject(workId, work, this.transformStoredWorkObject(this.getStoredWorkObject(workId, data.works), activity)));
				}
				if(a.startTime.length > 0){
					activity.setStart(this.parseCalendarString(new String(a.startTime)));
				}
				if(a.endTime.length > 0){
					activity.setEnd(this.parseCalendarString(new String(a.endTime)));
				}
				try {
					project.addActivity(activity);
				} catch (Exception e) {
					// Owned
				}
			}
			projects.add(project);
		}
		
		// --> Employee work weeks
		for(Employee employee: employees){
			StoredData.StoredEmployee e = this.getStoredEmployeeObject(employee.getUsername(), data.employees);
			if(e != null){
				for(StoredData.StoredWorkWeek w: e.workWeeks){
					WorkWeek workweek = new WorkWeek(w.week, w.year);
					for(int workId: w.workIds){
						workweek.addWork(this.getWorkObject(workId, work));
					}
					employee.addWorkWeek(workweek);
				}
			}
		}
		
		Company c = ProjectManagerApp.getCompany();
		c.setProjects(projects);
		c.setEmployees(employees);
	}
	
	/**
	 * Prints the given StoredData object in human-readable format to stdout.
	 * @param data StoredData object
	 */
	public void printState(StoredData data){
		System.out.println(this.stateString(data));
	}
	
	/**
	 * Formats the given StoredData object into a human-readable string.
	 * @param data StoredData object
	 * @return Human-readable string with the data in the stored object
	 */
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
	
	// UTILITY METHODS
	
	private char[][] getEmployeeNames(List<Employee> employees){
		ArrayList<char[]> names = new ArrayList<char[]>();
		for(Employee e: employees){
			names.add(e.getUsername().toCharArray());
		}
		return names.toArray(new char[names.size()][]);
	}
	private boolean hasWorkId(int workId, ArrayList<StoredData.StoredWork> storedworks){
		return this.hasWorkId(workId, storedworks.toArray(new StoredData.StoredWork[storedworks.size()]));
	}
	private boolean hasWorkId(int workId, StoredData.StoredWork[] storedworks){
		for(StoredData.StoredWork work: storedworks){
			if(work.workId == workId){
				return true;
			}
		}
		return false;
	}
	private int[] getWorkIds(List<Work> works, StoredData data, ArrayList<StoredData.StoredWork> storedworks){
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
	/*private int[] getWorkIds(List<Work> works){
		return this.getWorkIds(works, null, null);
	}*/
	private Work getWorkObject(int serialNumber, List<Work> works){
		return this.getWorkObject(serialNumber, works, null);
	}
	
	private Work getWorkObject(int serialNumber, List<Work> works, Work insertObject){
		for(Work work: works){
			if(work.serialNumber == serialNumber){
				return work;
			}
		}
		if(insertObject != null){
			works.add(insertObject);
		}
		return insertObject;
	}
	
	private StoredData.StoredWork getStoredWorkObject(int workId, StoredData.StoredWork[] storedworks){
		for(StoredData.StoredWork work: storedworks){
			if(work.workId == workId){
				return work;
			}
		}
		return null;
	}
	
	private Work transformStoredWorkObject(StoredData.StoredWork w, Activity activity){
		if(w.halfHours > 0){
			// Delegated
			return new DelegatedWork(w.halfHours, activity, w.workId);
		} else {
			// Registered
			return new RegisteredWork(
				activity, 
				this.parseCalendarString(new String(w.startTime)), 
				this.parseCalendarString(new String(w.endTime)), 
				w.workId
			);
		}
	}
	
	private Employee getEmployeeObject(String username, List<Employee> employees){
		for(Employee employee: employees){
			if(employee.getUsername().equals(username)){
				return employee;
			}
		}
		return null;
	}
	
	private StoredData.StoredEmployee getStoredEmployeeObject(String username, StoredData.StoredEmployee[] storedemployees){
		for(StoredData.StoredEmployee employee: storedemployees){
			if(new String(employee.username).equals(username)){
				return employee;
			}
		}
		return null;
	}
	
	private char[] formatCalendarString(Calendar c){
		if(c != null){
			return this.sdf.format(c.getTime()).toCharArray();
		}
		return new char[]{};
	}
	private Calendar parseCalendarString(String s){
		GregorianCalendar c = new GregorianCalendar();
		try {
			c.setTime(this.sdf.parse(s));
		} catch (ParseException e) {
			return null;
		}
		return c;
	}
	private String[] parseStringArray(char[][] array){
		String[] s = new String[array.length];
		for(int i = 0; i < array.length; i++){
			s[i] = new String(array[i]);
		}
		return s;
	}
}
