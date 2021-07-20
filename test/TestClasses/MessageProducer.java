package TestClasses;

import com.dse.cf.messageprocessor.MessageProcessor;
import com.dse.cf.messagesdata.Message;

public class MessageProducer implements MessageProcessor{
	
	private Message message = null;
	
	@Override
	public void processMessage(Message message) {
		this.message = message;
	}
	
	
	public Message getMessage() {
		return message;
	}

}
