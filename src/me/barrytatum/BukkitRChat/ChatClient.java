package me.barrytatum.BukkitRChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import biz.source_code.base64Coder.Base64Coder;

public class ChatClient implements Runnable {

	Socket connection;
	PrintWriter out;
	BufferedReader in;

	/**
	 * Constructor that allows use of a socket's input and output streams.
	 * 
	 * @param connection
	 * @throws IOException
	 */

	ChatClient(Socket connection) throws IOException {
		this.connection = connection;
		this.out = new PrintWriter(connection.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
	}
	
	/**
	 * Sends a message to the currently connected client.
	 * 
	 * @param message
	 */

	public void sendMessage(String message) {
		this.out.println(message);
	}
	
	/**
	 * Listen for messages on this socket.
	 */

	public void run() {

		String encodedString;

		while (true) {

			try {
				if ((encodedString = this.in.readLine()) != null) {

					String name, message;

					String[] container = encodedString.split(",");

					name = Base64Coder.decodeString(container[0]);
					message = Base64Coder.decodeString(container[1]);

					ChatHandler.sendChat(name, message);
				}

			} catch (IOException e) {
				return;
			}

		}
	}
}
