package net.nunnerycode.bukkit.mobbountyreloaded.managers;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class LanguageManager {

	private final MobBountyReloadedPlugin plugin;
	private final Map<String, String> messages;

	public LanguageManager(MobBountyReloadedPlugin plugin) {
		this.plugin = plugin;
		messages = new LinkedHashMap<String, String>();
	}

	public Map<String, String> getMessages() {
		return messages;
	}

	public void sendMessage(CommandSender reciever, String path) {
		String message = getMessage(path);
		if (message == null) {
			return;
		}
		reciever.sendMessage(message);
	}

	public String getMessage(String path) {
		String message = messages.get(getPlugin().getSettings().getLocale() + "." + path);
		if (message == null) {
			return null;
		}
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public MobBountyReloadedPlugin getPlugin() {
		return plugin;
	}

	public void sendMessage(CommandSender reciever, String path,
							String[][] arguments) {
		String message = getMessage(path, arguments);
		if (message == null) {
			return;
		}
		reciever.sendMessage(message);
	}

	public String getMessage(String path, String[][] arguments) {
		String message = getMessage(path);
		if (message == null) {
			return null;
		}
		for (String[] argument : arguments) {
			message = message.replaceAll(argument[0], argument[1]);
		}
		return message;
	}

	public List<String> getStringList(String path) {
		return Arrays.asList(getMessage(path).split("^"));
	}

	public List<String> getStringList(String path, String[][] arguments) {
		return Arrays.asList(getMessage(path).split("^"));
	}
}
