package projectmanager.app;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import projectmanager.app.Company;
import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;

public class CheckYourTime {

	/**
	 * Se hvornår du selv har tid
	 */
	@Test
	public void checkYourTime() {
		
		Employee employee = new Employee("hlb");
		Company company = ProjectManagerApp.getCompany();
		company.addEmployee(employee);
		company.addProject(new Project("project mayhem", "Google"));
		Project currentProject = company.getProjects().get(0);
		currentProject.addActivity(new Activity("funky activity"));
		
	}
}
