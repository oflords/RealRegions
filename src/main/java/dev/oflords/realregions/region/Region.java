package dev.oflords.realregions.region;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Region {

    public static List<Region> regions = new ArrayList<>();

    private String name;

    private UUID owner;
    private List<UUID> whitelist;

    private Location pos1;
    private Location pos2;

    public Region(String name, UUID owner, Location pos1, Location pos2) {
        this.name = name;
        this.owner = owner;
        this.pos1 = pos1;
        this.pos2 = pos2;

        this.whitelist = new ArrayList<>();
        this.whitelist.add(owner);
    }

    public UUID getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public List<UUID> getWhitelist() {
        return whitelist;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }
}
