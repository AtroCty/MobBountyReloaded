package net.nunnerycode.bukkit.mobbountyreloaded.settings;

import com.conventnunnery.libraries.config.ConventYamlConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import org.bukkit.entity.EntityType;

public class PluginSettings {

	private MobBountyReloadedPlugin plugin;
	private String locale;
	private boolean useKillCache;
	private long killCacheTimeLimit;
	private boolean debugMode;
	private boolean allowCreativeEarning;
	private Map<String, String> multipliers;
	private Map<String, String> rewards;
	private List<String> loadedMobs;

	public PluginSettings(MobBountyReloadedPlugin plugin) {
		this.plugin = plugin;
		multipliers = new HashMap<String, String>();
		rewards = new HashMap<String, String>();
		loadedMobs = getLoadedMobsFromServer();
	}

	private List<String> getLoadedMobsFromServer() {
		List<String> mobs = new ArrayList<String>();
		for (EntityType creature : EntityType.values()) {
			switch (creature) {
				case ARROW:
				case BOAT:
				case COMPLEX_PART:
				case DROPPED_ITEM:
				case EGG:
				case ENDER_CRYSTAL:
				case ENDER_PEARL:
				case ENDER_SIGNAL:
				case EXPERIENCE_ORB:
				case FALLING_BLOCK:
				case FIREBALL:
				case FIREWORK:
				case FISHING_HOOK:
				case ITEM_FRAME:
				case LIGHTNING:
				case MINECART:
				case MINECART_FURNACE:
				case MINECART_HOPPER:
				case MINECART_CHEST:
				case MINECART_MOB_SPAWNER:
				case MINECART_TNT:
				case PAINTING:
				case PRIMED_TNT:
				case SMALL_FIREBALL:
				case SNOWBALL:
				case SPLASH_POTION:
				case THROWN_EXP_BOTTLE:
				case WEATHER:
				case WITHER_SKULL:
					continue;
				default:
					if (creature.getName() != null && !creature.getName().equalsIgnoreCase("") &&
							!creature.getName().equalsIgnoreCase("null")) {
						mobs.add(creature.getName());
					}
			}
		}
		return mobs;
	}

	public void load() {
		ConventYamlConfiguration c = getPlugin().getGeneralYAML();
		c.load();
		locale = c.getString("locale", "en");
		useKillCache = c.getBoolean("killCache.use", false);
		killCacheTimeLimit = c.getLong("killCache.timeLimit", (long) 30000);
		debugMode = c.getBoolean("debugMode", false);
		allowCreativeEarning = c.getBoolean("creativeEarning.allow", false);
		c = getPlugin().getLocaleYAML();
		c.load();
		Iterator<String> iterator = c.getKeys(true).iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (c.isConfigurationSection(key)) {
				continue;
			}
			getPlugin().getLanguageManager().getMessages().put(key, c.getString(key, key));
		}
		multipliers.clear();
		c = getPlugin().getMultiplierYAML();
		c.load();
		Iterator<String> iterator1 = c.getKeys(true).iterator();
		while (iterator1.hasNext()) {
			String key = iterator1.next();
			if (c.isConfigurationSection(key)) {
				continue;
			}
			multipliers.put(key, c.getString(key, key));
		}
		rewards.clear();
		c = getPlugin().getRewardYAML();
		c.load();
		Iterator<String> iterator2 = c.getKeys(true).iterator();
		while (iterator2.hasNext()) {
			String key = iterator2.next();
			if (c.isConfigurationSection(key)) {
				continue;
			}
			rewards.put(key, c.getString(key, key));
		}
		if (rewards.isEmpty()) {
			for (String mob : getLoadedMobs()) {
				rewards.put(mob, "0.0");
			}
		}
	}

	public MobBountyReloadedPlugin getPlugin() {
		return plugin;
	}

	public void save() {
		ConventYamlConfiguration c = getPlugin().getGeneralYAML();
		c.set("locale", locale);
		c.set("killCache.use", useKillCache);
		c.set("killCache.timeLimit", killCacheTimeLimit);
		c.set("debugMode", debugMode);
		c.set("creativeEarning.allow", allowCreativeEarning);
		c.save();
		c = getPlugin().getRewardYAML();
		Iterator<Map.Entry<String, String>> iterator = rewards.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			c.set(entry.getKey(), entry.getValue());
		}
		c.save();
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public boolean isUseKillCache() {
		return useKillCache;
	}

	public void setUseKillCache(boolean useKillCache) {
		this.useKillCache = useKillCache;
	}

	public long getKillCacheTimeLimit() {
		return killCacheTimeLimit;
	}

	public void setKillCacheTimeLimit(long killCacheTimeLimit) {
		this.killCacheTimeLimit = killCacheTimeLimit;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public boolean isAllowCreativeEarning() {
		return allowCreativeEarning;
	}

	public void setAllowCreativeEarning(boolean allowCreativeEarning) {
		this.allowCreativeEarning = allowCreativeEarning;
	}

	public Map<String, String> getMultipliers() {
		return Collections.unmodifiableMap(multipliers);
	}

	public Map<String, String> getRewards() {
		return Collections.unmodifiableMap(rewards);
	}

	public List<String> getLoadedMobs() {
		return Collections.unmodifiableList(loadedMobs);
	}
}
