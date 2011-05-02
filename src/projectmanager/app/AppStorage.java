package projectmanager.app;

public class AppStorage {
	public static final String default_filename = "pmapp.data";
	private String filename;
	public AppStorage(){
		this(default_filename);
	}
	public AppStorage(String filename){
		this.filename = filename;
	}
	public String getFilename(){
		return filename;
	}
	public void saveCurrentState(){
		
	}
	public void loadState(){
		
	}
}
