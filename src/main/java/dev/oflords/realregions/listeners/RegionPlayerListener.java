package dev.oflords.realregions.listeners;

import dev.oflords.realregions.RegionPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RegionPlayerListener implements Listener {

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK)) {
            ItemStack wand = event.getItem();
            if (wand.getType() == Material.GOLDEN_SHOVEL && wand.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Region Wand")) {
                event.setCancelled(true);
                RegionPlayer regionPlayer = RegionPlayer.getByUUID(event.getPlayer().getUniqueId());
                if (regionPlayer == null) {
                    regionPlayer = RegionPlayer.createProfile(event.getPlayer().getUniqueId());
                }

                if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    regionPlayer.setPos1(event.getClickedBlock().getLocation());
                    event.getPlayer().sendMessage(ChatColor.YELLOW + "Location 1 Set!");
                } else {
                    regionPlayer.setPos2(event.getClickedBlock().getLocation());
                    event.getPlayer().sendMessage(ChatColor.YELLOW + "Location 2 Set!");
                }
            }
        }
    }
}
