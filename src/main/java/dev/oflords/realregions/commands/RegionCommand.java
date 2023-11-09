package dev.oflords.realregions.commands;

import dev.oflords.realregions.RegionPlayer;
import dev.oflords.realregions.region.Region;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                openMenu(player);
                return true;
            } else if (args.length == 1) {
                if (args[0].equals("wand")) {
                    ItemStack wand = new ItemStack(Material.GOLDEN_SHOVEL);
                    ItemMeta wandMeta = wand.getItemMeta();
                    wandMeta.setDisplayName(ChatColor.YELLOW + "Region Wand");
                    wand.setItemMeta(wandMeta);
                    player.getInventory().addItem(wand);

                    sender.sendMessage(ChatColor.GREEN + "You have been given a Region Wand!");
                } else if (args[0].equals("create")) {
                    sender.sendMessage("Usage: /region create <name>");
                } else if (args[0].equals("whitelist")) {
                    sender.sendMessage("Usage: /region whitelist <name>");
                } else if (args[0].equals("add")) {
                    sender.sendMessage("Usage: /region add <name> <player>");
                } else if (args[0].equals("remove")) {
                    sender.sendMessage("Usage: /region remove <name> <player>");
                } else {
                    openMenu(player);
                    return true;
                }
            } else if (args.length == 2) {
                String name = args[1];
                if (args[0].equals("create")) {
                    for (Region region : Region.regions) {
                        if (region.getOwner() == player.getUniqueId() && region.getName().toLowerCase().equals(name.toLowerCase())) {
                            player.sendMessage(ChatColor.RED + "You already have a region named " + name);
                            return false;
                        }
                    }

                    RegionPlayer regionPlayer = RegionPlayer.getByUUID(player.getUniqueId());
                    if (regionPlayer == null || regionPlayer.getPos1() == null || regionPlayer.getPos2() == null) {
                        player.sendMessage(ChatColor.RED + "You must use /region wand and set two locations before creating a region!");
                        return false;
                    }

                    new Region(name, player.getUniqueId(), regionPlayer.getPos1(), regionPlayer.getPos2());
                    player.sendMessage(ChatColor.GREEN + "Created new region " + name + "!");
                } else if (args[0].equals("whitelist")) {
                    for (Region region : Region.regions) {
                        if (region.getOwner() == player.getUniqueId() && region.getName().toLowerCase().equals(name.toLowerCase())) {
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
                } else if (args[0].equals("add")) {
                    sender.sendMessage("Usage: /region add <name> <player>");
                } else if (args[0].equals("remove")) {
                    sender.sendMessage("Usage: /region remove <name> <player>");
                }
            } else if (args.length == 3) {
                if (args[0].equals("add")) {
                    sender.sendMessage("add");
                } else if (args[0].equals("remove")) {
                    sender.sendMessage("remove");
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

    private void openMenu(Player player) {
        if (!player.hasPermission("region.menu")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to do this...");
            return;
        }

        player.sendMessage("Open Menu");
    }
}