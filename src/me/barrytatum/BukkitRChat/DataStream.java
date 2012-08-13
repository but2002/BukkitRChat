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

	DataStream(Socket connection) {
		this.connection = connection;
		try {
			this.out = new PrintWriter(connection.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			this.in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			this.out = new PrintWriter(connection.getOutputStream());

			while (true) {
				String message;

				while ((message = this.in.readLine()) != null) {
					this.out.println(message);
				}
			}
		} catch (IOException e) {
			BukkitRChat.logger.warning("Unable to open stream.");
			return;
		}
	}
	
	public void sendMessge(String name, String message) {
		out.println(String.format("%s: %s\n", name, message));
	}
}