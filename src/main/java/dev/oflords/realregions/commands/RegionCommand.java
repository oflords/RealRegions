package dev.oflords.realregions.commands;

import dev.oflords.realregions.region.RegionPlayer;
import dev.oflords.realregions.menus.ManageRegionMenu;
import dev.oflords.realregions.menus.ViewRegionsMenu;
import dev.oflords.realregions.region.Region;
import dev.oflords.realregions.util.ItemBuilder;
import dev.oflords.realregions.util.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegionCommand implements CommandExecutor, TabCompleter {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                openMenu(player, "");
                return true;
            } else if (args.length == 1) {
                switch (args[0]) {
                    case "wand" -> {
                        ItemBuilder wand = new ItemBuilder(Material.STICK).name(ChatColor.YELLOW + "Region Wand");
                        player.getInventory().addItem(wand.build());
                        sender.sendMessage(ChatColor.GREEN + "You have been given a Region Wand!");
                    }
                    case "create" -> sender.sendMessage("Usage: /region create <name>");
                    case "whitelist" -> sender.sendMessage("Usage: /region whitelist <name>");
                    case "add" -> sender.sendMessage("Usage: /region add <name> <player>");
                    case "remove" -> sender.sendMessage("Usage: /region remove <name> <player>");
                    default -> {
                        openMenu(player, args[1]);
                        return true;
                    }
                }
            } else if (args.length == 2) {
                String name = args[1];

                switch (args[0]) {
                    case "create" -> {
                        if (!player.hasPermission("region.create")) {
                            player.sendMessage(ChatColor.RED + "You do not have permission to do this...");
                            return false;
                        }

                        for (Region region : Region.regions) {
                            if (region.getOwner() == player.getUniqueId() && region.getName().equalsIgnoreCase(name)) {
                                player.sendMessage(ChatColor.RED + "You already have a region named " + name);
                                return false;
                            }
                        }
                        RegionPlayer regionPlayer = RegionPlayer.getByUUID(player.getUniqueId());
                        if (regionPlayer == null || regionPlayer.getPos1() == null || regionPlayer.getPos2() == null) {
                            player.sendMessage(ChatColor.RED + "You must use /region wand and set two locations before creating a region!");
                            return false;
                        }

                        if (RegionUtil.inOtherRegion(player, regionPlayer.getPos1()) || RegionUtil.inOtherRegion(player, regionPlayer.getPos2())) {
                            player.sendMessage(ChatColor.RED + "You cannot make a region within another region...");
                            return false;
                        }

                        new Region(name, player.getUniqueId(), regionPlayer.getPos1(), regionPlayer.getPos2());
                        player.sendMessage(ChatColor.GREEN + "Created new region " + name + "!");

                        regionPlayer.setPos1(null);
                        regionPlayer.setPos2(null);
                    }
                    case "whitelist" -> {
                        if (!player.hasPermission("region.whitelist")) {
                            player.sendMessage(ChatColor.RED + "You do not have permission to do this...");
                            return false;
                        }

                        for (Region region : Region.regions) {
                            if (region.getOwner() == player.getUniqueId() && region.getName().equalsIgnoreCase(name)) {
                                player.sendMessage(ChatColor.AQUA + name + " Whitelist:");
                                if (region.getWhitelist().isEmpty()) {
                                    player.sendMessage("- Nobody");
                                } else {
                                    for (UUID uuid : region.getWhitelist()) {
                                        player.sendMessage("- " + uuid);
                                    }
                                }
                                return false;
                            }
                        }
                        player.sendMessage(ChatColor.RED + "Could not find region named" + name);
                    }
                    case "add" -> sender.sendMessage("Usage: /region add <name> <player>");
                    case "remove" -> sender.sendMessage("Usage: /region remove <name> <player>");
                }
            } else if (args.length == 3) {
                String name = args[1];

                if (args[0].equals("add")) {
                    if (!player.hasPermission("region.add")) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to do this...");
                        return false;
                    }

                    for (Region region : Region.regions) {
                        if (region.getOwner() == player.getUniqueId() && region.getName().equalsIgnoreCase(name)) {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
                            if (offlinePlayer == null) {
                                player.sendMessage(ChatColor.RED + "Could not find player...");
                                return false;
                            }
                            if (region.getWhitelist().contains(offlinePlayer.getUniqueId())) {
                                player.sendMessage(ChatColor.RED + "Player has already been added...");
                                return false;
                            }
                            region.getWhitelist().add(offlinePlayer.getUniqueId());
                            player.sendMessage(ChatColor.GREEN + "Added " + offlinePlayer.getName() + " to your region whitelist!");
                            return false;
                        }
                    }
                    player.sendMessage(ChatColor.RED + "Could not find region named" + name);
                } else if (args[0].equals("remove")) {
                    if (!player.hasPermission("region.remove")) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to do this...");
                        return false;
                    }

                    for (Region region : Region.regions) {
                        if (region.getOwner() == player.getUniqueId() && region.getName().equalsIgnoreCase(name)) {
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);
                            if (offlinePlayer == null) {
                                player.sendMessage(ChatColor.RED + "Could not find player...");
                                return false;
                            }
                            if (!region.getWhitelist().contains(offlinePlayer.getUniqueId())) {
                                player.sendMessage(ChatColor.RED + "Player has not been added...");
                                return false;
                            }
                            region.getWhitelist().add(offlinePlayer.getUniqueId());
                            player.sendMessage(ChatColor.GREEN + "Removed " + offlinePlayer.getName() + " from your region whitelist!");
                            return false;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        completions.add("wand");
        completions.add("create");
        completions.add("whitelist");
        completions.add("add");
        completions.add("remove");

        return completions;
    }

    private void openMenu(Player player, String findRegion) {
        if (!player.hasPermission("region.menu")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to do this...");
            return;
        }

        Region region = null;
        if (findRegion != null && !findRegion.isEmpty()) {
            region = Region.getByName(player, findRegion);
        }

        if (region == null) {
            new ViewRegionsMenu().openMenu(player);
        } else {
            new ManageRegionMenu(region).openMenu(player);
        }
    }
}