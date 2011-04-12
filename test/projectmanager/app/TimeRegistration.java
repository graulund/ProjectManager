package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

public class TimeRegistration extends SampleDataSetup {
	
	/**
	 * Main scenario
	 */
	@Test
	public void registerTime() {
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("emp1");
		
		// random projects to employee
		Employee employee = company.employeeByUsername("emp1");
		Project project = new Project("lolproject", "Google");
		Activity activity = new Activity("lolcat", project);
		company.addProject(project);
		project.addActivity(activity);
		employee.addActivity(activity);
		
		// medarbejder v¾lger en aktivitet, som han er tilmeldt
		List<Activity> activities = company.employeeByUsername("emp1").getActivities();
		Activity chosenActivity = activities.get(0);
		
		// medarbejder indtaster f¿lgende tid 
		String date = "01.01.2011";
		String startTime = "10:00";
		String endTime = "17:00";
		
		// Omformning til calendar
		String[] dateSplit = date.split(".");
		
		GregorianCalendar calendarDate = new GregorianCalendar();
		calendarDate.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]));
		
		// tiden registreres
		RegisteredWork regWork = new RegisteredWork(chosenActivity, date, startTime, endTime);
		employee.addRegisteredWork(regWork);
		
		// tester at arbejdet er registreret korrekt
		assertEquals(7, employee.getWorkWeek(calendarDate.get(Calendar.WEEK_OF_YEAR), 2011).getRegisteredWork(chosenActivity, calendarDate).getHalfHoursWorked());
		
		
		
		
		// Programmet bruger f¿lgende kode til at omdanne input (SKAL IKKE V®RE I TESTEN!!)
		
		/*String[] dateSplit = date.split(".");
		String[] startTimeSplit = startTime.split(":");
		String[] endTimeSplit = endTime.split(":");
		
		GregorianCalendar startCalendar = new GregorianCalendar();
		startCalendar.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]), 
				Integer.parseInt(startTimeSplit[0]), Integer.parseInt(startTimeSplit[1])); 
		GregorianCalendar endCalendar = new GregorianCalendar();
		endCalendar.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]), 
				Integer.parseInt(endTimeSplit[0]), Integer.parseInt(endTimeSplit[1]));
		
		// Get the represented date in milliseconds
        long milis1 = startCalendar.getTimeInMillis();
        long milis2 = endCalendar.getTimeInMillis();
        
        // Calculate difference in milliseconds
        long diff = milis2 - milis1;
        
        // Calculate difference in Halfhours
        long diffHalfHours = (diff / (60 * 60 * 1000))/2;
        
        
        
		RegisteredWork regwork = new RegisteredWork(company.employeeByUsername("emp1"), activity, (int)diffHours, startCalendar);
		company.employeeByUsername("emp1").addWorkWeek(workweek);
		company.employeeByUsername("emp1").getWorkWeek(startCalendar.get(Calendar.WEEK_OF_YEAR)).addRegisteredWork(regwork);*/	
		
	}

}
