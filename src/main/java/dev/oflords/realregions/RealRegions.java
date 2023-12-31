package dev.oflords.realregions;

import dev.oflords.realregions.commands.RegionCommand;
import dev.oflords.realregions.listeners.RegionPlayerListener;
import dev.oflords.realregions.util.SQL;
import dev.oflords.realregions.util.menu.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public class RealRegions extends JavaPlugin {
    private SQL sql;
    @Override
    public void onEnable() {
        saveConfig();

        this.getCommand("region").setExecutor(new RegionCommand());

        this.sql = new SQL(getConfig(), this);
        this.sql.load();

        getServer().getPluginManager().registerEvents(new RegionPlayerListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        this.sql.save();
    }
}