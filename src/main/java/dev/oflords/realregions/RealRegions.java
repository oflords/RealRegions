package dev.oflords.realregions;

import dev.oflords.realregions.commands.RegionCommand;
import dev.oflords.realregions.region.RegionListener;
import org.bukkit.plugin.java.JavaPlugin;

public class RealRegions extends JavaPlugin {
    public static RealRegions realRegions;
    @Override
    public void onEnable() {
        this.getCommand("region").setExecutor(new RegionCommand());

        getServer().getPluginManager().registerEvents(new RegionListener(), this);

        // TODO: Load regions from SQL
    }

    public static RealRegions get() {
        return realRegions;
    }
}