package dev.oflords.realregions.util;

import dev.oflords.realregions.region.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RegionUtil {

    public static boolean inOtherRegion(Location location) {
        for (Region region : Region.regions) {
            if (isLocationWithinBounds(location, region.getPos1(), region.getPos2())) {
                return true;
            }
        }

        return false;
    }
    public static boolean inRegionNotAllowed(Player player, Location location) {
        if (player.hasPermission("region.bypass")) {
            return false;
        }

        for (Region region : Region.regions) {
            if (isLocationWithinBounds(location, region.getPos1(), region.getPos2())) {
                return region.getOwner() != player.getUniqueId() && !region.getWhitelist().contains(player.getUniqueId());
            }
        }

        return false;
    }

    public static boolean isLocationWithinBounds(Location target, Location corner1, Location corner2) {
        double minX = Math.min(corner1.getX(), corner2.getX());
        double minY = Math.min(corner1.getY(), corner2.getY());
        double minZ = Math.min(corner1.getZ(), corner2.getZ());

        double maxX = Math.max(corner1.getX(), corner2.getX());
        double maxY = Math.max(corner1.getY(), corner2.getY());
        double maxZ = Math.max(corner1.getZ(), corner2.getZ());

        return target.getX() >= minX && target.getX() <= maxX
                && target.getY() >= minY && target.getY() <= maxY
                && target.getZ() >= minZ && target.getZ() <= maxZ;
    }
}
