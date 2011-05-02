package projectmanager.app;

import org.junit.Test;

public class TestSaveObject extends SampleDataSetup {
	@Test
	public void printSaveObject(){
		AppStorage storage = new AppStorage();
		StoredData obj     = storage.storeCurrentState();
		storage.printState(obj);
	}
}
