package projectmanager.ui;

import java.io.PrintWriter;
import java.util.List;

import projectmanager.app.Employee;
import projectmanager.app.Project;
import projectmanager.app.ProjectManagerApp;
import projectmanager.app.Activity;

public class ManageProjectScreen extends Screen {
	private int serialNumber;
	private Project project;
	private String[] choices;
	private String[] names;
	private int noOfActivities = 0;

	public ManageProjectScreen(int serialNumber) {
		this.serialNumber = serialNumber;
		this.project      = ProjectManagerApp.getCompany().projectBySerialNumber(serialNumber);
	}

	@Override
	void printMenu(PrintWriter out) {
		Project p = this.project;
		StringBuilder s = new StringBuilder(this.formatTitle("Project: "+p.getName())+this.project.getReport()+"\n");
		String addActivity = "Add activity";
		String addLeader = "Set project leader";
		String printReport = "Print report";
		String peopleTime = "See when people have time";
		List<Activity> activities = project.getActivities();
		this.noOfActivities = activities.size();

		if(noOfActivities > 0){
			s.append("Following activities are registered under this project:\n");
			this.choices = new String[noOfActivities + 4];
			this.names = new String[noOfActivities];
			for(int i = 0; i < noOfActivities; i++){
				Activity activity = activities.get(i);
				this.choices[i] = "* " + activity.getName();
				this.names[i] = activity.getName();
			}
			this.choices[noOfActivities]   = addActivity;
			this.choices[noOfActivities+1] = addLeader;
			this.choices[noOfActivities+2] = printReport;
			this.choices[noOfActivities+3] = peopleTime;
		} else {
			s.append("No activities are registered.\n");
			this.choices = new String[] { addActivity, addLeader, printReport, peopleTime };
		}
		s.append(this.menuString(this.choices, "Back"));
		this.println(out, s.toString());
	}

	@Override
	boolean processInput(String input, PrintWriter out) {
		int selection = this.parseNumberInput(input, out);
		this.clearScreen(out);
		if(selection == 0){
			this.ui.setScreen(new ProjectMenuScreen());
			return false;
		}
		if (this.noOfActivities > 0){
			if(selection <= this.noOfActivities && selection >= 0){
				this.ui.setScreen(new ManageActivitiesScreen(this.names[selection-1], project));
			} else {
				switch(selection-this.noOfActivities){
				case 1:
					this.ui.setScreen(new CreateActivityScreen(project));
					break;
				case 2:
					this.ui.setScreen(new SetLeaderScreen(project));
					break;
				case 3:
					// printReport
					this.println(out, this.project.getReport() + "\n");
					break;
				case 4:
					this.ui.setScreen(new ChooseWorkWeekScreen("SeePeopleTime", project));
					break;
				default:
					this.wrongInputMessage(out);
				}
			}
		} else {
			switch(selection){
			case 1:
				this.ui.setScreen(new CreateActivityScreen(project));
				break;
			case 2:
				this.ui.setScreen(new SetLeaderScreen(project));
				break;
			case 3:
				// printReport
				this.println(out, this.project.getReport() + "\n");
				break;
			case 4:
				this.ui.setScreen(new ChooseWorkWeekScreen("SeePeopleTime", project));
				break;
			default:
				this.wrongInputMessage(out);
			}
		}
		return false;
	}

}
