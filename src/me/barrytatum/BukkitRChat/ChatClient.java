package me.barrytatum.BukkitRChat;

/**
 * File:		ChatClient.java
 * Created:		8/12/2012
 * Modified:	8/15/2012
 * Author:		Blake Renton
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import biz.source_code.base64Coder.Base64Coder;

public class ChatClient implements Runnable {

	private PrintWriter out;
	private BufferedReader in;
	private ChatServer instance;
	private int clientId;

	/**
	 * Constructor that allows use of a socket's input and output streams.
	 * 
	 * @param connection
	 * @throws IOException
	 */

	ChatClient(ChatServer instance, Socket connection) throws IOException {
		this.instance = instance;
		this.out = new PrintWriter(connection.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		this.clientId = this.instance.connectedClients.size() + 1;
	}

	/**
	 * Listen for messages on this socket, decode them, and send them to the
	 * server's chat handler.
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
					this.instance.sendChat(name, message, this.clientId);
				}

			} catch (IOException e) {
				return;
			}

		} // end while()

	} // end run()

	/**
	 * Sends a message to the currently connected client.
	 * 
	 * @param message
	 */

	public void sendMessage(String message) {
		this.out.println(message);
	}

	/**
	 * Returns this instance's client ID.
	 * 
	 * @return
	 */

	public int getClientId() {
		return this.clientId;
	}
}