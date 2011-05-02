package projectmanager.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;

import projectmanager.app.Activity;
import projectmanager.app.Company;
import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;
import projectmanager.app.RegisteredWork;

/**
 * A class the defines a common setUp
 * Test classes that want to use that data should inherit from this class.
 */
public class SampleDataSetup {
	
	public static ProjectManagerApp PMApp = new ProjectManagerApp();
	public static Company company = PMApp.getCompany();
	public static Random r = new Random();
	
	//@Before
	//public void setUp() throws Exception {
	public static void main(String[] args) throws RegisterWorkException {

		
		// 10 employees with random names
		for (int i = 1; i <= 10; i++) {
			String user = Long.toString(Math.abs(r.nextLong()), 36).substring(0, r.nextInt(2)+2);
			company.addEmployee(new Employee(user));
		}
		
		// 5 projects
		for (int i = 1; i <= 5; i++) {
			company.addProject(new Project("project"+i, "Google"));
		}
		
		// Add project leaders to all 5 projects except the last one
		for (int i = 0; i <= 3; i++) {
			Employee employee = company.getEmployees().get(i);
			company.getProjects().get(i).addLeader(employee);
		}
		
		
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				// Add 3 activities to each project
				Project currentProject = company.getProjects().get(i);
				currentProject.addActivity(new Activity("act"+j));
				
				for (int k = 1; k <= 3; k++) {
					int rand = (int)(Math.random() * company.getEmployees().size());
					Activity latestActivity = currentProject.getActivities().get(currentProject.getActivities().size()-1);
					Employee randomEmployee = company.getEmployees().get(rand);
					
					// Add 3 random employees to each activity
					latestActivity.addEmployee(randomEmployee);
					
					// Delegate work
					//randomEmployee.addDelegatedWork(new DelegatedWork(40, latestActivity));
					
					// Register work
					randomEmployee.addRegisteredWork(new RegisteredWork(latestActivity, rand/2+10));
				}
			}
		}
		
		System.out.println(company.getProjects().get(0).getReport());
	}
}
