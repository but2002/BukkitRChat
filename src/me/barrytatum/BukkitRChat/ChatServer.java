package me.barrytatum.BukkitRChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	Socket connection;
	ServerSocket server;
	int port;

	ChatServer(int port) throws IOException {
		this.server = new ServerSocket(port);
	}
	
	public void sendChat(Socket connection, String name, String message) {
		
	}

}
