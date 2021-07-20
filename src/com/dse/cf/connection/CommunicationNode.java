package com.dse.cf.connection;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.dse.cf.controller.NodeBase;
import com.dse.cf.messageprocessor.MessageProcessor;
import com.dse.cf.messagesdata.Message;

/**
 * Created after a new Connection is established and responsible for sending and handling the messages that arrive over the socket.
 * @author Adem
 *
 */
public class CommunicationNode {

	private Socket socket; 
	private String nodeId; 
	private LinkedList <Message> messagesToSend; 
	private LinkedList <Message> arrivedMessaged; 
	private Map<String, MessageProcessor> topicProcessors;
	
	public CommunicationNode(Socket socket, Map<String, MessageProcessor> topicProcessors, NodeBase nodeBase) {
		this.topicProcessors = topicProcessors;
		this.socket = socket; 
		this.nodeId = UUID.randomUUID().toString(); 
		this.arrivedMessaged = new LinkedList<>(); 
		this.messagesToSend = new LinkedList<>(); 

		// starting the individual processes
		ThreadPoolExecutor senderExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
		senderExecutor.execute(new SenderWorker(this,nodeBase));
		
		ThreadPoolExecutor listenExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
		listenExecutor.execute(new ListenWorker(this, nodeBase));
		
		
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
		for(int i = 0; i < 3; i++) {
			MessageWorker worker = new MessageWorker(topicProcessors, this, nodeBase);
			executor.execute(worker);
		}
	}
	
	
	/**
	 * Saves a message to the List of messages ready to be send to the communication system
	 * @param message
	 */
	public void addMessageToSend(Message message) {
		messagesToSend.add(message); 
	}
	
	
	/**
	 * Saves a message to the List of messages that arrived from the connected node for further processing
	 * @param message
	 */
	public void addArrivedMessage(Message message) {
		arrivedMessaged.add(message); 
	}
	
	
	/**
	 * Retrieves and removes the first message of the list of received messages. 
	 * @return the first message of this list, or null if this list is empty.
	 */
	public synchronized Message getOneMessage() {
		return arrivedMessaged.poll();
	}
	
	
	/**
	 * Retrieves and removes the first message of the list of the messages that are going to be sent from . 
	 * @return the first message of this list, or null if this list is empty.
	 */
	public synchronized Message getOneMessageToSend() {
		return messagesToSend.pollFirst();
	}
	
	
	/**
	 * Return the socket of the Node
	 * @return
	 */
	public Socket getSocket() {
		return socket;
	}
	
	
	/**
	 * Return true if the subscription map contains the key topic, otherwise false.
	 * @param topic
	 * @return
	 */
	public boolean checkSubscription(String topic) {
		return this.topicProcessors.keySet().contains(topic);
	}

	
	/**
	 * Return the MessageProcessor to which the specified topic is mapped
	 * @param topic
	 * @return
	 */
	public synchronized MessageProcessor getProccessor(String topic) {
		return topicProcessors.get(topic);	
	}
	
	
	/**
	 * Return the id of the Node
	 * @return id of Node
	 */
	public String getNodeId() {
		return nodeId;
	}
}
