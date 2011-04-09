package projectmanager;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ChooseYourselfAsProjectLeader {
	
	/**
	 * Main scenario: Tester scenariet, hvor en medarbejder successfuldt
	 * tildeler sig selv som projektleder på et projekt
	 */
//	@Test
//	public void ChoosingYourselfAsProjectLeader() {
//		ProjectManagerApp PMApp = new ProjectManagerApp();
//		Employee employee1 = new Employee("hlb");
//		Company company = new Company();
//		company.addEmployee(employee1);
//		
//		// medarbejder logger ind med initialer, der findes i databasen
//		boolean login = PMApp.employeeLogin("hlb");
//		
//		// checker at medarbejderen er logget ind
//		assertTrue(login);
//		assertTrue(PMApp.employeeLoggedIn());
//		
//		// medarbejder indtaster årstal og løbenummer for projekt
//		int year = 2011;
//		int serialNumber = 001;
//		
//		// projekt med løbenummer 001 oprettes
//		String name = "Software Engineering";
//		String client = "Google";
//		Project project1 = new Project(name, client);
//		
//		// tilføjer projekt til firmaet
//		PMApp.addProject(project1);
//		
//		// projektinformation returneres
//		Project projectTest = PMApp.projectBySerialNumber(serialNumber);
//		
//		// checker at den rette information returneres
//		assertEquals(year, projectTest.getYear());
//		assertEquals(name, projectTest.getName());
//		assertEquals(client, projectTest.getClient());
//		
//		// medarbejder tilføjer sig selv som projektleder
//		PMApp.getProject(project1).addLeader(PMApp.getEmployeeLoggedIn());
//		
//		// checker at medarbejderen er angivet som projektleder
//		assertEquals(PMApp.getEmployeeLoggedIn(), PMApp.getProject(project1).getLeader(employee1));
//	}
	
	/**
	 * Alternative scenario: Tester scenariet, hvor en medarbejder prøver at
	 * tilføje sig selv som projektleder, men bliver afvist, idet
	 * der allerede er tildelt en projektleder.
	 */
//	@Test
//	public void ProjectLeaderAlreadyExists() {
//		ProjectManagerApp PMApp = new ProjectManagerApp();
//		Employee employee1 = new Employee("hlb");
//		Employee employee2 = new Employee("abc");
//		Company company = new Company();
//		company.addEmployee(employee1);
//		company.addEmployee(employee2);
//		
//		// medarbejder logger ind med initialer, der findes i databasen
//		boolean login = PMApp.employeeLogin("hlb");
//		
//		// checker at medarbejderen er logget ind
//		assertTrue(login);
//		assertTrue(PMApp.employeeLoggedIn());
//		
//		// medarbejder indtaster årstal og løbenummer for projekt
//		int year = 2011;
//		int serialNumber = 002;
//		
//		// projekt med løbenummer 001 oprettes
//		String name = "Software Engineering";
//		String client = "Google";
//		Project project1 = new Project(name, client);
//		
//		// projekt med løbenummer 002 oprettes
//		String name2 = "Software Engineering 2";
//		String client2 = "Apple";
//		Project project2 = new Project(name2, client2);
//		
//		// tilføjer projekt til firmaet
//		PMApp.addProject(project1);
//		
//		PMApp.addProject(project2);
//		PMApp.getProject(project2).addLeader(employee2);
//		
//		// projektinformation returneres
//		Project projectTest = PMApp.projectBySerialNumber(serialNumber);
//		
//		// checker at den rette information returneres
//		assertEquals(year, projectTest.getYear());
//		assertEquals(name2, projectTest.getName());
//		assertEquals(client2, projectTest.getClient());
//		
//		// medarbejder tilføjer sig selv som projektleder
//		PMApp.getProject(project2).addLeader(PMApp.getEmployeeLoggedIn());
//		
//		// da der allerede er tilføjet et projekt, skal medarbejderen,
//		// der er logget ind, ikke være projektleder.
//		assertEquals(employee2, PMApp.getProject(project2).getLeader(employee2));
//	}
		
}
