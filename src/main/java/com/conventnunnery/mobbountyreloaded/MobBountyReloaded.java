/*
 * Copyright (c) 2013. ToppleTheNun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.conventnunnery.mobbountyreloaded;

import com.conventnunnery.mobbountyreloaded.listeners.MobBountyReloadedEntityListener;
import com.conventnunnery.mobbountyreloaded.managers.MobBountyCommand;
import com.conventnunnery.mobbountyreloaded.managers.MobBountyConfigs;
import com.conventnunnery.mobbountyreloaded.managers.MobBountyEconomy;
import com.conventnunnery.mobbountyreloaded.managers.MobBountyLanguage;
import com.conventnunnery.mobbountyreloaded.managers.MobBountyLocale;
import com.conventnunnery.mobbountyreloaded.managers.MobBountyPermission;
import com.conventnunnery.mobbountyreloaded.managers.MobBountySettings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class MobBountyReloaded extends JavaPlugin {

	private static MobBountyReloaded instance;
	private MobBountyAPI api;
	private MobBountyConfigs configManager;
	private MobBountySettings settingsManager;
	private MobBountyEconomy econManager;
	private MobBountyLocale localeManager;
	private MobBountyCommand commandManager;
	private MobBountyPermission permissionManager;
	private MobBountyLanguage languageManager;

	public MobBountyReloaded() {
		instance = this;
	}

	public MobBountyCommand getCommandManager() {
		return commandManager;
	}

	private void setCommandManager(final MobBountyCommand commandManager) {
		this.commandManager = commandManager;
	}

	public MobBountyConfigs getConfigManager() {
		return configManager;
	}

	private void setConfigManager(final MobBountyConfigs configManager) {
		this.configManager = configManager;
	}

	public MobBountyEconomy getEconManager() {
		return econManager;
	}

	private void setEconManager(final MobBountyEconomy econManager) {
		this.econManager = econManager;
	}

	public MobBountyLocale getLocaleManager() {
		return localeManager;
	}

	private void setLocaleManager(final MobBountyLocale localeManager) {
		this.localeManager = localeManager;
	}

	public MobBountyPermission getPermissionManager() {
		return permissionManager;
	}

	private void setPermissionManager(
			final MobBountyPermission permissionManager) {
		this.permissionManager = permissionManager;
	}

	public MobBountySettings getSettingsManager() {
		return settingsManager;
	}

	private void setSettingsManager(MobBountySettings settingsManager) {
		this.settingsManager = settingsManager;
	}

	@Override
	public void onDisable() {
		getAPI().log("Disabled.");
		getAPI().logToFile("Plugin Disabled");
	}

	@Override
	public void onEnable() {
		setAPI(new MobBountyAPI(getInstance()));
		setPermissionManager(new MobBountyPermission(getInstance()));
		setConfigManager(new MobBountyConfigs(getInstance()));
		setLocaleManager(new MobBountyLocale(getInstance()));
		setSettingsManager(new MobBountySettings(getInstance()));
		setEconManager(new MobBountyEconomy(getInstance()));
		setLanguageManager(new MobBountyLanguage(getInstance()));
		new MobBountyReloadedEntityListener(getInstance());
		setCommandManager(new MobBountyCommand(getInstance()));
		getServer().getMessenger().registerOutgoingPluginChannel(this,
				"SimpleNotice");
		getAPI().log("Enabled.");
		getAPI().logToFile("Plugin Enabled");
		List<String> mobs = new ArrayList<String>();
		for (EntityType et : EntityType.values()) {
			mobs.add(et.name());
		}
		getAPI().logToFile("EntityTypes found: " + mobs.toString().replace("[", "").replace("]", ""));
		startStatistics();
	}

	private void startStatistics() {
		try {
			new Metrics(this).start();
		} catch (IOException ignored) {
		}
	}

	public static MobBountyReloaded getInstance() {
		return instance;
	}

	public MobBountyAPI getAPI() {
		return api;
	}

	private void setAPI(final MobBountyAPI api) {
		this.api = api;
	}

	public MobBountyLanguage getLanguageManager() {
		return languageManager;
	}

	private void setLanguageManager(MobBountyLanguage languageManager) {
		this.languageManager = languageManager;
	}
}