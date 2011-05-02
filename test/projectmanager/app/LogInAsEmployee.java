package projectmanager.app;

import static org.junit.Assert.*;
import org.junit.Test;

import projectmanager.app.Company;
import projectmanager.app.Employee;
import projectmanager.app.ProjectManagerApp;


public class LogInAsEmployee {
	
	/**
	 * Tester scenariet, hvor en medarbejder logger ind
	 */
	@Test
	public void testLogin() {
		
		
		Employee employee = new Employee("hlb");
		Company company = ProjectManagerApp.getCompany();
		company.addEmployee(employee);
		
		// checker at medarbejderen ikke er logget ind
		assertFalse(ProjectManagerApp.isEmployeeLoggedIn());
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(ProjectManagerApp.isEmployeeLoggedIn());
		assertEquals(employee, ProjectManagerApp.getEmployeeLoggedIn());
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder indtaster forkerte initialer
	 * eller initialer, der ikke eksisterer i databasen
	 */
	@Test
	public void testLoginFailed() {
		
		
		
		// checker at medarbejderen ikke er logget ind
		assertFalse(ProjectManagerApp.isEmployeeLoggedIn());
		
		// medarbejder logger ind med initialer, der ikke findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// checker at medarbejderen ikke er logget ind
		assertFalse(login);
		assertFalse(ProjectManagerApp.isEmployeeLoggedIn());
		assertEquals(null, ProjectManagerApp.getEmployeeLoggedIn());
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder logger ud
	 */
	@Test
	public void testLogout() {
	
		
		Employee employee = new Employee("hlb");
		Company company = ProjectManagerApp.getCompany();
		company.addEmployee(employee);
		
		// checker at medarbejderen ikke er logget ind
		assertFalse(ProjectManagerApp.isEmployeeLoggedIn());
		
		// medarbejder logger ind med initialer, der ikke findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(ProjectManagerApp.isEmployeeLoggedIn());
		assertEquals(employee, ProjectManagerApp.getEmployeeLoggedIn());
		
		// medarbejderen logger ud
		boolean logout = ProjectManagerApp.employeeLogout();
		
		// checker at medarbejderen ikke er logget ind
		assertFalse(logout);
		assertFalse(ProjectManagerApp.isEmployeeLoggedIn());
		assertEquals(null, ProjectManagerApp.getEmployeeLoggedIn());
	}
}
