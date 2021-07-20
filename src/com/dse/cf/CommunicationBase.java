package com.dse.cf;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dse.cf.connection.CommunicationServer;
import com.dse.cf.controller.NodeBase;
import com.dse.cf.messageprocessor.MessageProcessor;
import com.dse.cf.messagesdata.Message;
import com.dse.cf.messagesdata.MessageIdStorage;

/**
 * Main class over which the communication framework is initiated and controlled
 * @author Adem
 *
 */
public class CommunicationBase {
	
	private NodeBase base; 
	private static MessageIdStorage IDstorage; 
	
	private String ipAddress; 
	private	Map<String, MessageProcessor> subscriptionList;
		
	/**
	 * Constructor for the CommunicationBase.  
	 * @param subscriptionList the list of Strings and Object of type MessageProcessor.
	 * Messages will be forwarded to the specifies Object based on the Key String.
	 * The Key String will be cast to UpperCase for correct comparison. 
	 * @throws UnknownHostException
	 */
	public CommunicationBase(Map<String, MessageProcessor> subscriptionList) throws UnknownHostException {
		CommunicationBase.IDstorage = MessageIdStorage.getIDStorage();
		this.ipAddress = InetAddress.getLocalHost().getHostAddress();
		 
		this.subscriptionList = new HashMap<String, MessageProcessor>();	
			for(String s: subscriptionList.keySet()) {
				this.subscriptionList.put(s.toUpperCase(), subscriptionList.get(s));
			} 
			
			for(String s: this.subscriptionList.keySet()) {
				System.out.println("Topic: " + s + " " + this.subscriptionList.get(s).getClass() );
			}
			 
		base = new NodeBase(this.subscriptionList);
	}
	
	
	
	/**
	 * Starts Server Thread on which the service will be available
	 * @param port The port number on which the Server will start
	 */
	public void startServer(int port) {
		new Thread(new CommunicationServer(base, port, ipAddress)).start();
	}
	
	public void connectTo(List<String> connections) {
		
		for(String newConnection: connections) {
			String[] parts = newConnection.split(":");
			String ipAddress = parts[0]; 
			int port = Integer.parseInt(parts[1]);
			
			base.connectTo(ipAddress, port);
		}
	}
	
	
	/**
	 * Used to pass an Object of type Message to the CommunicationFramework
	 * @param message Object used for the exchange of data
	 */
	public void sendMessage(Message message) {
		IDstorage.saveMessageId(message.getMessageId());
		
		base.passMessageToAll(message);
	}
	
	
	/**
	 * Used to block or unblock forwarding of Messages of the types that the Cf is subscribed to
	 * @param blockForwarding boolean value that indicates if the forwarding is blocked or not. If true forwarding is blocked, false not blocked.
	 */
	public void blockSubscribedForwarding(boolean blockForwarding) {
		base.setBlocking(blockForwarding);
	}
	
}
