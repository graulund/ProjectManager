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
	 * tildeler sig selv som projektleder på et projekt
	 */
	@Test
	public void ChoosingYourselfAsProjectLeader() {
		Employee employee1 = new Employee("hlb");
		Company company = ProjectManagerApp.getCompany();
		company.addEmployee(employee1);
		
		// projekt med løbenummer 001 oprettes
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(ProjectManagerApp.isEmployeeLoggedIn());
		
		// medarbejder indtaster løbenummer for projekt
		int serialNumber = 1;
		
		// medarbejder tilføjer sig selv som projektleder
		project1.addLeader(ProjectManagerApp.getEmployeeLoggedIn());
		
		// checker at medarbejderen er angivet som projektleder
		assertEquals(ProjectManagerApp.getEmployeeLoggedIn(), project1.getLeader());
	}		
}
