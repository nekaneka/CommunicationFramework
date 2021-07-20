package com.dse.cf.messagesdata;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Storage for IDs of arrived messages. Once created only one Storage available for the System.
 * @author Adem
 *
 */
public class MessageIdStorage {

	private static MessageIdStorage storage = null;	
	private Map<String, Timestamp> processedMessageIdsTest;
	
	
	private MessageIdStorage() {
		processedMessageIdsTest = new HashMap<String, Timestamp>();
		clearOldIds();
	}

	public static MessageIdStorage getIDStorage() {
		if(storage == null) {
			storage = new MessageIdStorage();
		}
		
		return storage; 
	}
	
	
	/**
	 * Returns true if the id is saved in the storage
	 * @param messageId
	 * @return
	 */
	public boolean checkForId(String messageId) {
		return processedMessageIdsTest.containsKey(messageId);
	}
	
	
	/**
	 * Saves id to storage if it is already not saved
	 * @param messageId
	 */
	public void saveMessageId(String messageId) {		
		if(!processedMessageIdsTest.keySet().contains(messageId))
			processedMessageIdsTest.put(messageId, new Timestamp(System.currentTimeMillis()));
	}
	
	
	/**
	 * Method that is invoked every 5 seconds and deletes outdated IDs.  
	 */
	private void clearOldIds() {
		long activateAfter = 5000;
		Timer t = new java.util.Timer();
		t.schedule( 
		        new java.util.TimerTask() {
		            @Override
		            public void run() {             	                
		                processedMessageIdsTest.entrySet().removeIf(
		                		set -> System.currentTimeMillis() - set.getValue().getTime() > 6000
		                		);
		            }
		        }, 
		        0, activateAfter
		);
		
	}
}
