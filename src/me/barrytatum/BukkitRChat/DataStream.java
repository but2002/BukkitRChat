package me.barrytatum.BukkitRChat;

import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

public class DataStream implements Runnable {

	private Socket connection;
	private PrintWriter out;
	
	public String message;

	/**
	 * Create a data stream to a client that allows data to be sent.
	 * 
	 * @param connection - An established socket connection.
	 */
	
	DataStream(Socket connection) {
		this.connection = connection;

		// Get the output stream of the client.
		try {
			this.out = new PrintWriter(this.connection.getOutputStream(), true);

		} catch (IOException e) {
			BukkitRChat.logger.warning("Unable to open stream.");
			return;
		}
	}

	/**
	 * Send messages to a client.
	 */
	
	public void run() {

		for (;;) {

			// Check that the client is still connected. 
			if ( this.connection.isClosed() ) break;
			
			// Check for messages.
			if (this.message != null) {
				this.out.println(this.message);
				this.message = null;
			}
		}
	}
}