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

    private RealRegions plugin;

    private Connection connection;

    private String address;
    private String database;
    private int port;
    private String username;
    private String password;

    public SQL(FileConfiguration config, RealRegions plugin) {
        this.plugin = plugin;

        if (config.isConfigurationSection("database")) {
            ConfigurationSection configCursor = config.getConfigurationSection("database");
            this.address = configCursor.getString("address");
            this.database = configCursor.getString("database");
            this.port = configCursor.getInt("port"); // 3306
            this.username = configCursor.getString("username");
            this.password = configCursor.getString("password");

            this.openConnection();
            this.createTable();
        }
    }

    // realregions_regions
    // realregions_whitelists

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
            plugin.getLogger().info("[Database] Connection to the database was successful.");
        } catch (SQLException ex) {
            plugin.getLogger().info("[Database] Could not establish a connection to a database... Shutting down");
            plugin.getLogger().info("[Database] Reason: " + ex.getMessage());

            Bukkit.getPluginManager().disablePlugin(plugin);
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

    public void createTable() {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE `realregions_regions` (`ID` INT(20) AUTO_INCREMENT,`Name` VARCHAR(100),`Owner` VARCHAR(40),`Pos1` VARCHAR(100),`Pos2` VARCHAR(100),`Active` BOOLEAN,PRIMARY KEY (`ID`));")) {
            statement.execute();
        } catch (SQLException e) {
            plugin.getLogger().info("[Database] Error 1: " + e.getMessage());
        }

        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE `realregions_whitelists` (`ID` INT(20) AUTO_INCREMENT,`RegionId` INT(20),`User` VARCHAR(40),`Active` BOOLEAN,PRIMARY KEY (`ID`));")) {
            statement.execute();
        } catch (SQLException e) {
            plugin.getLogger().info("[Database] Error 2: " + e.getMessage());
        }
    }

    public void save() {
        for (Region region : Region.regions) {
            String save;
            int regionId = 0;
            if (region.getId() > 0) {
                regionId = region.getId();
                save = "UPDATE realregions_regions SET (Name = '" + region.getName() + "', Owner = '" + region.getOwner().toString() + "', Pos1 = '" + LocationUtil.serialize(region.getPos1()) + "', Pos2 = '" + LocationUtil.serialize(region.getPos2()) + "') WHERE ID = " + regionId + ";";
            } else {
                save = "INSERT INTO realregions_regions (Name, Owner, Pos1, Pos2, Active) VALUES ('" + region.getName() + "', '" + region.getOwner().toString() + "', '" + LocationUtil.serialize(region.getPos1()) + "', '" + LocationUtil.serialize(region.getPos2()) + "', 1);";
            }

            try (PreparedStatement statement = connection.prepareStatement(save)) {
                statement.execute();
            } catch (SQLException e) {
                plugin.getLogger().info("[Database] Error 3: " + e.getMessage());
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT Id FROM realregions_regions WHERE Name = '" + region.getName() + "' AND Owner = '" + region.getOwner().toString() + "'")) {
                ResultSet resultSet = statement.executeQuery();
                resultSet.first();
                regionId = resultSet.getInt("Id");
            } catch (SQLException e) {
                plugin.getLogger().info("[Database] Error 4: " + e.getMessage());
            }

            for (UUID uuid : region.getWhitelist()) {
                String saveWhitelist = "INSERT INTO realregions_whitelists (RegionID, User) VALUES (" + regionId + ", '" + uuid.toString() + "')";

                try (PreparedStatement statement = connection.prepareStatement(saveWhitelist)) {
                    statement.execute();
                } catch (SQLException e) {
                    plugin.getLogger().info("[Database] Error 5: " + e.getMessage());
                }
            }
        }
    }

    public void load() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM realregions_regions WHERE Active = 1")) {
            ResultSet data = statement.executeQuery();

            while (data.next()) {
                plugin.getLogger().info("[Database] Loading region: " + data.getString("Name"));
                Region region = new Region(data.getString("Name"),
                        UUID.fromString(data.getString("Owner")),
                        LocationUtil.deserialize(data.getString("Pos1")),
                        LocationUtil.deserialize(data.getString("Pos2")));
                region.setId(data.getInt("ID"));

                String whitelist = "SELECT * FROM realregions_whitelists WHERE RegionID = " + data.getInt("ID") + " AND Active = 1";

                try (PreparedStatement whitelistStatement = connection.prepareStatement(whitelist)) {
                    ResultSet whitelistData = whitelistStatement.executeQuery();

                    while (whitelistData.next()) {
                        UUID whitelistUser = UUID.fromString(whitelistData.getString("User"));
                        if (!region.getWhitelist().contains(whitelistUser)) {
                            region.getWhitelist().add(whitelistUser);
                        }
                    }
                } catch (SQLException e) {
                    plugin.getLogger().info("[Database] Error 6: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            plugin.getLogger().info("[Database] Error 7: " + e.getMessage());
        }

        plugin.getLogger().info("[Database] Loaded " + Region.regions.size() + " regions!");
    }
}
