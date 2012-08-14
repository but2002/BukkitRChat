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
	public ArrayList<DataStream> connectedClients;
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
		this.connectedClients = new ArrayList<DataStream>();
	}

	/**
	 * Accept connections to the server.
	 */

	public void run() {

		while (true) {
			Socket connection = null;

			try {
				// Wait until a connection is requested.
				connection = server.accept();

			} catch (IOException e) {
				BukkitRChat.logger.warning("Unable to establish connection.");
				return;
			}

			// Create a client instance.
			DataStream outboundDataStream = new DataStream(connection);
			InputDataStream incomingDataStream = new InputDataStream(connection);

			// Add the client to the pool of clients.
			this.connectedClients.add(outboundDataStream);

			// Start the client's thread, allowing them to listen and speak.
			new Thread(outboundDataStream).start();
			new Thread(incomingDataStream).start();
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

		Iterator<DataStream> it = this.connectedClients.iterator();
		String composite = String.format("%s: %s", name, message);

		while (it.hasNext()) {
			DataStream client = it.next();
			client.message = composite;
		}
	}

	public static void recvChat(String input) {
		String name, message;
		String[] intermediate = input.split(",");

		name = Base64Coder.decodeString(intermediate[0]);
		message = Base64Coder.decodeString(intermediate[1]);

		ChatHandler.sendChat(name, message);
	}
}
