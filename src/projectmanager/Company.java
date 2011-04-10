package projectmanager;

import java.util.ArrayList;
import java.util.List;

public class Company {
	private ArrayList<Project> projects = new ArrayList<Project>();
	
	
	public List<Project> getProjects() {
		return this.projects;
	}
	public void addProject(Project project) {
		// TODO: What if it already exists in the list?
		this.projects.add(project);
	}
	
}
