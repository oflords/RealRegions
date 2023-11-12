package dev.oflords.realregions.util;

import dev.oflords.realregions.region.Region;

import java.util.UUID;

public class SQL {
    // Main Table Structure:
    // ID - int (auto increment, primary key)
    // Name - varchar(100)
    // Owner - UUID
    // Whitelist - ID of second table
    // Pos1 - varchar(50)
    // Pos2 - varchar(50)
    // Active - bool (default true)

    // Whitelist Table Structure:
    // ID - int (auto increment, primary key)
    // RegionID - int
    // User - UUID
    // Active - bool

    public void save() {
        for (Region region : Region.regions) {
            String save = "INSERT INTO regions (Name, Owner, Pos1, Pos2) VALUES ('" + region.getName() + "', '" + region.getOwner().toString() + "', '" + region.getPos1().toString() + "', '" + region.getPos2().toString() + "');";

            for (UUID uuid : region.getWhitelist()) {
                String saveWhitelist = "INSERT INTO regions_whitelist (RegionID, User) VALUES (1, '" + uuid.toString() + "')";
            }
        }
    }

    public void load() {
        String SQL = "SELECT * FROM regions WHERE Active = 1";

        // for each result
        // new region
        String whitelist = "SELECT * FROM regions_whitelist WHERE RegionID = 1 AND Active = 1";
        // for each result
        // region.getWhitelist().add(result);
    }
}
