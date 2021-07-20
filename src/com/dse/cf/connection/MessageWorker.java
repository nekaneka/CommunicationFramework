package com.dse.cf.connection;

import java.util.Map;

import com.dse.cf.controller.NodeBase;
import com.dse.cf.messageprocessor.MessageProcessor;
import com.dse.cf.messagesdata.Message;

public class MessageWorker implements Runnable {

	private Map<String, MessageProcessor> processor; 
	private CommunicationNode node; 
	private NodeBase nodeBase; 
	 
	public MessageWorker(Map<String, MessageProcessor> topicProcessors, CommunicationNode node, NodeBase nodeBase) {
		this.node = node; 
		this.processor = topicProcessors;
		this.nodeBase = nodeBase;
	}
	
	
	@Override
	public void run() {
		
		while(true) {
			Message messageToProcess = node.getOneMessage(); 
			
			if(messageToProcess != null) {
				if(node.checkSubscription(messageToProcess.getTopic())) {
				 node.getProccessor(messageToProcess.getTopic()).processMessage(messageToProcess);
				}
	          
				if(!nodeBase.isForwardingBlocked()) {
					nodeBase.passMessageToAll(messageToProcess);
				}else if(!node.checkSubscription(messageToProcess.getTopic())) {
				  	nodeBase.passMessageToAll(messageToProcess);
				}
			}
		}

	}
}
