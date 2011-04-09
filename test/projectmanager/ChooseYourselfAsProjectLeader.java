package projectmanager;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ChooseYourselfAsProjectLeader {
	
	/**
	 * Main scenario: Tester scenariet, hvor en medarbejder successfuldt
	 * tildeler sig selv som projektleder på et projekt
	 */
	@Test
	public void ChoosingYourselfAsProjectLeader() {
		ProjectManagerApp PMApp = new ProjectManagerApp();
		
		Company company = new Company();
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(PMApp.employeeLoggedIn());
		
		// medarbejder indtaster årstal og løbenummer for projekt
		int year = 2011;
		int serialNumber = 001;
		
		// projekt med løbenummer 001 oprettes
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		
		// projekt med løbenummer 002 oprettes
		String name2 = "Software Engineering 2";
		String client2 = "Apple";
		Project project2 = new Project(name2, client2);
		
		// tilføjer projekt til firmaet
		PMApp.addProject(project1);
		String leader = null;
		PMApp.getProject(project1).addLeader(leader);
		
		PMApp.addProject(project2);
		String leader2 = "abc";
		PMApp.getProject(project2).addLeader(leader2);
		
		// projektinformation returneres
		Project projectTest = PMApp.projectBySerialNumber(serialNumber);
		
		// checker at den rette information returneres
		assertEquals(year, projectTest.getYear());
		assertEquals(name, projectTest.getName());
		assertEquals(client, projectTest.getClient());
		
		// medarbejder tilføjer sig selv som projektleder
		PMApp.getProject(project1).addLeader("hlb");
		
		// checker at medarbejderen er angivet som projektleder
		assertEquals(PMApp.getProject(project1).getLeader();
	}
		
}
