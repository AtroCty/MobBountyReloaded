package net.nunnerycode.bukkit.mobbountyreloaded;

import com.conventnunnery.libraries.config.ConventConfigurationManager;
import com.conventnunnery.libraries.config.ConventYamlConfiguration;
import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;
import net.nunnerycode.bukkit.libraries.module.Module;
import net.nunnerycode.bukkit.libraries.module.ModuleLoader;
import net.nunnerycode.bukkit.libraries.module.ModulePlugin;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;

public class MobBountyReloadedPlugin extends ModulePlugin {

	private static MobBountyReloadedPlugin INSTANCE;
	private DebugPrinter debugPrinter;
	private ModuleLoader moduleLoader;
	private ConventConfigurationManager conventConfigurationManager;
	private ConventYamlConfiguration generalYAML;
	private ConventYamlConfiguration killstreakYAML;
	private ConventYamlConfiguration localeYAML;
	private ConventYamlConfiguration multiplierYAML;
	private ConventYamlConfiguration rewardYAML;

	public MobBountyReloadedPlugin() {
		INSTANCE = this;
	}

	public static MobBountyReloadedPlugin getInstance() {
		return INSTANCE;
	}

	public ConventYamlConfiguration getGeneralYAML() {
		return generalYAML;
	}

	public ConventYamlConfiguration getKillstreakYAML() {
		return killstreakYAML;
	}

	public ConventYamlConfiguration getLocaleYAML() {
		return localeYAML;
	}

	public ConventYamlConfiguration getMultiplierYAML() {
		return multiplierYAML;
	}

	public ConventYamlConfiguration getRewardYAML() {
		return rewardYAML;
	}

	@Override
	public void onLoad() {
		debugPrinter = new DebugPrinter(getDataFolder().getPath(), "debug.log");
		moduleLoader = new ModuleLoader(this);
		conventConfigurationManager = new ConventConfigurationManager(this);
	}

	@Override
	public void onDisable() {
		disable();
		debug(Level.INFO, getName() + " v" + getDescription().getVersion() + " disabled", "", "", "");
	}

	@Override
	public void onEnable() {
		enable();
		debug(Level.INFO, getName() + " v" + getDescription().getVersion() + " enabled");
	}

	public void reload() {
		disable();

		enable();
	}

	private void enable() {

		conventConfigurationManager.unpackConfigurationFiles("general.yml", "killstreak.yml", "locale.yml",
				"multiplier.yml", "reward.yml");


		generalYAML = new ConventYamlConfiguration(new File(getDataFolder().getPath(), "general.yml"));
		killstreakYAML = new ConventYamlConfiguration(new File(getDataFolder().getPath(), "killstreak.yml"));
		localeYAML = new ConventYamlConfiguration(new File(getDataFolder().getPath(), "locale.yml"));
		multiplierYAML = new ConventYamlConfiguration(new File(getDataFolder().getPath(), "multiplier.yml"));
		rewardYAML = new ConventYamlConfiguration(new File(getDataFolder().getPath(), "reward.yml"));

		Iterator<Module> iterator = getModules().iterator();
		while (iterator.hasNext()) {
			Module m = iterator.next();
			m.disable();
			debug(Level.INFO, m.getName() + " disabled");
		}
		getModules().clear();
		moduleLoader.loadModules(new File(getDataFolder().getPath(), "/modules/"));
		iterator = getModules().iterator();
		while (iterator.hasNext()) {
			Module m = iterator.next();
			debug(Level.INFO, m.getName() + " enabled");
		}
	}

	private void disable() {
		Iterator<Module> iterator = getModules().iterator();
		while (iterator.hasNext()) {
			Module m = iterator.next();
			m.disable();
			debug(Level.INFO, m.getName() + " disabled");
		}
		getModules().clear();
	}

	public void debug(Level level, String... messages) {
		debugPrinter.debug(level, messages);
	}

}
