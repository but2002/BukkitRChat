package me.barrytatum.BukkitRChat;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {
	public BukkitRChat plugin;

	public ChatHandler(BukkitRChat instance) {
		this.plugin = instance;
	}

	/**
	 * @param name
	 *            Nickname of messenger
	 * @param message
	 *            Their chat message
	 */
	public static void sendChat(String name, String message) {
		Bukkit.getServer().broadcastMessage(name + ": " + message);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		BukkitRChat.chatServer.sendChat(event.getPlayer().getDisplayName(),
				event.getMessage());
	}
}
