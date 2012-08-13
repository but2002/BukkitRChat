package me.barrytatum.BukkitRChat;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class DataStream implements Runnable {
	
	public Socket connection;
	public PrintWriter out;
	public BufferedReader in;
	
	DataStream(Socket connection) throws IOException {
		this.connection = connection;
		this.out = new PrintWriter(connection.getOutputStream());
	}
	
	public void send(String message) {
		
	}

	public void run() {
		try {
			this.in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
			this.out = new PrintWriter( connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
