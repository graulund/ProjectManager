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
		
		a1.addEmployee(employee);
		a2.addEmployee(employee);
		
		employee.addDelegatedWork(25, 2011, a1, 40);
		employee.addDelegatedWork(25, 2011, a2, 30);
		
		employee.addRegisteredWork(new RegisteredWork(a1, 15));
		employee.addRegisteredWork(new RegisteredWork(a2, 10));
		
		// Vi forventer nu at få følgende resultat
		String one = "funky activity: 0/40"; // should be 15/40
		String two = "sweet activity: 0/30"; // should be 10/30
		
		// Tjekker schedule
		List<String> schedule = employee.getWorkWeeks().get(0).getWeekSchedule();
		
		assertEquals(schedule.get(0), one);
		assertEquals(schedule.get(1), two);
	}
}
