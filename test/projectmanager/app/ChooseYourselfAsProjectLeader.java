package projectmanager.app;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import projectmanager.app.Company;
import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class ChooseYourselfAsProjectLeader {
	
	@Before
	public void resetBefore() {
		ProjectManagerApp.reset();
	}
	
	
	/**
	 * Main scenario: Tester scenariet, hvor en medarbejder successfuldt
	 * tildeler sig selv som projektleder p� et projekt
	 */
	@Test
	public void ChoosingYourselfAsProjectLeader() {
		Employee employee1 = new Employee("hlb");
		Company company = ProjectManagerApp.getCompany();
		company.addEmployee(employee1);
		
		// projekt med l�benummer 001 oprettes
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(ProjectManagerApp.isEmployeeLoggedIn());
		
		// medarbejder indtaster l�benummer for projekt
		int serialNumber = 1;
		
		// projektinformation returneres
		Project projectTest = company.projectBySerialNumber(serialNumber);
		
		// checker at den rette information returneres
		assertEquals(name, projectTest.getName());
		assertEquals(client, projectTest.getClient());
		
		// medarbejder tilf�jer sig selv som projektleder
		projectTest.addLeader(ProjectManagerApp.getEmployeeLoggedIn());
		
		// checker at medarbejderen er angivet som projektleder
		assertEquals(ProjectManagerApp.getEmployeeLoggedIn(), projectTest.getLeader());
	}
	
	/**
	 * Alternative scenario: Tester scenariet, hvor en medarbejder pr�ver at
	 * tilf�je sig selv som projektleder, men bliver afvist, idet
	 * der allerede er tildelt en projektleder.
	 */
	@Test
	public void ProjectLeaderAlreadyExists() {
		Employee employee1 = new Employee("hlb");
		Employee employee2 = new Employee("abc");
		Company company = ProjectManagerApp.getCompany();
		company.addEmployee(employee1);
		company.addEmployee(employee2);
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(ProjectManagerApp.isEmployeeLoggedIn());
		
		
		// projekt med l�benummer 001 oprettes
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		
		// tilf�jer projekt til firmaet
		company.addProject(project1);
		project1.addLeader(employee2);
		
		// medarbejder tilf�jer sig selv som projektleder
		project1.addLeader(ProjectManagerApp.getEmployeeLoggedIn());
		
		// da der allerede er tilf�jet et projekt, skal medarbejderen,
		// der er logget ind, ikke v�re projektleder.
		assertEquals(employee2, project1.getLeader());
	}
		
}
