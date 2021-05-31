package net.elicodes.vw.listeners;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.world.WorldEvent;

public class VirtualWorldCreateEvent extends WorldEvent {

    private HandlerList handlers = new HandlerList();
    private boolean withSchematic;
    private long atraso;

    public VirtualWorldCreateEvent(World world) {
        super(world);
    }

    public long getAtraso() {
        return atraso;
    }

    public void setAtraso(long atraso) {
        this.atraso = atraso;
    }

    public boolean isWithSchematic() {
        return withSchematic;
    }

    public void setWithSchematic(boolean withSchematic) {
        this.withSchematic = withSchematic;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
