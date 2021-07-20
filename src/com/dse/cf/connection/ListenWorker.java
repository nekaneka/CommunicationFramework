package com.dse.cf.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.dse.cf.controller.NodeBase;
import com.dse.cf.exception.DisconnectedFromSystemException;
import com.dse.cf.messagesdata.Message;
import com.dse.cf.messagesdata.MessageIdStorage;

/**
 * 
 * @author Adem
 *
 */
public class ListenWorker implements Runnable {

	private CommunicationNode node; 
	private Socket socket; 
	private ObjectInputStream ois;
	private NodeBase nodeBase; 
	
	private static MessageIdStorage IDStorage;
	
	public ListenWorker(CommunicationNode node, NodeBase nodeBase) {
		this.node = node; 
		this.nodeBase = nodeBase;
		this.socket = node.getSocket();
		this.IDStorage = MessageIdStorage.getIDStorage();
		 
		openInputStream();
	}

	private void openInputStream() {
		
		try {
			this.ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("ListenWorker OutputStream establisment FAILED! " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		while (!socket.isClosed()) {
            try {
            	
            	try {
    	            Thread.sleep(200);
    	        } catch (InterruptedException e) {
    	            e.printStackTrace();
    	        }
            	
                Message message = (Message) ois.readObject();
 
                if(!IDStorage.checkForId(message.getMessageId())) {
                	IDStorage.saveMessageId(message.getMessageId());
                	
                	node.addArrivedMessage(message);
                }

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
            } catch (ClassNotFoundException e) {
                System.err.println("NodeListener Class Exception!" + e);
            }
        }
	


	}

}
