package com.dse.cf.controller;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dse.cf.connection.CommunicationNode;
import com.dse.cf.exception.DisconnectedFromSystemException;
import com.dse.cf.messageprocessor.MessageProcessor;
import com.dse.cf.messagesdata.Message;


/**
 * Storage for Nodes
 * @author Adem
 *
 */
public class NodeBase {

	private List<CommunicationNode> nodes; 
	private Map<String, MessageProcessor> topicProcessors;
	private boolean forwardingBlocked;
	 
	
	public NodeBase(Map<String, MessageProcessor> topicProcessors) {
		this.forwardingBlocked = false;
		this.topicProcessors = topicProcessors; 
		this.nodes = new ArrayList<CommunicationNode>();
	}

	/**
	 * Passes a message to all Nodes. 
	 * @param message
	 */
	public void passMessageToAll(Message message) {
		nodes.stream().forEach(f->f.addMessageToSend(message));
	}
	 
	/**
	 * Creates and saves a new Node when a connection arrives on the server
	 * @param socket connecting to the arrived connection on the server
	 */
	public void newConnection(Socket socket) {
		System.out.println("*** NEW CONNECTION ESTABLISHED! ***");
		nodes.add(new CommunicationNode(socket, topicProcessors, this)); 
	}
	
	
	/**
	 * Establishes a new connection to the specifies IpAddress and port. If successful a new node is created and saved.
	 * @param ipAddress
	 * @param port
	 */
	public void connectTo(String ipAddress, int port) {
		
		System.out.println("*** CONNECTING TO "  + ipAddress + "-" + port +"! ***");
		
		try {
			Socket socket = new Socket(ipAddress, port);
			nodes.add(new CommunicationNode(socket, topicProcessors, this)); 
		} catch (IOException e) {
			System.err.println("Connection FAILED to: " + ipAddress + "-" +port);
		}
	
	}

	
	/**
	 * If a connection is terminated, removes the node from storage with the specified ID
	 * @param nodeId ID of the to be removed Node 
	 * @throws DisconnectedFromSystemException thrown if there are no more connections and we are separated from the communication system
	 */
	public void removeNode(String nodeId) throws DisconnectedFromSystemException {
		nodes.removeIf(node -> node.getNodeId().equals(nodeId));
		if(nodes.isEmpty()) {
			throw new DisconnectedFromSystemException("You have been Diconnected from the communication System!");
		}
	}
	
	
	/**
	 * Returns if the forwarding is blocked or not. 
	 * @return 
	 */
	public boolean isForwardingBlocked() {
		return forwardingBlocked;
	}

	/**
	 * Sets the value of forwardingBlocked to the passed parameter
	 * @param blockForwarding
	 */
	public void setBlocking(boolean blockForwarding) {
		this.forwardingBlocked = blockForwarding;
		
	}

}
