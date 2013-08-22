package net.nunnerycode.bukkit.mobbountyreloaded.settings;

import com.conventnunnery.libraries.config.ConventYamlConfiguration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;

public class PluginSettings {

	private MobBountyReloadedPlugin plugin;
	private String locale;
	private boolean useKillCache;
	private long killCacheTimeLimit;
	private boolean debugMode;
	private boolean allowCreativeEarning;
	private Map<String, String> multipliers;
	private Map<String, String> rewards;

	public PluginSettings(MobBountyReloadedPlugin plugin) {
		this.plugin = plugin;
		multipliers = new HashMap<String, String>();
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
		for (Map.Entry<String, String> entry : rewards.entrySet()) {
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
}
