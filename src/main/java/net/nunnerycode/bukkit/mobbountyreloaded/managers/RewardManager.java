package net.nunnerycode.bukkit.mobbountyreloaded.managers;

import java.util.Random;
import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;

public class RewardManager {

	private final MobBountyReloadedPlugin plugin;

	public RewardManager(MobBountyReloadedPlugin plugin) {
		this.plugin = plugin;
	}

	public double getValue(String worldName, String type) {
		String s;
		if (getPlugin().getSettings().getRewards().containsKey(worldName + "." + type)) {
			s = getPlugin().getSettings().getRewards().get(worldName + "." + type);
		} else if (getPlugin().getSettings().getRewards().containsKey("Default." + type)) {
			s = getPlugin().getSettings().getRewards().get("Default." + type);
		} else {
			s = "0.0";
		}
		if (s.contains(":")) {
			return handleRange(s, ":", 0);
		}
		return getInt(s, 0);
	}

	public MobBountyReloadedPlugin getPlugin() {
		return plugin;
	}

	private int handleRange(String string, String splitter, int fallback) {
		Random rand = new Random();
		int result;
		int range;
		int loc;
		String[] splitString = string.split(splitter);
		if (splitString.length > 1) {
			if (getDouble(splitString[0], 0) > getDouble(splitString[1], 0)) {
				range = (int) ((getDouble(splitString[0], 0) * 100) - (getDouble(
						splitString[1], 0) * 100));
				loc = (int) (getDouble(splitString[1], 0) * 100);
			} else {
				range = (int) ((getDouble(splitString[1], 0) * 100) - (getDouble(
						splitString[0], 0) * 100));
				loc = (int) (getDouble(splitString[0], 0) * 100);
			}
			result = (loc + rand.nextInt(range + 1)) / 100;
			return result;
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

	private int getInt(String string, int fallBack) {
		int i;
		try {
			i = (int) Math.round(getDouble(string, fallBack));
		} catch (Exception e) {
			i = fallBack;
		}
		return i;
	}

	private int handleRange(int i1, int i2) {
		Random rand = new Random();
		int result;
		int range;
		int loc;
		if ((double) i1 > (double) i2) {
			range = (int) (((double) i1 * 100) - ((double) i2 * 100));
			loc = (int) ((double) i2 * 100);
		} else {
			range = (int) (((double) i2 * 100) - ((double) i1 * 100));
			loc = (int) ((double) i1 * 100);
		}
		result = (loc + rand.nextInt(range + 1)) / 100;
		return result;
	}
}
