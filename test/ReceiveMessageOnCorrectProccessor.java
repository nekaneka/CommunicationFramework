import static org.junit.jupiter.api.Assertions.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.dse.cf.CommunicationBase;
import com.dse.cf.data.ActuatorData;
import com.dse.cf.messageprocessor.MessageProcessor;
import com.dse.cf.messagesdata.Message;

import TestClasses.MessageProducer;

class ReceiveMessageOnCorrectProccessor {

	
	@Test
	void test() {
		Map<String, MessageProcessor> testMap1 = new HashMap<>();
		String test1 = "TeSt1";
		MessageProcessor mpTest = new MessageProducer();
		testMap1.put(test1, new MessageProducer());
		CommunicationBase testBase1 = null;
		
		
		Map<String, MessageProcessor> testMap2 = new HashMap<>();
		String test21 = "teSt1";
		testMap1.put(test21, mpTest);
		//testMap1.put(test2, new MessageProducer2());
		CommunicationBase testBase2 = null;
		
		try {
			testBase1 = new CommunicationBase(testMap1);
			testBase2 = new CommunicationBase(testMap2);
			testBase1.startServer(15000);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Thread.sleep(100);
			System.out.println("Sleep");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testBase2.connectTo(Arrays.asList("localhost:15000"));
		
		
		Message testMessage = new Message("tesT1", "me", "me", new ActuatorData());
		testBase1.sendMessage(testMessage);
		
		while(((MessageProducer) mpTest).getMessage() == null)
			return;
			
		assertEquals(((MessageProducer) mpTest).getMessage(), testMessage.getMessageId());
		assertTrue(((MessageProducer) mpTest).getMessage().getSenderObject() instanceof ActuatorData);

		
		
	}

}
