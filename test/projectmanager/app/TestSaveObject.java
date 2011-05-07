package projectmanager.app;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestSaveObject {//extends SampleDataSetup {
	@Test
	public void printSaveObject(){
		AppStorage storage = new AppStorage();
		//StoredData obj = new StoredData();
		//StoredData obj     = storage.storeCurrentState();
		//storage.printState(obj);
		//storage.saveState(obj);
		//storage.saveCurrentState();
		StoredData obj = storage.loadState();
		storage.printState(obj);
		storage.restoreState(obj);
		ProjectManagerApp.employeeLogin("ridp");
		assertTrue(ProjectManagerApp.isEmployeeLoggedIn());
		Employee employee = ProjectManagerApp.getEmployeeLoggedIn();
		System.out.println(employee);
		System.out.println(employee.getUsername());
		System.out.println(employee.getWorkWeeks().size());
	}
}
