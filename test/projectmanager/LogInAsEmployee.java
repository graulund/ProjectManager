package projectmanager;

import static org.junit.Assert.*;

import org.junit.Test;


public class LogInAsEmployee {
	
	/**
	 * Tester scenariet, hvor en medarbejder logger ind
	 */
	@Test
	public void testLogin() {
		
		ProjectManagerApp PMApp = new ProjectManagerApp();
		
		// checker at medarbejderen ikke er logget ind
		assertFalse(PMApp.employeeLoggedIn());
		
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("hlb");
		
		// checker at medarbejderen er logget ind
		assertTrue(login);
		assertTrue(PMApp.employeeLoggedIn());		
	}
	
	@Test
	public void testLoginFailed() {
		ProjectManagerApp PMApp = new ProjectManagerApp();
		
		// checker at medarbejderen ikke er logget ind
		assertFalse(PMApp.employeeLoggedIn());
		
		// medarbejder logger ind med initialer, der ikke findes i databasen (forkerte)
		boolean login = PMApp.employeeLogin("lol");
		
		// checker at medarbejderen ikke er logget ind
		assertFalse(login);
		assertFalse(PMApp.employeeLoggedIn());	
		
		
		
	}

}
