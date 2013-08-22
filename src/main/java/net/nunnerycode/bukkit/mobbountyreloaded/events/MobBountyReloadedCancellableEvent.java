package net.nunnerycode.bukkit.mobbountyreloaded.events;

import org.bukkit.event.Cancellable;

public class MobBountyReloadedCancellableEvent extends MobBountyReloadedEvent implements Cancellable {

	private boolean cancelled;

	@Override
	public final boolean isCancelled() {
		return cancelled;
	}

	@Override
	public final void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
