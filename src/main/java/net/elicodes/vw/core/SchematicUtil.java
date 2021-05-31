package net.elicodes.vw.core;

import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class SchematicUtil {

    public static void loadSchematic(File file, World world, double x, double y, double z) {
        try {
            com.sk89q.worldedit.Vector origin = new com.sk89q.worldedit.Vector(x, y, z);
            com.sk89q.worldedit.EditSession es = new com.sk89q.worldedit.EditSession(new com.sk89q.worldedit.bukkit.BukkitWorld(world), 999999999);
            com.sk89q.worldedit.CuboidClipboard cc = com.sk89q.worldedit.CuboidClipboard.loadSchematic(file);
            cc.paste(es, origin, false);
        }catch (com.sk89q.worldedit.world.DataException | IOException | com.sk89q.worldedit.MaxChangedBlocksException e) {
            e.printStackTrace();
        }
    }
}