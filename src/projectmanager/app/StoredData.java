package projectmanager.app;

public class StoredData {
	// Storage structure:
	private class StoredProject {
		public String name;
		public String client;
		public int serialNumber;
		public String projectLeaderName;
		public String[] employeeNames;
		public StoredActivity[] activities;
	}
	
	private class StoredEmployee {
		public String name;
		public int[] workIds;
	}
	
	private class StoredActivity {
		public String name;
		public String[] employeeNames;
		public int[] workIds;
		public String startTime;
		public String endTime;
	}
	
	private class StoredWork {
		public int workId;
		public int weekNumber;
		//public String[] employeeNames;
		public String startTime;
		public String endTime;
	}
	
	// Stored data for:
	// --> Company
	public int currentSerialNumber;
	// --> Projects
	public StoredProject[] projects;
	// --> Employees
	public StoredEmployee[] employees;
}