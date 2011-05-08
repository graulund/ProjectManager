package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class TimeRegistration {
	private Employee employee;
	Activity chosenActivity;
	Company company;
	Project project;
	
	@Before
	public void setUptEmployeeAndActivity() throws OperationNotAllowedException, CreatingActivityException {
		ProjectManagerApp.reset();
		employee = new Employee("hlb");
		company = ProjectManagerApp.getCompany();
		company.addEmployee(employee);
		
		// random projects to employee
		project = new Project("lolproject", "Google");
		Activity activity = new Activity("lolcat");
		company.addProject(project);
		project.addLeader(employee);
		
		ProjectManagerApp.employeeLogin("hlb");
		project.addActivity(activity);
		//activity.addEmployee(employee);
		project.addEmployee(employee, activity);
		
		chosenActivity = activity;
	}
	
	/**
	 * Main scenario: tester, hvor en medarbejder succesfuldt registrerer sin tid
	 * @throws ProjectManagerException 
	 */
	@Test
	public void registerTime() throws ProjectManagerException {	
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// medarbejder indtaster f퓄gende tid 
		String date = "01.01.2011";
		String startTime = "10:00";
		String endTime = "17:00";
		
		// splitter inputtet
		String[] dateSplit = date.split("\\.");
		String[] startTimeSplit = startTime.split(":");
		String[] endTimeSplit = endTime.split(":");
		
		// gemmer inputtet som kalendre
		GregorianCalendar startCalendar = new GregorianCalendar();
		startCalendar.set(Integer.parseInt(dateSplit[2]), 
						  Integer.parseInt(dateSplit[1]), 
						  Integer.parseInt(dateSplit[0]), 
						  Integer.parseInt(startTimeSplit[0]), 
						  Integer.parseInt(startTimeSplit[1])); 
		GregorianCalendar endCalendar = new GregorianCalendar();
		endCalendar.set(Integer.parseInt(dateSplit[2]), 
						Integer.parseInt(dateSplit[1]), 
						Integer.parseInt(dateSplit[0]), 
						Integer.parseInt(endTimeSplit[0]), 
						Integer.parseInt(endTimeSplit[1]));
		
		// tiden registreres
		RegisteredWork regWork = new RegisteredWork(chosenActivity, startCalendar, endCalendar);
		ProjectManagerApp.registerWork(regWork);
		
		// tester at arbejdet er registreret korrekt
		RegisteredWork regworkTest = employee.getWorkWeek(startCalendar.get(Calendar.WEEK_OF_YEAR), 2011).getRegisteredWork(chosenActivity, startCalendar);
		assertEquals(14, regworkTest.getHalfHoursWorked());
		
		// den 1/1 - 2011 svarer til l퓊dag den 1. januar 2011
		assertEquals(1, regworkTest.getDate().get(Calendar.DATE));
		assertEquals(1, regworkTest.getDate().get(Calendar.JANUARY));
		assertEquals(2011, regworkTest.getDate().get(Calendar.YEAR));		
	}
	
	/**
	 * Tester, hvor en medarbejder registrerer
	 * 2 forskellige tider inden for samme uge
	 * @throws ProjectManagerException 
	 */
	@Test
	public void testTwoRegisteredWorkInOneWeek() throws ProjectManagerException {		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// medarbejder indtaster f퓄gende tid 
		String date1 = "01.01.2011";
		String startTime1 = "10:30";
		String endTime1 = "17:00";
		
		// splitter inputtet
		String[] dateSplit1 = date1.split("\\.");
		String[] startTimeSplit1 = startTime1.split(":");
		String[] endTimeSplit1 = endTime1.split(":");
		
		// gemmer inputtet som kalendre
		GregorianCalendar startCalendar1 = new GregorianCalendar();
		startCalendar1.set(Integer.parseInt(dateSplit1[2]), 
						  Integer.parseInt(dateSplit1[1]), 
						  Integer.parseInt(dateSplit1[0]), 
						  Integer.parseInt(startTimeSplit1[0]), 
						  Integer.parseInt(startTimeSplit1[1])); 
		GregorianCalendar endCalendar1 = new GregorianCalendar();
		endCalendar1.set(Integer.parseInt(dateSplit1[2]), 
						Integer.parseInt(dateSplit1[1]), 
						Integer.parseInt(dateSplit1[0]), 
						Integer.parseInt(endTimeSplit1[0]), 
						Integer.parseInt(endTimeSplit1[1]));
		
		// tiden registreres
		RegisteredWork regWork1 = new RegisteredWork(chosenActivity, startCalendar1, endCalendar1);
		ProjectManagerApp.registerWork(regWork1);
		
		// Senere... indtastes dette
		String date2 = "02.01.2011";
		String startTime2 = "12:00";
		String endTime2 = "16:20";
		
		// splitter inputtet
		String[] dateSplit2 = date2.split("\\.");
		String[] startTimeSplit2 = startTime2.split(":");
		String[] endTimeSplit2 = endTime2.split(":");
		
		// gemmer inputtet som kalendre
		GregorianCalendar startCalendar2 = new GregorianCalendar();
		startCalendar2.set(Integer.parseInt(dateSplit2[2]), 
						  Integer.parseInt(dateSplit2[1]), 
						  Integer.parseInt(dateSplit2[0]), 
						  Integer.parseInt(startTimeSplit2[0]), 
						  Integer.parseInt(startTimeSplit2[1])); 
		GregorianCalendar endCalendar2 = new GregorianCalendar();
		endCalendar2.set(Integer.parseInt(dateSplit2[2]), 
						Integer.parseInt(dateSplit2[1]), 
						Integer.parseInt(dateSplit2[0]), 
						Integer.parseInt(endTimeSplit2[0]), 
						Integer.parseInt(endTimeSplit2[1]));
		
		// tiden registreres
		RegisteredWork regWork2 = new RegisteredWork(chosenActivity, startCalendar2, endCalendar2);
		ProjectManagerApp.registerWork(regWork2);
		
		// test if the two registered weeks are in the same workweek
		assertEquals(startCalendar1.get(Calendar.WEEK_OF_YEAR), startCalendar2.get(Calendar.WEEK_OF_YEAR));
		assertEquals(employee.getWorkWeek(startCalendar1.get(Calendar.WEEK_OF_YEAR), 2011), employee.getWorkWeek(startCalendar2.get(Calendar.WEEK_OF_YEAR), 2011));
	}
	
	/**
	 * Tester, hvor en medarbejder registrerer en tid, 
	 * der overlapper med en tidligere registreret tid
	 * @throws ProjectManagerException 
	 */
	@Test
	public void testDublicationOfRegWork() throws ProjectManagerException {
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// medarbejder indtaster f퓄gende tid 
		String date1 = "01.01.2011";
		String startTime1 = "10:30";
		String endTime1 = "17:00";
		
		// splitter inputtet
		String[] dateSplit1 = date1.split("\\.");
		String[] startTimeSplit1 = startTime1.split(":");
		String[] endTimeSplit1 = endTime1.split(":");
		
		// gemmer inputtet som kalendre
		GregorianCalendar startCalendar1 = new GregorianCalendar();
		startCalendar1.set(Integer.parseInt(dateSplit1[2]), 
						  Integer.parseInt(dateSplit1[1])-1, 
						  Integer.parseInt(dateSplit1[0]), 
						  Integer.parseInt(startTimeSplit1[0]), 
						  Integer.parseInt(startTimeSplit1[1])); 
		GregorianCalendar endCalendar1 = new GregorianCalendar();
		endCalendar1.set(Integer.parseInt(dateSplit1[2]), 
						Integer.parseInt(dateSplit1[1])-1, 
						Integer.parseInt(dateSplit1[0]), 
						Integer.parseInt(endTimeSplit1[0]), 
						Integer.parseInt(endTimeSplit1[1]));
		
		// tiden registreres
		RegisteredWork regWork1 = new RegisteredWork(chosenActivity, startCalendar1, endCalendar1);
		ProjectManagerApp.registerWork(regWork1);
		
		// Senere...
		// medarbejder indtaster f퓄gende tid (som overlappe ovenst똢nde)
		String date2 = "01.01.2011";
		String startTime2 = "08:00";
		String endTime2 = "14:30";
		
		// splitter inputtet
		String[] dateSplit2 = date2.split("\\.");
		String[] startTimeSplit2 = startTime2.split(":");
		String[] endTimeSplit2 = endTime2.split(":");
		
		// gemmer inputtet som kalendre
		GregorianCalendar startCalendar2 = new GregorianCalendar();
		startCalendar2.set(Integer.parseInt(dateSplit2[2]), 
						  Integer.parseInt(dateSplit2[1])-1, 
						  Integer.parseInt(dateSplit2[0]), 
						  Integer.parseInt(startTimeSplit2[0]), 
						  Integer.parseInt(startTimeSplit2[1])); 
		GregorianCalendar endCalendar2 = new GregorianCalendar();
		endCalendar2.set(Integer.parseInt(dateSplit2[2]), 
						Integer.parseInt(dateSplit2[1])-1, 
						Integer.parseInt(dateSplit2[0]), 
						Integer.parseInt(endTimeSplit2[0]), 
						Integer.parseInt(endTimeSplit2[1]));
		
		// tiden registreres
		RegisteredWork regWork2 = new RegisteredWork(chosenActivity, startCalendar2, endCalendar2);
		
		try {
			ProjectManagerApp.registerWork(regWork2);
			fail("A AlreadyRegisteredWorkException should have been thrown");			
		} catch (RegisterWorkException e) {
			// Step 4
			assertEquals("You have already registered a work at the given time", e.getMessage());
		}	
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder indtaster gyldig dato/tid, som er indenfor aktivitetens tid
	 * @throws OperationNotAllowedException 
	 * @throws ProjectManagerException 
	 */
	@Test
	public void testValidDateTime() throws OperationNotAllowedException, ProjectManagerException {
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// tilf퓂er aktivitet, hvor start- og slutuge angives
		Activity activity2 = new Activity("hamburger");
		activity2.setStart(1, 2011);
		activity2.setEnd(4, 2011);
		company.addProject(project);
		project.addActivity(activity2);
		//activity2.addEmployee(employee);
		project.addEmployee(employee, activity2);
		
		// medarbejder v푡ger denne aktivitet, som han er tilmeldt
		chosenActivity = activity2;
		
		// medarbejder indtaster f퓄gende tid 
		String date1 = "01.01.2011";
		String startTime1 = "10:30";
		String endTime1 = "17:00";
		
		// splitter inputtet
		String[] dateSplit1 = date1.split("\\.");
		String[] startTimeSplit1 = startTime1.split(":");
		String[] endTimeSplit1 = endTime1.split(":");
		
		// gemmer inputtet som kalendre
		Calendar startCalendar1 = TestingUtilities.createCalendar(
				Integer.parseInt(dateSplit1[2]),
				Integer.parseInt(dateSplit1[1]),
				Integer.parseInt(dateSplit1[0]),
				Integer.parseInt(startTimeSplit1[0]),
				Integer.parseInt(startTimeSplit1[1])
				);
		Calendar endCalendar1 = TestingUtilities.createCalendar(
				Integer.parseInt(dateSplit1[2]),
				Integer.parseInt(dateSplit1[1]),
				Integer.parseInt(dateSplit1[0]),
				Integer.parseInt(endTimeSplit1[0]),
				Integer.parseInt(endTimeSplit1[1])
				);	
		
		// tiden registreres
		RegisteredWork regWork1 = new RegisteredWork(chosenActivity, startCalendar1, endCalendar1);
		ProjectManagerApp.registerWork(regWork1);
	}
	
	/**
	 * Tester, hvor en medarbejder indtaster gyldig dato/tid, men udenfor aktivitetens tid
	 * @throws RegisterWorkException 
	 */
	@Test
	public void testInvalidDateTime() throws RegisterWorkException {
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// tilf퓂er aktivitet
		Activity activity2 = new Activity("hamburger");
		activity2.setStart(20, 2010);
		activity2.setEnd(25, 2010);
		company.addProject(project);
		
		// medarbejder indtaster f퓄gende tid 
		String date1 = "01.01.2011";
		String startTime1 = "10:30";
		String endTime1 = "17:00";
		
		// splitter inputtet
		String[] dateSplit1 = date1.split("\\.");
		String[] startTimeSplit1 = startTime1.split(":");
		String[] endTimeSplit1 = endTime1.split(":");
		
		// gemmer inputtet som kalendre
		Calendar startCalendar1 = new GregorianCalendar(Locale.ENGLISH);
		startCalendar1.set(Integer.parseInt(dateSplit1[2]), 
						  Integer.parseInt(dateSplit1[1])-1, 
						  Integer.parseInt(dateSplit1[0]), 
						  Integer.parseInt(startTimeSplit1[0]), 
						  Integer.parseInt(startTimeSplit1[1])); 
		GregorianCalendar endCalendar1 = new GregorianCalendar();
		endCalendar1.set(Integer.parseInt(dateSplit1[2]), 
						Integer.parseInt(dateSplit1[1])-1, 
						Integer.parseInt(dateSplit1[0]), 
						Integer.parseInt(endTimeSplit1[0]), 
						Integer.parseInt(endTimeSplit1[1]));
		
		
		// tiden registreres
		RegisteredWork regWork1 = new RegisteredWork(activity2, startCalendar1, endCalendar1);
		

		try {
			ProjectManagerApp.registerWork(regWork1);
			fail("A AlreadyRegisteredWorkException should have been thrown");			
		} catch (ProjectManagerException e) {
			// Step 4
			assertEquals("You have registered time outside the timer-interval of the given activity.", e.getMessage());
		}	
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder indtaster en start-tid, der er efter sluttid
	 */
	@Test
	public void testInvalidTime() {
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// medarbejder indtaster f퓄gende tid 
		String date1 = "01.01.2011";
		String startTime1 = "17:30";
		String endTime1 = "10:00";
		
		// splitter inputtet
		String[] dateSplit1 = date1.split("\\.");
		String[] startTimeSplit1 = startTime1.split(":");
		String[] endTimeSplit1 = endTime1.split(":");
		
		// gemmer inputtet som kalendre
		GregorianCalendar startCalendar1 = new GregorianCalendar();
		startCalendar1.set(Integer.parseInt(dateSplit1[2]), 
						  Integer.parseInt(dateSplit1[1])-1, 
						  Integer.parseInt(dateSplit1[0]), 
						  Integer.parseInt(startTimeSplit1[0]), 
						  Integer.parseInt(startTimeSplit1[1])); 
		GregorianCalendar endCalendar1 = new GregorianCalendar();
		endCalendar1.set(Integer.parseInt(dateSplit1[2]), 
						Integer.parseInt(dateSplit1[1])-1, 
						Integer.parseInt(dateSplit1[0]), 
						Integer.parseInt(endTimeSplit1[0]), 
						Integer.parseInt(endTimeSplit1[1]));
		
		// tiden registreres
		RegisteredWork regWork1 = new RegisteredWork(chosenActivity, startCalendar1, endCalendar1);
		try {
			ProjectManagerApp.registerWork(regWork1);
			fail("A AlreadyRegisteredWorkException should have been thrown");			
		} catch (ProjectManagerException e) {
			// Step 4
			assertEquals("The starting time is after the ending time.", e.getMessage());
		}
	}
}
