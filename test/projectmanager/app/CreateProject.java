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

public class CreateProject {
	
	/**
	 * Tester scenariet, hvor en medarbejder successfuldt opretter et project.
	 */
	@Test
	public void testCreateProject() {
		ProjectManagerApp PMApp = new ProjectManagerApp();
		Company company = PMApp.getCompany();
		Employee employee = new Employee("hlb");
		company.addEmployee(employee);
		
		// checker at der ikke eksisterer nogle projekter
		assertTrue(company.getProjects().isEmpty());
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(PMApp.isEmployeeLoggedIn());
		
		// medarbejderen indtaster f¿lgende informationer
		String name = "Software Engineering";
		String client = "Google";
		
		// projekt oprettes ud fra informationer og tilf¿jes til App'en
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		// checker at projektet er oprettet korrekt i App'en
		List<Project> projects = company.getProjects();
		assertEquals(1,projects.size());
		assertEquals(name, projects.get(0).getName());
		assertEquals(client, projects.get(0).getClient());
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder indtaster initialerne forkert.
	 */
	@Test
	public void testCreateProjectWrongLogin() {
		ProjectManagerApp PMApp = new ProjectManagerApp();
		Company company = PMApp.getCompany();
		
		// checker at der ikke eksisterer nogle projekter
		assertTrue(company.getProjects().isEmpty());
		
		// medarbejder logger ind med initialer, der IKKE findes i databasen
		boolean login = PMApp.employeeLogin("hlb");
		
		// checker at medarbejderen IKKE er logget ind
		assertFalse(login);
		assertFalse(PMApp.isEmployeeLoggedIn());
	}
	
}
