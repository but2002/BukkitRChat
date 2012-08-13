package me.barrytatum.BukkitRChat;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

public class DataStream {
	
	Socket connection;
	PrintWriter out;

	DataStream(Socket connection) throws IOException {
		this.connection = connection;
		this.out = new PrintWriter(connection.getOutputStream());
	}
	
	public void send(String message) {
		
	}

	
}
