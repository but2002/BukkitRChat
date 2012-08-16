package me.barrytatum.BukkitRChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.BufferedReader;

import biz.source_code.base64Coder.Base64Coder;

public class ChatServer implements Runnable {

	public ServerSocket server;
	public int port;
	public ArrayList<ChatClient> connectedClients;
	public BufferedReader in;

	/**
	 * Constructor to create an instance of a chat server, allowing clients to
	 * connect.
	 * 
	 * @param port
	 */

	ChatServer(int port) {

		// Bind the chat server to the specified port.
		try {
			this.server = new ServerSocket(port);

		} catch (IOException e) {
			BukkitRChat.logger.warning(String.format(
					"Unable to bind on port %d.", port));
		}

		// Initialize pool of connected clients.
		this.connectedClients = new ArrayList<ChatClient>();
	}

	/**
	 * Accept connections to the server.
	 */

	public void run() {

		while (true) {
			Socket connection = null;

			// Wait until a connection is requested.

			try {
				connection = server.accept();

			} catch (IOException e) {
				BukkitRChat.logger.warning("Unable to establish connection.");
				return;
			}

			// Create a client instance.

			ChatClient client;

			try {
				client = new ChatClient(this, connection);

			} catch (IOException e) {
				BukkitRChat.logger.warning("Unable to open stream to client.");
				return;
			}

			// Add the client to the pool of clients.
			this.connectedClients.add(client);

			Thread clientThread = new Thread(client);
			clientThread.start();
		}
	}

	/**
	 * Send a message to connected clients.
	 * 
	 * @param name
	 * @param message
	 */

	public void sendChat(String name, String message) {

		if (this.connectedClients.size() == 0)
			return;

		Iterator<ChatClient> it = this.connectedClients.iterator();

		String encodedName = Base64Coder.encodeString(name);
		String encodedMessage = Base64Coder.encodeString(message);

		while (it.hasNext()) {
			ChatClient client = it.next();
			client.sendMessage(String.format("%s,%s", encodedName,
					encodedMessage));
		}
	}

	/**
	 * Send a message to all connected clients, except for the specified client
	 * ID.
	 * 
	 * @param name
	 * @param message
	 * @param clientId
	 */

	public void sendChat(String name, String message, int clientId) {
		if (this.connectedClients.size() == 0)
			return;

		Iterator<ChatClient> it = this.connectedClients.iterator();

		String encodedName = Base64Coder.encodeString(name);
		String encodedMessage = Base64Coder.encodeString(message);

		while (it.hasNext()) {
			ChatClient client = it.next();

			if (client.getClientId() != clientId) {
				client.sendMessage(String.format("%s,%s", encodedName,
						encodedMessage));
			}
		}
	}
}