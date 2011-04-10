package projectmanager;

public class Project {
	private String name;
	private String client;
	private int serialNumber;
	private Employee projectLeader;
	
	public Project(String name, String client) {
		this.name = name;
		this.client = client;
	}
	public String getName(){
		return this.name;
	}
	public String getClient(){
		return this.client;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setClient(String client){
		this.client = client;
	}
	public void setSerialNumber(int serialKey) {
		this.serialNumber = serialKey;
	}
	public int getSerialNumber() {
		return this.serialNumber;
	}
	public void addLeader(Employee employeeLoggedIn) {
		if (this.projectLeader == null) {
			this.projectLeader = employeeLoggedIn;
		}
	}
	public Employee getLeader() {
		return this.projectLeader;
	}
}
