package me.barrytatum.BukkitRChat;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.BufferedReader;

public class InputDataStream implements Runnable {

	Socket connection;
	BufferedReader in;

	InputDataStream(Socket connection) {
		this.connection = connection;

		try {
			this.in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

		} catch (IOException e) {
			BukkitRChat.logger.warning("Unable to open stream.");
			return;
		}
	}

	public void run() {
		
		for (;;) {
			
			String message;

			try {
				while ((message = in.readLine()) != null) {
					ChatServer.recvChat(message);
				}
			} catch (IOException e) {
				BukkitRChat.logger.warning("Unable to read stream.");
			}
		}
	}
}
