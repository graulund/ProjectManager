package projectmanager;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ChooseYourselfAsProjectLeader {
	
	/**
	 * Main scenario: Tester scenariet, hvor en medarbejder successfuldt
	 * tildeler sig selv som projektleder p� et projekt
	 */
	@Test
	public void ChoosingYourselfAsProjectLeader() {
		ProjectManagerApp PMApp = new ProjectManagerApp();
		Employee employee1 = new Employee("hlb");
		Company company = PMApp.getCompany();
		company.addEmployee(employee1);
		
		// projekt med l�benummer 001 oprettes
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(PMApp.isEmployeeLoggedIn());
		
		// medarbejder indtaster l�benummer for projekt
		int serialNumber = 1;
		
		// projektinformation returneres
		Project projectTest = company.projectBySerialNumber(serialNumber);
		
		// checker at den rette information returneres
		assertEquals(name, projectTest.getName());
		assertEquals(client, projectTest.getClient());
		
		// medarbejder tilf�jer sig selv som projektleder
		projectTest.addLeader(PMApp.getEmployeeLoggedIn());
		
		// checker at medarbejderen er angivet som projektleder
		assertEquals(PMApp.getEmployeeLoggedIn(), projectTest.getLeader());
	}
	
	/**
	 * Alternative scenario: Tester scenariet, hvor en medarbejder pr�ver at
	 * tilf�je sig selv som projektleder, men bliver afvist, idet
	 * der allerede er tildelt en projektleder.
	 */
	@Test
	public void ProjectLeaderAlreadyExists() {
		ProjectManagerApp PMApp = new ProjectManagerApp();
		Employee employee1 = new Employee("hlb");
		Employee employee2 = new Employee("abc");
		Company company = PMApp.getCompany();
		company.addEmployee(employee1);
		company.addEmployee(employee2);
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(PMApp.isEmployeeLoggedIn());
		
		// medarbejder indtaster �rstal og l�benummer for projekt
		int serialNumber = 002;
		
		// projekt med l�benummer 001 oprettes
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		
		// projekt med l�benummer 002 oprettes
		String name2 = "Software Engineering 2";
		String client2 = "Apple";
		Project project2 = new Project(name2, client2);
		
		// tilf�jer projekt til firmaet
		company.addProject(project1);
		
		company.addProject(project2);
		project2.addLeader(employee2);
		
		// projektinformation returneres
		Project projectTest = company.projectBySerialNumber(serialNumber);
		
		// checker at den rette information returneres
		assertEquals(name2, projectTest.getName());
		assertEquals(client2, projectTest.getClient());
		
		// medarbejder tilf�jer sig selv som projektleder
		project2.addLeader(PMApp.getEmployeeLoggedIn());
		
		// da der allerede er tilf�jet et projekt, skal medarbejderen,
		// der er logget ind, ikke v�re projektleder.
		assertEquals(employee2, project2.getLeader());
	}
		
}
