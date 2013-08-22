package net.nunnerycode.bukkit.mobbountyreloaded.managers;

import java.util.logging.Level;
import net.milkbowl.vault.economy.Economy;
import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {

	private final MobBountyReloadedPlugin plugin;
	private Economy economy;

	public EconomyManager(MobBountyReloadedPlugin plugin) {
		this.plugin = plugin;
	}

	public MobBountyReloadedPlugin getPlugin() {
		return plugin;
	}

	public Economy getEconomy() {
		return economy;
	}

	public boolean setupEconomy() {
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			plugin.debug(Level.INFO, "No economy plugin found. Disabling the plugin.");
			return false;
		}
		economy = rsp.getProvider();
		return economy != null;
	}
}
