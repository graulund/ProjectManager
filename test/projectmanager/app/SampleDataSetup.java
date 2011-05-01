package projectmanager.app;

import java.util.ArrayList;
import java.util.List;

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
	
	ProjectManagerApp PMApp = new ProjectManagerApp();
	Company company = PMApp.getCompany();
	
	@Before
	public void setUp() throws Exception {
		
		// 10 employees
		for (int i = 1; i <= 10; i++) {
			company.addEmployee(new Employee("emp"+i));
		}
		
		// 5 projects
		for (int i = 1; i <= 5; i++) {
			company.addProject(new Project("project"+i, "Google"));
		}
		
		// Add project leaders to all 5 projects except the last one
		for (int i = 1; i <= 4; i++) {
			company.projectBySerialNumber(i).addLeader(company.employeeByUsername("emp"+i));
		}
		
		
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				// Add 3 activities to each project
				Project currentProject = company.projectBySerialNumber(i);
				currentProject.addActivity(new Activity("act"+j));
				
				for (int k = 1; k <= 3; k++) {
					int r = (int)(Math.random() * company.getEmployees().size());
					Activity latestActivity = currentProject.getActivities().get(currentProject.getActivities().size()-1);
					Employee randomEmployee = company.getEmployees().get(r);
					
					// Add 3 random employees to each activity
					latestActivity.addEmployee(randomEmployee);
					
					// Register work
					randomEmployee.addRegisteredWork(new RegisteredWork(latestActivity, r/2+10));
				}
			}
		}
		
		
		
		
		
		
		
		/*
		List<Book> books = new ArrayList<Book>();
		books.add(new Book("Som001","Software Engineering - 9","Ian Sommerville"));
		books.add(new Book("Sof001","XML for Dummies","Fred Software"));
		for (int i = 1; i <= 10; i++) {
			books.add(new Book("book"+i,"Book "+i,"Author "+i));
		}
		
		libApp.adminLogin("adminadmin");
		for (Book book : books) {
			libApp.addBook(book);
		}

		List<User> users = new ArrayList<User>();
		
		Address address = new Address("Kirkevej",2344,"Herlev");
		User user = new User("1234651234","User 1","user1@library.dk",address);
		users.add(user);
		address = new Address("Lyngby",2345,"Holte");
		user = new User("1212871234","User 2","user2@library.dk",address);
		
		for (User usr : users) {
			libApp.register(usr);
		}*/
	}
}
