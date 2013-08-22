package net.nunnerycode.bukkit.mobbountyreloaded.managers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import net.nunnerycode.bukkit.mobbountyreloaded.utils.RandomRangeUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public final class RewardManager {

	private final MobBountyReloadedPlugin plugin;
	private final Map<String, Double> rewardValue;

	public RewardManager(MobBountyReloadedPlugin plugin) {
		this.plugin = plugin;
		rewardValue = new HashMap<String, Double>();
	}

	public void updateRewardValue() {
		Map<String, String> rewards = getPlugin().getSettings().getRewards();
		for (World w : Bukkit.getWorlds()) {
			String worldName = w.getName();
			String typeName;
			String s;
			for (EntityType entityType : EntityType.values()) {
				typeName = entityType.name();
				if (rewards.containsKey(worldName + "." + typeName)) {
					s = rewards.get(worldName + "." + typeName);
				} else if (rewards.containsKey("Default." + typeName)) {
					s = rewards.get("Default." + typeName);
				} else {
					s = "0.0";
				}
				if (s.contains(":")) {
					rewardValue.put(worldName + "." + typeName, handleRange(s, ":", 0.0D));
				}
				rewardValue.put(worldName + "." + typeName, getDouble(s, 0.0D));
			}
		}
	}

	public MobBountyReloadedPlugin getPlugin() {
		return plugin;
	}

	private double handleRange(String string, String splitter, double fallback) {
		Random rand = new Random();
		String[] splitString = string.split(splitter);
		if (splitString.length > 1) {
			return RandomRangeUtils.randomRangeDoubleInclusive(getDouble(splitString[0], 0.0D),
					getDouble(splitString[1], 0.0D));
		}
		return fallback;
	}

	private double getDouble(String string, double fallBack) {
		double d;
		try {
			d = Double.parseDouble(string);
		} catch (Exception e) {
			d = fallBack;
		}
		return d;
	}

	public double getValue(String worldName, String type) {
		if (getRewardValue().containsKey(worldName + "." + type)) {
			return getRewardValue().get(worldName + "." + type);
		}
		return 0.0D;
	}

	private int getInt(String string, int fallBack) {
		int i;
		try {
			i = (int) Math.round(getDouble(string, fallBack));
		} catch (Exception e) {
			i = fallBack;
		}
		return i;
	}

	public Map<String, Double> getRewardValue() {
		return Collections.unmodifiableMap(rewardValue);
	}
}
