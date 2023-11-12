package dev.oflords.realregions;

import dev.oflords.realregions.commands.RegionCommand;
import dev.oflords.realregions.region.RegionListener;
import dev.oflords.realregions.util.SQL;
import dev.oflords.realregions.util.menu.MenuListener;
import org.bukkit.plugin.java.JavaPlugin;

public class RealRegions extends JavaPlugin {
    public static RealRegions realRegions;
    public SQL sql;
    @Override
    public void onEnable() {
        this.getCommand("region").setExecutor(new RegionCommand());

        this.sql = new SQL(getConfig());
        this.sql.load();

        getServer().getPluginManager().registerEvents(new RegionListener(), this);
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        this.sql.save();
    }

    public static RealRegions get() {
        return realRegions;
    }
}