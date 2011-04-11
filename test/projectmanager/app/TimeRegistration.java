package projectmanager.app;

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
		Project project = new Project("lolproject", "Google");
		company.addProject(project);
		Activity activity = new Activity("lolcat", project);
		project.addActivity(activity);
		company.employeeByUsername("emp1").addActivity(activity);
		
		// medarbejder v¾lger en aktivitet, som han er tilmeldt
		List<Activity> activities = company.employeeByUsername("emp1").getActivities();
		activities.get(0);
		
		// medarbejder indtaster tid 
		String date = "24.12.2011";
		String startTime = "10:00";
		String endTime = "17:00";
		
		String[] dateSplit = date.split(".");
		String[] startTimeSplit = startTime.split(":");
		String[] endTimeSplit = endTime.split(":");
		
		Calendar startCalendar = new GregorianCalendar();
		startCalendar.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]), 
				Integer.parseInt(startTimeSplit[0]), Integer.parseInt(startTimeSplit[1])); 
		Calendar endCalendar = new GregorianCalendar();
		endCalendar.set(Integer.parseInt(dateSplit[2]), Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]), 
				Integer.parseInt(endTimeSplit[0]), Integer.parseInt(endTimeSplit[1]));
		
		// Get the represented date in milliseconds
        long milis1 = startCalendar.getTimeInMillis();
        long milis2 = endCalendar.getTimeInMillis();
        
        // Calculate difference in milliseconds
        long diff = milis2 - milis1;
        
        // Calculate difference in hours
        long diffHours = (diff / (60 * 60 * 1000));

		RegisteredWork regwork = new RegisteredWork(company.employeeByUsername("emp1"), activity, (int)diffHours*2);
		
		company.employeeByUsername("emp1").addRegisteredWork(regwork);		
	}

}
