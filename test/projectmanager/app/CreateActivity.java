package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CreateActivity {
	
	@Before
	public void resetBefore() {
		ProjectManagerApp.reset();
	}
	

	/**
	 * Main scenario: Opret aktivitet i et projekt
	 * @throws OperationNotAllowedException 
	 * @throws CreatingActivityException 
	 */
	@Test
	public void testCreateActivity() throws OperationNotAllowedException, CreatingActivityException {
		ProjectManagerApp.reset();
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
		
		// der tilf¿jes en aktivitet
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
	 * Tester scenariet, hvor en projektmanager tilf¿jer start- og slut-dato til aktiviteten
	 * @throws OperationNotAllowedException 
	 * @throws ProjectManagerException 
	 */
	@Test
	public void testSetActivityDate() throws OperationNotAllowedException, ProjectManagerException {
		ProjectManagerApp.reset();
		Company company = ProjectManagerApp.getCompany();
		
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		Employee employee = new Employee("hlb");
		company.addEmployee(employee);
		project1.addLeader(employee);
		assertEquals(employee, project1.getLeader());
		
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		assertTrue(project1.getActivities().isEmpty());
		
		int serialNumber = project1.getSerialNumber();
		Project chosenProject = company.projectBySerialNumber(serialNumber);
		
		String actName = "activity1";		
		Activity activity = new Activity(actName);
		chosenProject.addActivity(activity);
		
		// projectmanageren tilf¿jer dato'er
		activity.setStart(10, 2011);
		activity.setEnd(15, 2011);
		
		assertEquals(10, activity.getStart().get(Calendar.WEEK_OF_YEAR));
		assertEquals(2011, activity.getStart().get(Calendar.YEAR));
		assertEquals(15, activity.getEnd().get(Calendar.WEEK_OF_YEAR));
		assertEquals(2011, activity.getStart().get(Calendar.YEAR));
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder s¾tter en start-uge, der er efter slut-ugen, 
	 * samt s¾tter en slut-uge, der er f¿r start-ugen. Ved dette kastes en exception
	 * @throws ProjectManagerException
	 * @throws OperationNotAllowedException
	 */
	@Test
	public void testInvalidActivityDates() throws ProjectManagerException, OperationNotAllowedException {
		ProjectManagerApp.reset();
		Company company = ProjectManagerApp.getCompany();
		
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		Employee employee = new Employee("hlb");
		company.addEmployee(employee);
		project1.addLeader(employee);
		assertEquals(employee, project1.getLeader());
		
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		assertTrue(project1.getActivities().isEmpty());
		
		int serialNumber = project1.getSerialNumber();
		Project chosenProject = company.projectBySerialNumber(serialNumber);
		
		String actName = "activity1";		
		Activity activity = new Activity(actName);
		chosenProject.addActivity(activity);
		activity.setStart(10, 2011);
		activity.setEnd(15, 2011);
		
		try {
			activity.setStart(17, 2011);
			fail("Der skulle v¾re kastet en exception!");
		} catch (ProjectManagerException e) {
			assertEquals("Den givne tid er invalid.", e.getMessage());
		}
		
		try {
			activity.setEnd(9, 2011);
			fail("Der skulle v¾re kastet en exception!");
		} catch (ProjectManagerException e) {
			assertEquals("Den givne tid er invalid.", e.getMessage());
		}
		
		assertEquals(10, activity.getStart().get(Calendar.WEEK_OF_YEAR));
		assertEquals(2011, activity.getStart().get(Calendar.YEAR));
		assertEquals(15, activity.getEnd().get(Calendar.WEEK_OF_YEAR));
		assertEquals(2011, activity.getStart().get(Calendar.YEAR));
	}
}
