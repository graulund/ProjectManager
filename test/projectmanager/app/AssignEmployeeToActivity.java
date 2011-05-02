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
		
		// medarbejderen v푡ger en medarbejder
		Employee employeeChosen = company.employeeByUsername("emp2");
		
		// indtaster uger og 똱
		int yearFrom = 2011;
		int yearTo = 2011;
		int weekFrom = 5;
		int weekTo = 8;
		
		// medarbejdern v푡ger antal timer
		int hours = 10;
		
		// medarbejder tilf퓂er en medarbejder til en aktivitet
		employeeChosen.addDelegatedWork(weekFrom, weekTo, yearFrom, yearTo, activityChosen, hours);
		
		// checker en masse
		assertEquals(activityChosen, employee2.getWorkWeek(5, 2011).getDelegatedWork().get(0).getActivity());
		assertEquals(8, employee2.getWorkWeek(5, 2011).getDelegatedWork().get(0).getHalfHoursWorked());	
		
		assertEquals(activityChosen, employee2.getWorkWeek(6, 2011).getDelegatedWork().get(0).getActivity());
		assertEquals(6, employee2.getWorkWeek(6, 2011).getDelegatedWork().get(0).getHalfHoursWorked());	
		
		assertEquals(activityChosen, employee2.getWorkWeek(7, 2011).getDelegatedWork().get(0).getActivity());
		assertEquals(6, employee2.getWorkWeek(7, 2011).getDelegatedWork().get(0).getHalfHoursWorked());
	}
}
