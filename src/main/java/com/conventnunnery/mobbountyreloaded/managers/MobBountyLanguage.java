package com.conventnunnery.mobbountyreloaded.managers;

import com.conventnunnery.mobbountyreloaded.MobBountyReloaded;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MobBountyLanguage {
	private MobBountyReloaded plugin;
	private final Map<String, String> messages;

	public MobBountyLanguage(MobBountyReloaded plugin) {
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
		String message = messages.get(path);
		if (message == null) {
			return null;
		}
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public MobBountyReloaded getPlugin() {
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
		String message = messages.get(path);
		if (message == null) {
			return null;
		}
		message = ChatColor.translateAlternateColorCodes('&', message);
		for (String[] argument : arguments) {
			message = message.replaceAll(argument[0], argument[1]);
		}
		return message;
	}

	public List<String> getStringList(String path) {
		List<String> message = Arrays.asList(messages.get(path).split("^"));
		List<String> strings = new ArrayList<String>();
		for (String s : message) {
			strings.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return strings;
	}

	public List<String> getStringList(String path, String[][] arguments) {
		List<String> message = Arrays.asList(messages.get(path).split("^"));
		List<String> strings = new ArrayList<String>();
		for (String s : message) {
			for (String[] argument : arguments) {
				strings.add(ChatColor.translateAlternateColorCodes('&', s.replace(argument[0], argument[1])));
			}
		}
		return strings;
	}
}
