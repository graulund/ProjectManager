package projectmanager.app;

import java.io.Serializable;

/**
 * Class that holds the reorganized data being stored permanently by the application in a file.
 */
@SuppressWarnings("serial")
public class StoredData implements Serializable {
	
	// STORAGE STRUCTURE
	
	/**
	 * Class that holds reorganized Project data.
	 */
	public class StoredProject implements Serializable {
		public char[] name;
		public char[] client;
		public int serialNumber;
		public char[] projectLeaderName = new char[]{};
		public char[][] employeeNames;
		public StoredActivity[] activities;
	}
	
	/**
	 * Class that holds reorganized Employee data.
	 */
	public class StoredEmployee implements Serializable {
		public char[] username;
		public char[] fullname = new char[]{};
		public StoredWorkWeek[] workWeeks;
	}
	
	/**
	 * Class that holds reorganized Storage data.
	 */
	public class StoredActivity implements Serializable {
		public char[] name;
		public char[][] employeeNames;
		public int[] workIds;
		public char[] startTime;
		public char[] endTime;
	}
	
	/**
	 * Class that holds reorganized Work Week data.
	 */
	public class StoredWorkWeek implements Serializable {
		public int week;
		public int year;
		public int[] workIds;
	}
	
	/**
	 * Class that holds reorganized Stored Work data.
	 */
	public class StoredWork implements Serializable {
		public int workId;
		public int halfHours = 0;
		public char[] startTime = new char[0];
		public char[] endTime   = new char[0];
	}
	
	// STORED DATA VARIABLES
	
	/**
	 * Stored current serial number.
	 */
	public int currentSerialNumber;
	
	/**
	 * Stored data for projects.
	 */
	public StoredProject[] projects;
	
	/**
	 * Stored data for employees.
	 */
	public StoredEmployee[] employees;
	
	/**
	 * Stored data for work.
	 */
	public StoredWork[] works;
}