package me.barrytatum.BukkitRChat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatServer {

	public ServerSocket server;
	public int port;
	public ArrayList<DataStream> connectedClients = new ArrayList<DataStream>();

	ChatServer(int port) {
		try {
			this.server = new ServerSocket(port);
			this.start();
		} catch (IOException e) {
			BukkitRChat.logger.warning(String.format(
					"Unable to bind on port %d.", port));
		}
	}

	private void start() {
		while (true) {
			
			Socket connection = null;
			
			try {
				connection = server.accept();
			} catch (IOException e) {
				BukkitRChat.logger.warning("Unable to establish connection.");
			}

			DataStream client = new DataStream(connection);
			new Thread(client).start();
			
			this.connectedClients.add(client);
		}
	}

	public void sendMessage(String name, String message) {
		Iterator<DataStream> it = this.connectedClients.iterator();
		
		while ( it.hasNext() ) {
			DataStream client = it.next();
			client.sendMessge(name, message);
		}
	}
}
