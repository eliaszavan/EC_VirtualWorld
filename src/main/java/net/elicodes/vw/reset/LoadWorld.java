package net.elicodes.vw.reset;

import net.elicodes.vw.sdm.FakeIDataManager;
import net.minecraft.server.v1_8_R3.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.scoreboard.CraftScoreboard;
import org.bukkit.generator.ChunkGenerator;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class LoadWorld {

    public static MinecraftServer console = ((CraftServer) Bukkit.getServer()).getServer();
    public static CraftServer craftServer = ((CraftServer) Bukkit.getServer());

    private static final Map<String, World> getWorlds() {
        Field variavel = null;
        try {
            variavel = craftServer.getClass().getDeclaredField("worlds");
            variavel.setAccessible(true);
            return (Map<String, World>) variavel.get(craftServer);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void load(WorldCreator creator) {
        Validate.notNull(creator, "Creator may not be null");
        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        World world = Bukkit.getWorld(name);
        WorldType type = WorldType.getType(creator.type().getName());
        boolean generateStructures = creator.generateStructures();
        if (world == null) {
            if (generator == null) {
                generator = craftServer.getGenerator(name);
            }
            int dimension = 10 + console.worlds.size();
            boolean used = false;

            do {
                Iterator var11 = console.worlds.iterator();

                while (var11.hasNext()) {
                    WorldServer server = (WorldServer) var11.next();
                    used = server.dimension == dimension;
                    if (used) {
                        ++dimension;
                        break;
                    }
                }
            } while (used);
            boolean hardcore = false;
            WorldSettings worldSettings = new WorldSettings(creator.seed(), WorldSettings.EnumGamemode.getById(Bukkit.getDefaultGameMode().getValue()), generateStructures, hardcore, type);
            worldSettings.setGeneratorSettings(creator.generatorSettings());

            WorldData worldData = new WorldData(worldSettings, name);
            worldData.checkName(name);
            FakeIDataManager sdm = new FakeIDataManager();
            sdm.setWorldData(worldData);
            WorldServer internal = (WorldServer) (new WorldServer(console, sdm, worldData, dimension, console.methodProfiler, creator.environment(), generator)).b();
            internal.scoreboard = ((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle();
            internal.tracker = new EntityTracker(internal);
            internal.addIWorldAccess(new WorldManager(console, internal));
            internal.worldData.setDifficulty(EnumDifficulty.EASY);
            internal.setSpawnFlags(true, true);
            console.worlds.add(internal);
            if (generator != null) {
                internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
            }

            System.out.print("Preparing start region for level " + (console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");
            if (internal.getWorld().getKeepSpawnInMemory()) {
                System.out.println("if keep spawn in memory");
            }
        }
    }
}
