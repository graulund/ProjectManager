package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AssignEmployeeToActivity {
	
	private Employee employee1;
	private Employee employee2;
	Activity activity;
	Company company;
	Project project;
	
	@Before
	public void setUpProjectAndEmployee() throws OperationNotAllowedException, CreatingActivityException {
		company = ProjectManagerApp.getCompany();
		
		employee1 = new Employee("emp1");
		employee2 = new Employee("emp2");
		company.addEmployee(employee1);
		company.addEmployee(employee2);
		
		project = new Project("lolproject", "Google");
		company.addProject(project);	
	}
	
	/**
	 * Tester scenariet, hvor en medarbejder successfuldt
	 * tildeler en medarbejder til en aktivitet
	 * @throws CreatingActivityException 
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testAssignEmployeeToActivity() throws OperationNotAllowedException, CreatingActivityException {
		// medarbejder logger ind med initialer, der findes i databasen
		boolean login = ProjectManagerApp.employeeLogin("emp1");
		
		// tilf�jer den ene projektleder som projektleder
		project.addLeader(employee1);
		System.out.println(ProjectManagerApp.getEmployeeLoggedIn().getUsername());
		
		// tilf�jer et projekt
		activity = new Activity("lolcat");
		project.addActivity(activity);	
		
		// medarbejderen v�lger et projekt, som han er projektleder for
		Project projectChosen = ProjectManagerApp.getEmployeeLoggedIn().getProjectLeader().get(0);
		
		// medarbejdern v�lger en aktivitet
		Activity activityChosen = projectChosen.activityByName("lolcat");
		
		// medarbejderen v�lger en medarbejder
		Employee employeeChosen = company.employeeByUsername("emp2");
		
		// indtaster uger og �r
		int yearFrom = 2011;
		int yearTo = 2011;
		int weekFrom = 5;
		int weekTo = 8;
		
		// medarbejdern v�lger antal timer
		int hours = 5;
		
		// medarbejder tilf�jer en medarbejder til en aktivitet
		employeeChosen.addDelegatedWork(weekFrom, weekTo, yearFrom, yearTo, activityChosen, hours);
		
		// checker en masse
		assertEquals(activityChosen, employee2.getWorkWeek(5, 2011).getDelegatedWork().get(0).getActivity());
		assertEquals(4, employee2.getWorkWeek(5, 2011).getDelegatedWork().get(0).getHalfHoursWorked());	
		
		assertEquals(activityChosen, employee2.getWorkWeek(6, 2011).getDelegatedWork().get(0).getActivity());
		assertEquals(2, employee2.getWorkWeek(6, 2011).getDelegatedWork().get(0).getHalfHoursWorked());	
		
		assertEquals(activityChosen, employee2.getWorkWeek(7, 2011).getDelegatedWork().get(0).getActivity());
		assertEquals(2, employee2.getWorkWeek(7, 2011).getDelegatedWork().get(0).getHalfHoursWorked());
	}
}
