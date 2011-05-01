package projectmanager.app;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AssignEmployeeToActivity {
	
	private ProjectManagerApp PMApp;
	private Employee employee1;
	private Employee employee2;
	Activity activity;
	Company company;
	Project project;
	
	@Before
	public void setUpProjectAndEmployee() {
		PMApp = new ProjectManagerApp();
		company = PMApp.getCompany();
		
		employee1 = new Employee("emp1");
		employee2 = new Employee("emp2");
		company.addEmployee(employee1);
		company.addEmployee(employee2);
		
		project = new Project("lolproject", "Google");
		company.addProject(project);
		
		activity = new Activity("lolcat");
		project.addActivity(activity);		
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder successfuldt
	 * tildeler en medarbejder til en aktivitet
	 */
	@Test
	public void testAssignEmployeeToActivity() {
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = PMApp.employeeLogin("emp1");
		
		// tilf�jer den ene projektleder som projektleder
		project.addLeader(employee1);
		
		// medarbejderen v�lger et projekt, som han er projektleder for
		Project projectChosen = PMApp.getEmployeeLoggedIn().getProjectLeader().get(0);
		
		// medarbejdern v�lger en aktivitet
		Activity activityChosen = projectChosen.activityByName("lolcat");
		
		// medarbejderen v�lger en medarbejder
		Employee employeeChosen = company.employeeByUsername("emp2");
		
		// medarbejdern v�lger antal timer
		int hours = 10;
		
		// medarbejder tilf�jer en medarbejder til en aktivitet
		PMApp.assignEmployeeToActivity(employee2, activity, hours);
		
		// checker om medarbejderen er tilf�jet til aktiviteten
		assertEquals(activity, employee2.getDeligatedWork().get(0));
		assertEquals(hours, employee2.getDeligatedWork().get(0).getHours());
		
	}

}
