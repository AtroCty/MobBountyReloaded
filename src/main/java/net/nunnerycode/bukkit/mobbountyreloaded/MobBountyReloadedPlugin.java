package net.nunnerycode.bukkit.mobbountyreloaded;

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

	public MobBountyReloadedPlugin() {
		INSTANCE = this;
	}

	public static MobBountyReloadedPlugin getInstance() {
		return INSTANCE;
	}

	@Override
	public void onLoad() {
	 	debugPrinter = new DebugPrinter(getDataFolder().getPath(), "debug.log");
		moduleLoader = new ModuleLoader(this);
	}

	public void debug(Level level, String... messages) {
		debugPrinter.debug(level, messages);
	}

	public void reload() {
		disable();

		enable();
	}

	@Override
	public void onDisable() {
		disable();
		debug(Level.INFO, getName() + " v" + getDescription().getVersion() + " disabled", "", "", "");
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

	@Override
	public void onEnable() {
		enable();
		debug(Level.INFO, getName() + " v" + getDescription().getVersion() + " enabled");
	}

	private void enable() {
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

}
