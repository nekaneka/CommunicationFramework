import static org.junit.jupiter.api.Assertions.*;

import com.dse.cf.messagesdata.MessageIdStorage;

class MessageStorageIdTest {

	MessageIdStorage storage = MessageIdStorage.getIDStorage();
	
	@org.junit.jupiter.api.Test
	void IdTesting() {
		storage.saveMessageId("1234");
		
		assertEquals(true, storage.checkForId("1234"));
		
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(false, storage.checkForId("1234"));
	}

}
