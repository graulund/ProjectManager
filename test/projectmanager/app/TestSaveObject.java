package projectmanager.app;

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
	}
}
