package me.barrytatum.BukkitRChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.BufferedReader;

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
	 *            - The port to bind.
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
				client = new ChatClient(connection);
			
			} catch (IOException e) {
				BukkitRChat.logger.warning("Unable to open stream to client.");
				return;
			}

			// Add the client to the pool of clients.
			this.connectedClients.add(client);
		}
	}

	/**
	 * Send a message to connected clients.
	 * 
	 * @param name
	 *            - The name of the speaking player.
	 * @param message
	 *            - The spoken message.
	 */

	public void sendChat(String name, String message) {

		if (this.connectedClients.size() == 0)
			return;

		Iterator<ChatClient> it = this.connectedClients.iterator();
		String composite = String.format("%s: %s", name, message);

		while (it.hasNext()) {
			ChatClient client = it.next();
			client.sendMessage(composite);
		}
	}
}
