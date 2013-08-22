package net.nunnerycode.bukkit.mobbountyreloaded.managers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import net.nunnerycode.bukkit.mobbountyreloaded.utils.RandomRangeUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public final class RewardManager {

	private final MobBountyReloadedPlugin plugin;
	private final Map<String, double[]> rewardValue;

	public RewardManager(MobBountyReloadedPlugin plugin) {
		this.plugin = plugin;
		rewardValue = new HashMap<String, double[]>();
	}

	public void updateRewardValue() {
		rewardValue.clear();
		Map<String, String> rewards = getPlugin().getSettings().getRewards();
		Iterator<World> iterator = Bukkit.getWorlds().iterator();
		while (iterator.hasNext()) {
			World w = iterator.next();
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
				rewardValue.put(worldName + "." + typeName, new double[]{getDouble(s, 0.0D), getDouble(s, 0.0D)});
			}
		}
	}

	public MobBountyReloadedPlugin getPlugin() {
		return plugin;
	}

	private double[] handleRange(String string, String splitter, double fallback) {
		String[] splitString = string.split(splitter);
		if (splitString.length > 1) {
			return new double[]{getDouble(splitString[0], 0.0D),
					getDouble(splitString[1], 0.0D)};
		}
		return new double[]{fallback, fallback};
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
		if (getRewardValueMap().containsKey(worldName + "." + type)) {
			double[] values = getRewardValueMap().get(worldName + "." + type);
			return RandomRangeUtils.randomRangeDoubleInclusive(values[0], values[1]);
		}
		return 0.0D;
	}

	public Map<String, double[]> getRewardValueMap() {
		return Collections.unmodifiableMap(rewardValue);
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
}
