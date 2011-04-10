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
		
		// Add project leaders to all projects
		for (int i = 1; i <= 5; i++) {
			company.projectBySerialNumber(i).addLeader(company.employeeByUsername("emp"+i));
			company.employeeByUsername("emp"+i).setProjectLeaderOf(company.projectBySerialNumber(i));
		}
		
		PMApp.
		
		
		
		
		
		company.employeeByUsername("emp1");
		
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
		}
	}
}
