package net.elicodes.vw;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static Main getPlugin() {
        return Main.getPlugin(Main.class);
    }

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&2[ResetAPI] &aAPI habilitada com sucesso. &f(&7V: &e" + getDescription().getVersion() + "&f)."));
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[ResetAPI] &cAPI desabilitada com sucesso. &f(&7V: &e" + getDescription().getVersion() + "&f)."));
    }
}
