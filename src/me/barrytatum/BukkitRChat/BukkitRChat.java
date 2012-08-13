package me.barrytatum.BukkitRChat;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitRChat extends JavaPlugin {

	public static Logger logger = Logger.getLogger("Minecraft");
	int port;
	public final ChatHandler ch = new ChatHandler(this);

	public void onEnable() {
		File file = new File(getDataFolder() + File.separator + "config.yml");
		if (!file.exists()) {
			logger.info("Generating config.yml");

			this.getConfig().addDefault("port", 5956);
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
		}
		this.port = this.getConfig().getInt("port");

		this.getServer().getPluginManager().registerEvents(this.ch, this);
		logger.info("Hello from BukkitRChat");
	}
}
