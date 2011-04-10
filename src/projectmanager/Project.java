package projectmanager;

public class Project {
	private String name;
	private String client;
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
}
