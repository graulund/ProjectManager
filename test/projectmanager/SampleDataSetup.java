package projectmanager;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

/**
 * A class the defines a common setUp
 * Test classes that want to use that data should inherit from this class.
 */
public class SampleDataSetup {
	
	ProjectManagerApp PMApp = new ProjectManagerApp();
	
	@Before
	public void setUp() throws Exception {
		Company company = PMApp.getCompany();
		
		// 10 employees
		for (int i = 1; i <= 10; i++) {
			company.addEmployee(new Employee("emp"+i));
		}
		
		// 5 projects
		for (int i = 1; i <= 5; i++) {
			company.addProject(new Project("project"+i, "Google"));
		}
		
		// Add project leaders to all 5 projects
		for (int i = 1; i <= 5; i++) {
			company.projectBySerialNumber(i).addLeader(company.employeeByUsername("emp"+i));
		}
		
		
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				// Add 3 activities to each project
				Project current_project = company.projectBySerialNumber(i);
				current_project.addActivity(new Activity("act"+j, current_project));
				
				for (int k = 1; k <= 3; k++) {
					int r = (int)(Math.random() * company.getEmployees().size());
					Activity latest_activity = current_project.getActivities().get(current_project.getActivities().size()-1);
					Employee random_employee = company.getEmployees().get(r);
					
					// Add 3 random employees to each activity
					latest_activity.addEmployee(random_employee);
					
					// Register work
					random_employee.addRegisteredWork(new RegisteredWork(random_employee, latest_activity, r/2+10));
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
