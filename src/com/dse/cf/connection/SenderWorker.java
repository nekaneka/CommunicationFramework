package com.dse.cf.connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.dse.cf.controller.NodeBase;
import com.dse.cf.exception.DisconnectedFromSystemException;
import com.dse.cf.messagesdata.Message;

public class SenderWorker implements Runnable {
	
	private CommunicationNode node; 
	private Socket socket;
	private ObjectOutputStream oos;
	private NodeBase nodeBase; 
 
	
	public SenderWorker(CommunicationNode node, NodeBase nodeBase) {
		this.node = node;
		this.socket = node.getSocket();
		this.nodeBase = nodeBase;
		try {
			this.oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.err.println("SenderWorker Soket OutputStream Establishment FAILD!" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		while(!socket.isClosed()) {
			
			try {
	            Thread.sleep(100);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	
			Message message = node.getOneMessageToSend();
			
			if(message != null) {
				try {
					oos.writeObject(message);
					oos.flush();
					
				} catch (IOException e) {
					System.err.println("Node Connection terminated on node: " + node.getNodeId());
					
	                try {
	                	socket.close();
						nodeBase.removeNode(node.getNodeId());
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					
					}catch (DisconnectedFromSystemException e2) {
						System.err.println(e2.getMessage());
					}
				}
			}
		}

	}

}
