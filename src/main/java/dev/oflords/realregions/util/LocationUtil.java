package dev.oflords.realregions.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class LocationUtil {

    public static List<String> serialize(List<Location> source) {
        List<String> spawns = new ArrayList<>();

        for (Location loc : source) {
            spawns.add(serialize(loc));
        }

        return spawns;
    }

    public static String serialize(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" + location.getZ() +
                ":" + location.getYaw() + ":" + location.getPitch();
    }

    public static List<Location> deserialize(List<String> source) {
        List<Location> spawns = new ArrayList<>();

        for (String loc : source) {
            spawns.add(deserialize(loc));
        }

        return spawns;
    }

    public static Location deserialize(String source) {
        if (source == null) {
            return null;
        }

        String[] split = source.split(":");
        World world = Bukkit.getServer().getWorld(split[0]);

        if (world == null) {
            return null;
        }

        return new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }

}
