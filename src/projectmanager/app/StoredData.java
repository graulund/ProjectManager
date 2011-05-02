package projectmanager.app;

public class StoredData {
	// Storage structure:
	public class StoredProject {
		public char[] name;
		public char[] client;
		public int serialNumber;
		public char[] projectLeaderName;
		public char[][] employeeNames;
		public StoredActivity[] activities;
	}
	
	public class StoredEmployee {
		public char[] name;
		public int[] workIds;
	}
	
	public class StoredActivity {
		public char[] name;
		public char[][] employeeNames;
		public int[] workIds;
		public char[] startTime;
		public char[] endTime;
	}
	
	public class StoredWork {
		public int workId;
		public int weekNumber;
		//public char[][] employeeNames;
		public char[] startTime;
		public char[] endTime;
	}
	
	// Stored data for:
	// --> Company
	public int currentSerialNumber;
	// --> Projects
	public StoredProject[] projects;
	// --> Employees
	public StoredEmployee[] employees;
}