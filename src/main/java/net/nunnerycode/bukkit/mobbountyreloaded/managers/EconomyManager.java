package net.nunnerycode.bukkit.mobbountyreloaded.managers;

import java.util.logging.Level;
import net.milkbowl.vault.economy.Economy;
import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class EconomyManager {

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

	public boolean setAccount(String accountName, double amount) {
		if (!economy.hasAccount(accountName)) {
			economy.createPlayerAccount(accountName);
		}
		return transaction(accountName, -economy.getBalance(accountName)) && transaction(accountName, amount);
	}

	public boolean transaction(String accountName, double amount) {
		if (amount > 0.0D) {
			return payAccount(accountName, amount);
		} else if (amount < 0.0D) {
			return fineAccount(accountName, amount);
		}
		return false;
	}

	public boolean fineAccount(String accountName, double amount) {
		double d = Math.abs(amount);
		if (economy.hasAccount(accountName)) {
			return economy.withdrawPlayer(accountName, d).transactionSuccess();
		}
		economy.createPlayerAccount(accountName);
		return economy.withdrawPlayer(accountName, d).transactionSuccess();
	}

	public boolean payAccount(String accountName, double amount) {
		double d = Math.abs(amount);
		if (economy.hasAccount(accountName)) {
			return economy.depositPlayer(accountName, d).transactionSuccess();
		}
		economy.createPlayerAccount(accountName);
		return economy.depositPlayer(accountName, d).transactionSuccess();
	}
}
