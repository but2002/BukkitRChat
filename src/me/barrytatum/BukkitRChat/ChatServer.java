package me.barrytatum.BukkitRChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	public ServerSocket server;
	public int port;

	ChatServer(int port) {
		try {
			this.server = new ServerSocket(port);
			this.start();
		} catch (IOException e) {

		}
		
	}
	
	private void start() throws IOException {
		while ( true ) {
			Socket connection = null;
			connection = server.accept();
			
			new Thread(new DataStream(connection)).start();
		}
	}
	
	public void sendChat(Socket connection, String name, String message) {
		
	}

}
