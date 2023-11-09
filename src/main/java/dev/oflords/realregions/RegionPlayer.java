package dev.oflords.realregions;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.UUID;

public class RegionPlayer {

    public static HashMap<UUID, RegionPlayer> profiles = new HashMap<>();

    private UUID uuid;

    private Location pos1;
    private Location pos2;

    public RegionPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public static RegionPlayer createProfile(UUID uuid) {
        if (profiles.containsKey(uuid)) return getByUUID(uuid);
        RegionPlayer data = new RegionPlayer(uuid);

        profiles.put(uuid, data);
        return data;
    }

    public static void removeProfile(UUID uuid) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(RealRegions.get(), () -> {
            profiles.remove(uuid);
        });
    }

    public static RegionPlayer getByUUID(UUID uuid) {
        if (!profiles.containsKey(uuid)) {
            createProfile(uuid);
        }
        return profiles.get(uuid);
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }
}
