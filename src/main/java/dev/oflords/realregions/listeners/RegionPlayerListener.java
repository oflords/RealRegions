package dev.oflords.realregions.listeners;

import dev.oflords.realregions.RegionPlayer;
import dev.oflords.realregions.util.RegionUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RegionPlayerListener implements Listener {

    @EventHandler
    private void onWand(PlayerInteractEvent event) {
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

    @EventHandler
    private void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (RegionUtil.inRegionNotAllowed(player, event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
        }
    }

    @EventHandler
    private void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (RegionUtil.inRegionNotAllowed(player, event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (RegionUtil.inRegionNotAllowed(player, player.getLocation())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
        } else if (event.getClickedBlock() != null && RegionUtil.inRegionNotAllowed(player, event.getClickedBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
        }
    }

    @EventHandler
    private void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (RegionUtil.inRegionNotAllowed(player, event.getEntity().getLocation())) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
            }
        }
    }

    @EventHandler
    private void onHangingRemover(HangingBreakByEntityEvent event) {
        if (event.getRemover() instanceof Player player) {
            if (RegionUtil.inRegionNotAllowed(player, event.getEntity().getLocation())) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
            }
        }
    }

    @EventHandler
    private void onPrime(ExplosionPrimeEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (RegionUtil.inRegionNotAllowed(player, player.getLocation())) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
            }
        }
    }

    @EventHandler
    private void onChange(EntityChangeBlockEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (RegionUtil.inRegionNotAllowed(player, player.getLocation())) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
            }
        }
    }

    @EventHandler
    private void onSign(SignChangeEvent event) {
        Player player = event.getPlayer();

        if (RegionUtil.inRegionNotAllowed(player, event.getBlock().getLocation())) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You are not whitelisted in this claim...");
        }
    }
}
