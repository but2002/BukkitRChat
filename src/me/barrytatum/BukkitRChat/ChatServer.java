package me.barrytatum.BukkitRChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServer {

	public ServerSocket server;
	public int port;
	public ArrayList<DataStream> connectedClients;

	/**
	 * Constructor to create an instance of a chat server, allowing clients to
	 * connect.
	 * 
	 * @param port - The port to bind.
	 */

	ChatServer(int port) {
		
		try {
			this.server = new ServerSocket(port);
			this.start();
			
		} catch (IOException e) {
			BukkitRChat.logger.warning(String.format(
					"Unable to bind on port %d.", port));
		}
	}

	/**
	 * Accept connections to the server.
	 */

	private void start() {
		
		while (true) {
			Socket connection = null;

			try {
				connection = server.accept();
				
			} catch (IOException e) {
				BukkitRChat.logger.warning("Unable to establish connection.");
				return;
			}

			// Create a client instance.
			DataStream client = new DataStream(connection);

			// Add the client to the pool of clients.
			this.connectedClients = new ArrayList<DataStream>();
			this.connectedClients.add(client);

			// Start the client's thread, allowing them to listen and speak.
			new Thread(client).start();
		}
	}

	/**
	 * Send a message to connected clients.
	 * 
	 * @param name - The name of the speaking player.
	 * @param message - The spoken message.
	 */

	public void sendChat(String name, String message) {
		Iterator<DataStream> it = this.connectedClients.iterator();
		String composite = String.format("%s: %s", name, message);

		while (it.hasNext()) {
			DataStream client = it.next();
			client.message = composite;
		}
	}
}
