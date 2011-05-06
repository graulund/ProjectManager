package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EditRegisteredWork {
	private Employee employee;
	Company company;
	Project project;
	Activity activity;
	int week, year;
	RegisteredWork regwork1;
	
	@Before
	public void setUp() throws OperationNotAllowedException, ProjectManagerException {
		ProjectManagerApp.reset();
		employee = new Employee("hlb");
		company = ProjectManagerApp.getCompany();
		company.addEmployee(employee);
		
		project = new Project("lolproject", "Google");
		activity = new Activity("lolcat");
		company.addProject(project);
		project.addLeader(employee);
		
		ProjectManagerApp.employeeLogin("hlb");
		project.addActivity(activity);
		activity.addEmployee(employee);
		
		// adding registered work
		// regwork 1: week 25, 5 hours = 10 halfhours
		// regwork 2: week 25, 7 hours = 14 halfhours
		year = 2011; week = 25;
		Calendar startDate1 = TestingUtilities.createCalendar(2011, 25, 10, 0);
		Calendar endDate1 = TestingUtilities.createCalendar(2011, 25, 15, 0);
		
		regwork1 = new RegisteredWork(activity, startDate1, endDate1);
		//RegisteredWork regwork2 = new RegisteredWork(activity, startDate2, endDate2);
		employee.addRegisteredWork(regwork1);
		//employee.addRegisteredWork(regwork2);
		
		ProjectManagerApp.employeeLogout();
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder successfuldt redigere i sit registrerede arbejde
	 * @throws ProjectManagerException 
	 */
	@Test
	public void testEditRegisteredWork() throws ProjectManagerException {
		// Tester den allerede registrerede tid
		assertEquals(10, employee.getWorkWeek(week, year).getRegisteredWork().get(0).getHalfHoursWorked());
		
		assertEquals(week, regwork1.getStartTime().get(Calendar.WEEK_OF_YEAR));
		assertEquals(year, regwork1.getStartTime().get(Calendar.YEAR));
		assertEquals(10, regwork1.getStartTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(00, regwork1.getStartTime().get(Calendar.MINUTE));
		
		assertEquals(week, regwork1.getEndTime().get(Calendar.WEEK_OF_YEAR));
		assertEquals(year, regwork1.getEndTime().get(Calendar.YEAR));
		assertEquals(15, regwork1.getEndTime().get(Calendar.HOUR_OF_DAY));
		assertEquals(00, regwork1.getEndTime().get(Calendar.MINUTE));
		
		// 1: medarbejder logger ind
		ProjectManagerApp.employeeLogin("hlb");
		
		// 2: medarbejder vælger "rediger registrer tid"
		//    og får liste af registrerede tider indenfor aktiviteten
		List<RegisteredWork> regworks = ProjectManagerApp.getEmployeeLoggedIn().getWorkWeek(week, year).getRegisteredWork();
		
		// 3: medarbejder vælger en af de registrerede tider
		RegisteredWork regworkChosen = regworks.get(0);
		
		// 4: medarbejder skriver en ny start-tid og en ny slut-tid
		int newHourFrom = 9, newMinutesFrom = 45;
		int newHourTo   = 17, newMinutesTo  = 30;
		
		employee.setRegisteredWork(regworkChosen, newHourFrom, newMinutesFrom, newHourTo, newMinutesTo);
		
		// der testes om det registrerede arbejde er rettet
		Calendar regworkStart = regworkChosen.getStartTime();
		assertEquals(week, regworkStart.get(Calendar.WEEK_OF_YEAR));
		assertEquals(year, regworkStart.get(Calendar.YEAR));
		assertEquals(newHourFrom, regworkStart.get(Calendar.HOUR_OF_DAY));
		assertEquals(newMinutesFrom, regworkStart.get(Calendar.MINUTE));
		
		Calendar regworkEnd   = regworkChosen.getEndTime();
		assertEquals(year, regworkEnd.get(Calendar.YEAR));
		assertEquals(week, regworkEnd.get(Calendar.WEEK_OF_YEAR));
		assertEquals(year, regworkEnd.get(Calendar.YEAR));
		assertEquals(newHourTo, regworkEnd.get(Calendar.HOUR_OF_DAY));
		assertEquals(newMinutesTo, regworkEnd.get(Calendar.MINUTE));
		
		assertEquals(15, regworkChosen.getHalfHoursWorked());
	}

	@Test
	public void testEditRegisteredWorkCollision() throws ProjectManagerException {
		Calendar startDate2 = (Calendar) regwork1.getStartTime().clone();
		Calendar endDate2 = (Calendar) regwork1.getEndTime().clone();
		startDate2.add(Calendar.HOUR_OF_DAY, 6);
		endDate2.add(Calendar.HOUR_OF_DAY, 4);
		assertEquals(16, startDate2.get(Calendar.HOUR_OF_DAY));
		assertEquals(19, endDate2.get(Calendar.HOUR_OF_DAY));
		// startDate2 --> endDate2 = 16.00 - 19.00
		
		assertEquals(regwork1.getDate().get(Calendar.DATE), startDate2.get(Calendar.DATE));
		assertEquals(regwork1.getDate().get(Calendar.DATE), endDate2.get(Calendar.DATE));
		
		RegisteredWork regwork2 = new RegisteredWork(activity, startDate2, endDate2);
		employee.addRegisteredWork(regwork2);
		
		// medarbejderen vælger et registreret arbejde
		RegisteredWork regworkChosen = regwork2;
		
		// medbarjedern ændrer tiden, så den kollidere med et andet registreret arbejde
		int newStartHour = 11, newStartMinutes = 0;
		int newEndHour   = 16, newEndMinutes = 0;
		
		try {
			employee.setRegisteredWork(regworkChosen, newStartHour, newStartMinutes, newEndHour, newEndMinutes);
			fail("A AlreadyRegisteredWorkException should have been thrown");			
		} catch (RegisterWorkException e) {
			// Step 4
			assertEquals("You have already registered a work at the given time", e.getMessage());
		}	
	}
}
