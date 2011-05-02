package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CreateActivity {

	/**
	 * Main scenario: Opret aktivitet i et projekt
	 * @throws OperationNotAllowedException 
	 * @throws ActivityAlreadyCreatedException 
	 */
	@Test
	public void CreateActivity() throws OperationNotAllowedException, ActivityAlreadyCreatedException {
		ProjectManagerApp.re
		Company company = ProjectManagerApp.getCompany();
		
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		Employee employee = new Employee("hlb");
		company.addEmployee(employee);
		project1.addLeader(employee);
		
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		assertEquals(employee, project1.getLeader());
		
		// checker at der ikke eksisterer nogle aktiviteter
		assertTrue(project1.getActivities().isEmpty());
		
		// der tilføjes en aktivitet
		int serialNumber = project1.getSerialNumber();
		Project chosenProject = company.projectBySerialNumber(serialNumber);
		
		String actName = "activity1";		
		Activity activity = new Activity(actName);
		chosenProject.addActivity(activity);
		
		assertEquals(1, project1.getActivities().size());
		assertEquals(activity, project1.activityByName(actName));
		assertEquals(actName, project1.getActivities().get(0).getName());
	}
	
	/**
	 * Tester scenariet, hvor der oprettes en aktivitet af en bruger, som
	 * ikke er projektleder
	 * @throws ActivityAlreadyCreatedException 
	 */
	@Test
	public void testNotProjectManager() throws ActivityAlreadyCreatedException {
		Company company = ProjectManagerApp.getCompany();
		
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		Employee employee = new Employee("hlb");
		company.addEmployee(employee);
		
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		assertFalse(project1.hasProjectManager());
		
		// checker at der ikke eksisterer nogle aktiviteter
		assertTrue(project1.getActivities().isEmpty());
		
		// der tilføjes en aktivitet
		int serialNumber = project1.getSerialNumber();
		Project chosenProject = company.projectBySerialNumber(serialNumber);
		
		String actName = "activity1";		
		Activity activity = new Activity(actName);
		try {
			chosenProject.addActivity(activity);
			fail("A AlreadyRegisteredWorkException should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Create activity", e.getOperation());
		}	
		
	}
	
	/**
	 * Tester scenariet, hvor der oprettes to aktiviteter med samme navn
	 * Ønsket resultat: der kastes en exception på det andet forsøg
	 * @throws OperationNotAllowedException 
	 * @throws ActivityAlreadyCreatedException 
	 */
	@Test
	public void testTwoActivitySameName() throws OperationNotAllowedException, ActivityAlreadyCreatedException {
		Company company = ProjectManagerApp.getCompany();
		
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		Employee employee = new Employee("hlb");
		company.addEmployee(employee);
		project1.addLeader(employee);
		
		assertFalse(ProjectManagerApp.isEmployeeLoggedIn());
		boolean login = ProjectManagerApp.employeeLogin("hls");
		assertTrue(login);
		assertEquals(employee, ProjectManagerApp.getEmployeeLoggedIn());
		
		
		assertEquals(employee, project1.getLeader());
		
		// checker at der ikke eksisterer nogle aktiviteter
		assertTrue(project1.getActivities().isEmpty());
		
		// der tilføjes en aktivitet
		int serialNumber = project1.getSerialNumber();
		Project chosenProject = company.projectBySerialNumber(serialNumber);
		
		String actName = "activity1";		
		Activity activity = new Activity(actName);
		assertEquals(employee, chosenProject.getLeader());
		assertEquals(employee, ProjectManagerApp.getEmployeeLoggedIn());
		chosenProject.addActivity(activity);
		
		// der tilføjes endnu en aktivitet af samme navn	
		Activity activity2 = new Activity(actName);
		
		try {
			chosenProject.addActivity(activity2);
			fail("A AlreadyRegisteredWorkException should have been thrown");
		} catch (ActivityAlreadyCreatedException e) {
			assertEquals("An activity with the desired name already exists.", e.getMessage());
		}	
	}
}
