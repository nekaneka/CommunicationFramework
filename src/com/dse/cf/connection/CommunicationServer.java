package com.dse.cf.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.dse.cf.controller.NodeBase;

/**
 * Opens a server on which new connections can be accepted
 * @author Adem
 *
 */
public class CommunicationServer implements Runnable {

	private ServerSocket serverSocket;
	private NodeBase base; 
	private int port; 
	
	
	/**
	 * 
	 * @param base used to save new Nodes after a new Connection is established
	 * @param the port number
	 * @param IPAddress IP Address on which the service is available:
	 */
	public CommunicationServer(NodeBase base, int port, String IPAddress) {
		this.base = base; 
		this.port = port; 
	}
	
	@Override
	public void run() {
		
		try {
			System.out.println("Service available on port: " + port + " Address: " + InetAddress.getLocalHost().getHostAddress());
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.err.println("Server COULD NOT START! " + e.getMessage());
		}

		
		while (true) {
            try {
                final Socket socket = serverSocket.accept();
                base.newConnection(socket);
            } catch (IOException e) {
                System.err.println("Socket Connection Acception Failed! " + e.getMessage());
            }
        }

		
	}

}
