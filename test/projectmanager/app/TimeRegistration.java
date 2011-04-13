package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

public class TimeRegistration {
	
	/**
	 * Main scenario: tester, hvor en medarbejder succesfuldt registrerer sin tid
	 */
	@Test
	public void registerTime() {
		ProjectManagerApp PMApp = new ProjectManagerApp();
		Employee employee = new Employee("hlb");
		Company company = PMApp.getCompany();
		company.addEmployee(employee);
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("hlb");
		
		// random projects to employee
		Project project = new Project("lolproject", "Google");
		Activity activity = new Activity("lolcat", project);
		company.addProject(project);
		project.addActivity(activity);
		employee.addActivity(activity);
		
		// medarbejder v푡ger en aktivitet, som han er tilmeldt
		List<Activity> activities = company.employeeByUsername("hlb").getActivities();
		Activity chosenActivity = activities.get(0);
		
		// medarbejder indtaster f퓄gende tid 
		String date = "01.01.2011";
		String startTime = "10:00";
		String endTime = "17:00";
		
		// Omformning til calendar
		String[] dateSplit = date.split("\\.");
		
		GregorianCalendar calendarDate = new GregorianCalendar();
		calendarDate.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]));
		
		// tiden registreres
		RegisteredWork regWork = new RegisteredWork(chosenActivity, date, startTime, endTime);
		employee.addRegisteredWork(regWork);
		
		// tester at arbejdet er registreret korrekt
		assertEquals(14, employee.getWorkWeek(calendarDate.get(Calendar.WEEK_OF_YEAR), 2011).getRegisteredWork(chosenActivity, calendarDate).getHalfHoursWorked());
		assertEquals(1, employee.getWorkWeek(calendarDate.get(Calendar.WEEK_OF_YEAR), 2011).getRegisteredWork(chosenActivity, calendarDate).getDate().get(Calendar.DATE));
		assertEquals(1, employee.getWorkWeek(calendarDate.get(Calendar.WEEK_OF_YEAR), 2011).getRegisteredWork(chosenActivity, calendarDate).getDate().get(Calendar.MONTH));
		assertEquals(2011, employee.getWorkWeek(calendarDate.get(Calendar.WEEK_OF_YEAR), 2011).getRegisteredWork(chosenActivity, calendarDate).getDate().get(Calendar.YEAR));		
	}
	
	/**
	 * Alternativ scenarie 1: tester, hvor en medarbejder registrerer
	 * 2 forskellige tider inden for samme uge
	 */
	@Test
	public void testTwoRegisteredWorkInOneWeek() {
		ProjectManagerApp PMApp = new ProjectManagerApp();
		Employee employee = new Employee("hlb");
		Company company = PMApp.getCompany();
		company.addEmployee(employee);
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("hlb");
		
		// random projects to employee
		Project project = new Project("lolproject", "Google");
		Activity activity = new Activity("lolcat", project);
		company.addProject(project);
		project.addActivity(activity);
		employee.addActivity(activity);
		
		// medarbejder v푡ger en aktivitet, som han er tilmeldt
		List<Activity> activities = company.employeeByUsername("hlb").getActivities();
		Activity chosenActivity = activities.get(0);
		
		// medarbejder indtaster f퓄gende tid 
		String date1 = "01.01.2011";
		String startTime1 = "10:45";
		String endTime1 = "17:00";
		
		String date2 = "02.02.2011";
		String startTime2 = "12:00";
		String endTime2 = "16:30";
		
		// Omformning til calendar
		String[] dateSplit1 = date1.split("\\.");
		String[] dateSplit2 = date2.split("\\.");
		
		GregorianCalendar calendarDate1 = new GregorianCalendar();
		calendarDate1.set(Integer.parseInt(dateSplit1[2]), Integer.parseInt(dateSplit1[1]), Integer.parseInt(dateSplit1[0]));
		
		GregorianCalendar calendarDate2 = new GregorianCalendar();
		calendarDate2.set(Integer.parseInt(dateSplit2[2]), Integer.parseInt(dateSplit2[1]), Integer.parseInt(dateSplit2[0]));
		
		// tilf퓂er indtastede arbejde
		RegisteredWork regWork1 = new RegisteredWork(chosenActivity, date1, startTime1, endTime1);
		employee.addRegisteredWork(regWork1);
		
		RegisteredWork regWork2 = new RegisteredWork(chosenActivity, date2, startTime2, endTime2);
		employee.addRegisteredWork(regWork2);
		
		// test if half hours worked is correct
		assertEquals(13, employee.getWorkWeek(calendarDate1.get(Calendar.WEEK_OF_YEAR), 2011).getRegisteredWork(chosenActivity, calendarDate1).getHalfHoursWorked());
		assertEquals(9, employee.getWorkWeek(calendarDate2.get(Calendar.WEEK_OF_YEAR), 2011).getRegisteredWork(chosenActivity, calendarDate2).getHalfHoursWorked());
		
		// test if the two registered weeks are in the same workweek
		assertEquals(employee.getWorkWeek(calendarDate1.get(Calendar.WEEK_OF_YEAR), 2011), employee.getWorkWeek(calendarDate2.get(Calendar.WEEK_OF_YEAR), 2011));
		
		
		
		
	}
	
	/**
	 * Alternativ scenarie 2: tester, hvor en medarbejder giver forkert format af tid
	 */
	@Test
	public void testWrongTimeInput() {
		
	}
	
	/**
	 * Alternativ scenarie 3: tester, hvor en medarbejder giver forkert format af dato
	 */
	@Test
	public void testWrongDateInput() {
		
	}
	
	/**
	 * Alternativ scenarie 4: tester, hvor en medarbejder ikke indtaster nogen data
	 * (date-default = dagen, hvor det indtastes)
	 */
	@Test
	public void testNoDateInput() {
		
	}
	
	/**
	 * Alternativ scenarie 5: tester, hvor en medarbejder indtaster ugyldig dato/tid
	 * (fx. udenfor aktivitetens tid)
	 */
	@Test
	public void testInvalidDateTime() {
		
	}
	

}
