package net.elicodes.vw.listeners;

import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;

public class VirtualWorldDeleteEvent extends WorldEvent implements Cancellable {

    private HandlerList handlers = new HandlerList();
    private boolean isCancellable;
    private long atraso;

    public VirtualWorldDeleteEvent(World world) {
        super(world);
    }

    public long getAtraso() {
        return atraso;
    }

    public void setAtraso(long atraso) {
        this.atraso = atraso;
    }

    @Override
    public boolean isCancelled() {
        return isCancellable;
    }

    @Override
    public void setCancelled(boolean isCancellable) {
        this.isCancellable = isCancellable;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
