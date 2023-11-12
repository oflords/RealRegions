package dev.oflords.realregions.util;

import dev.oflords.realregions.RealRegions;
import dev.oflords.realregions.region.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.UUID;

public class SQL {

    private Connection connection;

    private String address;
    private String database;
    private int port;
    private String username;
    private String password;

    public SQL(FileConfiguration config) {
        if (config.isConfigurationSection("database")) {
            ConfigurationSection configCursor = config.getConfigurationSection("database");
            this.address = configCursor.getString("address");
            this.database = configCursor.getString("database");
            this.port = configCursor.getInt("port"); // 3306
            this.username = configCursor.getString("username");
            this.password = configCursor.getString("password");

            this.openConnection();
            // if (this.loaded) this.createTable();
        }
    }

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

    private void openConnection() {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.address + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password.isEmpty() ? null : this.password);
            // this.connection = DriverManager.getConnection("jdbc:mysql://" + this.address + ":" + this.port + "/" + this.database + ";useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", this.username, this.password.isEmpty() ? null : this.password);
            RealRegions.get().getLogger().info("[Database] Connection to the database was successful.");
        } catch (SQLException ex) {
            RealRegions.get().getLogger().info("[Database] Could not establish a connection to a database... Shutting down");
            RealRegions.get().getLogger().info("[Database] Reason: " + ex.getMessage());

            Bukkit.getPluginManager().disablePlugin(RealRegions.get());
            Bukkit.getServer().shutdown();
        }
    }

    public Connection getConnection() {
        if (connection == null) openConnection();
        return connection;
    }

    public void closeConnection() {
        try {
            if (this.connection != null && !connection.isClosed()) this.connection.close();
        } catch (SQLException ex) {
//            ex.printStackTrace();
        }
    }

    public void save() {
        for (Region region : Region.regions) {
            String save;
            if (region.getId() > 0) {
                save = "INSERT INTO regions (Name, Owner, Pos1, Pos2) VALUES ('" + region.getName() + "', '" + region.getOwner().toString() + "', '" + region.getPos1().toString() + "', '" + region.getPos2().toString() + "');";
            } else {
                save = "UPDATE regions SET (Name = '" + region.getName() + "', Owner = '" + region.getOwner().toString() + "', Pos1 = '" + region.getPos1().toString() + "', Pos2 = '" + region.getPos2().toString() + "') WHERE ID = " + region.getId() + ";";
            }

            for (UUID uuid : region.getWhitelist()) {
                String saveWhitelist = "INSERT INTO regions_whitelist (RegionID, User) VALUES (1, '" + uuid.toString() + "')";
            }
        }
    }

    public void load() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM regions WHERE Active = 1")) {
            ResultSet data = statement.executeQuery();

            if (data.next()) {
                // new region

                // for each result
                // new region
                String whitelist = "SELECT * FROM regions_whitelist WHERE RegionID = 1 AND Active = 1";
                // for each result
                // region.getWhitelist().add(result);
            }
        } catch (SQLException e) {

        }
    }
}
