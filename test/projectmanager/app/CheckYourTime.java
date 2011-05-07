package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import projectmanager.app.Company;
import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class CheckYourTime {

	/**
	 * Se hvornår du selv har tid 
	 */
	@Test
	public void checkYourTime() throws Exception {
		
		Employee employee = new Employee("hlb");
		Company company = ProjectManagerApp.getCompany();
		company.addEmployee(employee);
		company.addProject(new Project("project mayhem", "Google"));
		Project currentProject = company.getProjects().get(0);
		
		Activity a1 = new Activity("funky activity");
		Activity a2 = new Activity("sweet activity");
		
		currentProject.getActivities().add(a1);
		currentProject.getActivities().add(a2);
		
		//a1.addEmployee(employee);
		//a2.addEmployee(employee);
		currentProject.addEmployee(employee, a1);
		currentProject.addEmployee(employee, a2);
		
		employee.addDelegatedWork(25, 2011, a1, 40);
		employee.addDelegatedWork(25, 2011, a2, 30);
		
		Calendar startDate = new GregorianCalendar();
		startDate.set(Calendar.YEAR, 2011);
		startDate.set(Calendar.WEEK_OF_YEAR, 25);
		startDate.set(Calendar.HOUR, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		
		Calendar endDate1 = new GregorianCalendar();
		endDate1.set(Calendar.YEAR, 2011);
		endDate1.set(Calendar.WEEK_OF_YEAR, 25);
		endDate1.set(Calendar.HOUR, 15);
		endDate1.set(Calendar.MINUTE, 0);
		endDate1.set(Calendar.SECOND, 0);
		
		Calendar startDate2 = (Calendar) startDate.clone();
		Calendar endDate2 = (Calendar) endDate1.clone();
		startDate2.add(Calendar.DATE, 1);
		endDate2.add(Calendar.DATE, 1);
		endDate2.add(Calendar.HOUR, -5);
		
		RegisteredWork regwork1 = new RegisteredWork(a1, startDate, endDate1); // 15 hours of work
		RegisteredWork regwork2 = new RegisteredWork(a2, startDate2, endDate2); // 10 hours of work
		employee.addRegisteredWork(regwork1);
		employee.addRegisteredWork(regwork2);
		
		// Vi forventer nu at få følgende resultat
		String one = "funky activity: 15/40"; 
		String two = "sweet activity: 10/30"; 	
		
		// Tjekker schedule
		List<String> schedule = employee.getWorkWeek(25, 2011).getWeekSchedule();
		assertEquals(schedule.get(0), one);
		assertEquals(schedule.get(1), two);
	}
}
