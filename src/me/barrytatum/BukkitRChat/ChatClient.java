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

	ChatClient(Socket connection) throws IOException {
		this.connection = connection;
		this.out = new PrintWriter(connection.getOutputStream());
		this.in = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
	}

	public void sendMessage(String message) {
		this.out.println(message);
	}

	public void run() {

		String encodedString;

		while (true) {

			try {
				if ((encodedString = this.in.readLine()) != null) {
					String name, message;
					String[] container;

					container = Base64Coder.decodeString(encodedString).split(",");
					name = container[0];
					message = container[1];

					ChatHandler.sendChat(name, message);
				}
				
			} catch (IOException e) {
				return;
			}

		}

	}

}
