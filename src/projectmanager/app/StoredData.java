package projectmanager.app;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StoredData implements Serializable {
	// Storage structure:
	public class StoredProject {
		public char[] name;
		public char[] client;
		public int serialNumber;
		public char[] projectLeaderName = new char[]{};
		public char[][] employeeNames;
		public StoredActivity[] activities;
	}
	
	public class StoredEmployee {
		public char[] username;
		public char[] fullname = new char[]{};
		public StoredWorkWeek[] workWeeks;
	}
	
	public class StoredActivity {
		public char[] name;
		public char[][] employeeNames;
		public int[] workIds;
		public char[] startTime;
		public char[] endTime;
	}
	
	public class StoredWorkWeek {
		public int week;
		public int year;
		public int[] workIds;
	}
	
	public class StoredWork {
		public int workId;
		public int halfHours = 0;
		public char[] startTime = new char[0];
		public char[] endTime   = new char[0];
	}
	
	// Stored data for:
	// --> Company
	public int currentSerialNumber;
	// --> Projects
	public StoredProject[] projects;
	// --> Employees
	public StoredEmployee[] employees;
	// --> Works
	public StoredWork[] works;
}