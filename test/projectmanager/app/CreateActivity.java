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
	 */
	@Test
	public void CreateActivity() {
		Company company = ProjectManagerApp.getCompany();
		
		String name = "Software Engineering";
		String client = "Google";
		Project project1 = new Project(name, client);
		company.addProject(project1);
		
		Employee employee = new Employee("hlb");
		company.addEmployee(employee);
		project1.addLeader(employee);
		
		assertEquals(employee, project1.getLeader());
		
		// checker at der ikke eksisterer nogle aktiviteter
		assertTrue(project1.
	}
}
