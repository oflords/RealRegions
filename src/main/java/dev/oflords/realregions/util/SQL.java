package dev.oflords.realregions.util;

public class SQL {
    // Main Table Structure:
    // ID - int (auto increment, primary key)
    // Name - varchar(100)
    // Owner - UUID
    // Whitelist - ID of second table
    // Pos1 - varchar(50)
    // Pos2 - varchar(50)
    // Active - bool

    // Whitelist Table Structure:
    // ID - int (auto increment, primary key)
    // RegionID - int
    // User - UUID
    // Active - bool
}
