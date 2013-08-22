package net.nunnerycode.bukkit.mobbountyreloaded;

import java.util.logging.Level;
import net.nunnerycode.bukkit.libraries.module.ModulePlugin;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;

public class MobBountyReloadedPlugin extends ModulePlugin {

	private static MobBountyReloadedPlugin INSTANCE;
	private DebugPrinter debugPrinter;

	public MobBountyReloadedPlugin() {
		INSTANCE = this;
	}

	public static MobBountyReloadedPlugin getInstance() {
		return INSTANCE;
	}

	@Override
	public void onLoad() {
	 	debugPrinter = new DebugPrinter(getDataFolder().getPath(), "debug.log");
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

	}

	@Override
	public void onEnable() {
		enable();
		debug(Level.INFO, getName() + " v" + getDescription().getVersion() + " enabled");
	}

	private void enable() {

	}

}
