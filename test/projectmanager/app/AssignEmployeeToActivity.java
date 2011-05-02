package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
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
		
		// tilf퓂er den ene projektleder som projektleder
		project.addLeader(employee1);
		
		// medarbejderen v푡ger et projekt, som han er projektleder for
		Project projectChosen = PMApp.getEmployeeLoggedIn().getProjectLeader().get(0);
		
		// medarbejdern v푡ger en aktivitet
		Activity activityChosen = projectChosen.activityByName("lolcat");
		activityChosen.setStart(1, 2011);
		activityChosen.setEnd(4, 2011);
		
		// medarbejderen v푡ger en medarbejder
		Employee employeeChosen = company.employeeByUsername("emp2");
		
		// medarbejdern v푡ger antal timer
		int hours = 10;
		
		// medarbejder tilf퓂er en medarbejder til en aktivitet
		PMApp.assignEmployeeToActivity(employeeChosen, activityChosen, hours);
		
		// checker om medarbejderen er tilf퓂et til aktiviteten
		assertEquals(activityChosen, employee2.getActivity(activityChosen));
		
		// checker om der er oprettet et delegeret arbejde
		for (int i = activityChosen.getStart().get(Calendar.WEEK_OF_YEAR);
			 i <= activityChosen.getEnd().get(Calendar.WEEK_OF_YEAR); i++) {
				 assertEquals(activityChosen, employee2.getWorkWeek(i, 2011).getDelegatedWork().get(0).getActivity());				 
			 }
	}
	
		
}
