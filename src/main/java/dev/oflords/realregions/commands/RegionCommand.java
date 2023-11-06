package dev.oflords.realregions.commands;

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
                if (args[0].equals("create")) {
                    sender.sendMessage("Create");
                } else if (args[0].equals("whitelist")) {
                    sender.sendMessage("Whitelist");
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