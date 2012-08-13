package me.barrytatum.BukkitRChat;

import java.net.Socket;

public class ConnectionHandler implements Runnable {

	Socket connection;
	
	ConnectionHandler(Socket connection) {
		this.connection = connection;
	}

	public void run() {
		
	}
	
}
