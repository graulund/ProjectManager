package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

public class TimeRegistration {
	
	/**
	 * Main scenario
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
		//Employee employee = company.employeeByUsername("hlb");
		Project project = new Project("lolproject", "Google");
		Activity activity = new Activity("lolcat", project);
		company.addProject(project);
		project.addActivity(activity);
		employee.addActivity(activity);
		
		// medarbejder v¾lger en aktivitet, som han er tilmeldt
		List<Activity> activities = company.employeeByUsername("hlb").getActivities();
		Activity chosenActivity = activities.get(0);
		
		// medarbejder indtaster f¿lgende tid 
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

}
