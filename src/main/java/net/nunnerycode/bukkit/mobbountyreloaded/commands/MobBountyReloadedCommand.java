package net.nunnerycode.bukkit.mobbountyreloaded.commands;

import net.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import org.bukkit.command.CommandSender;
import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.CommandHandler;

public class MobBountyReloadedCommand {

	private final MobBountyReloadedPlugin plugin;

	public MobBountyReloadedCommand(MobBountyReloadedPlugin plugin) {
		this.plugin = plugin;
		new CommandHandler(this.plugin).registerCommands(this);
	}

	@Command(identifier = "mobbountyreward", description = "Used to change reward values for mobs.",
			permissions = "mbr.command.reward")
	public void rewardCommand(CommandSender sender, @Arg(name = "action", def = "set",
			description = "set | help") String action, @Arg(name = "mob", def = "") String type,
							  @Arg(name = "amount", def = "0.0") String amount, @Arg(name = "world",
			def = "Default") String world) {
		if (action.equalsIgnoreCase("help")) {
			getPlugin().sendMessage(sender, getPlugin().getLanguageManager().getMessage("MBCommandUsage",
					new String[][]{{"%command%", "mobbountyreward"}, {"%args%", "[set|help] [mob] [amount] " +
							"[world]"}}));
			getPlugin().sendMessage(sender, getPlugin().getLanguageManager().getMessage("MBRMobs", new String[][]{{
					"%mobs%", getPlugin().getSettings().getLoadedMobs().toString().replace("[", "").replace("]", "")}}));
		} else {
			getPlugin().getRewardManager().setRewardValue(world + "." + type, amount);
			if (world.equalsIgnoreCase("Default")) {
				getPlugin().sendMessage(sender, getPlugin().getLanguageManager().getMessage("MBRChange",
						new String[][]{{"%mobtype%", type}, {"%amount%", amount}}));
			} else {
				getPlugin().sendMessage(sender, getPlugin().getLanguageManager().getMessage("MBWRChange",
						new String[][]{{"%mobtype%", type}, {"%amount%", amount}}));
			}
		}
	}

	public MobBountyReloadedPlugin getPlugin() {
		return plugin;
	}

}
