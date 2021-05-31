package net.elicodes.vw.developers;

import net.elicodes.vw.listeners.VirtualWorldCreateEvent;
import net.elicodes.vw.listeners.VirtualWorldDeleteEvent;
import net.elicodes.vw.reset.EmptyWorldGenerator;
import net.elicodes.vw.reset.LoadWorld;
import net.elicodes.vw.core.SchematicUtil;
import net.minecraft.server.v1_8_R3.ExceptionWorldConflict;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.io.File;
import java.util.logging.Level;

public class WorldAPI {

    private static MinecraftServer console = LoadWorld.console;
    private static CraftServer server = LoadWorld.craftServer;

    public static World createVirtualWorld(String name) {
        if (Bukkit.getWorld(name) != null) return null;
        long previous = System.currentTimeMillis();
        LoadWorld.load(new WorldCreator(name).generator(new EmptyWorldGenerator()));
        World world = Bukkit.getWorld(name);

        world.setSpawnLocation(0, 100, 0);
        long later = System.currentTimeMillis();
        long ms = previous - later;

        WorldServer handle = ((CraftWorld) world).getHandle();
        VirtualWorldCreateEvent e = new VirtualWorldCreateEvent(handle.getWorld());
        e.setWithSchematic(false);
        e.setAtraso(ms);
        Bukkit.getPluginManager().callEvent(e);
        return world;
    }

    public static World createVirtualWorldWithSchematic(String name, File schematicFile) {
        if (Bukkit.getWorld(name) != null) return null;
        long previous = System.currentTimeMillis();
        LoadWorld.load(new WorldCreator(name).generator(new EmptyWorldGenerator()));
        World world = Bukkit.getWorld(name);

        world.setSpawnLocation(0, 100, 0);
        long later = System.currentTimeMillis();
        long ms = previous - later;
        SchematicUtil.loadSchematic(schematicFile, world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY(), world.getSpawnLocation().getZ());

        WorldServer handle = ((CraftWorld) world).getHandle();
        VirtualWorldCreateEvent e = new VirtualWorldCreateEvent(handle.getWorld());
        e.setWithSchematic(true);
        e.setAtraso(ms);
        Bukkit.getPluginManager().callEvent(e);

        return world;
    }

    public static boolean deleteVirtualWorld(String name) {
        World world = Bukkit.getWorld(name);
        if (world == null) {
            return false;
        } else {
            WorldServer handle = ((CraftWorld) world).getHandle();
            if (!console.worlds.contains(handle)) {
                return false;
            } else if (handle.dimension <= 1) {
                return false;
            } else if (handle.players.size() > 0) {
                return false;
            } else {
                VirtualWorldDeleteEvent e = new VirtualWorldDeleteEvent(handle.getWorld());
                Bukkit.getPluginManager().callEvent(e);
                if (e.isCancelled()) {
                    return false;
                } else {
                    try {
                        long previous = System.currentTimeMillis();
                        handle.save(true, null);
                        handle.saveLevel();
                        long later = System.currentTimeMillis();
                        long ms = previous - later;
                        e.setAtraso(ms);
                    } catch (ExceptionWorldConflict var12) {
                        server.getLogger().log(Level.SEVERE, null, var12);
                    }
                }
            }
        }
        return false;
    }

    public static boolean deleteVirtualWorld(World world) {
        if (world == null) {
            return false;
        } else {
            WorldServer handle = ((CraftWorld) world).getHandle();
            if (!console.worlds.contains(handle)) {
                return false;
            } else if (handle.dimension <= 1) {
                return false;
            } else if (handle.players.size() > 0) {
                return false;
            } else {
                VirtualWorldDeleteEvent e = new VirtualWorldDeleteEvent(handle.getWorld());
                Bukkit.getPluginManager().callEvent(e);
                if (e.isCancelled()) {
                    return false;
                } else {
                    try {
                        long previous = System.currentTimeMillis();
                        handle.save(true, null);
                        handle.saveLevel();
                        long later = System.currentTimeMillis();
                        long ms = previous - later;
                        e.setAtraso(ms);
                    } catch (ExceptionWorldConflict var12) {
                        server.getLogger().log(Level.SEVERE, null, var12);
                    }
                }
            }
        }
        return false;
    }
}
