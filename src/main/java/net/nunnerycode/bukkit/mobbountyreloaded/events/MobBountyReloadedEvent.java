package net.nunnerycode.bukkit.mobbountyreloaded.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobBountyReloadedEvent extends Event {

	private static final HandlerList HANDLER_LIST = new HandlerList();

	public final static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

	@Override
	public final HandlerList getHandlers() {
		return HANDLER_LIST;
	}

}
