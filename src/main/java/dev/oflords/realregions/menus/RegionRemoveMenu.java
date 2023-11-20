package dev.oflords.realregions.menus;

import dev.oflords.realregions.region.Region;
import dev.oflords.realregions.util.ItemBuilder;
import dev.oflords.realregions.util.menu.Button;
import dev.oflords.realregions.util.menu.pagination.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RegionRemoveMenu extends PaginatedMenu {

    private Region region;

    public RegionRemoveMenu(Region region) {
        this.region = region;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&5Remove Player";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (UUID player1 : region.getWhitelist()) {
            buttons.put(buttons.size(), new RemovePlayerButton(player1, region));
        }

        return buttons;
    }

    public static class RemovePlayerButton extends Button {

        public RemovePlayerButton(UUID player, Region region) {
            this.removePlayer = player;
            this.region = region;
        }

        private UUID removePlayer;
        private Region region;

        @Override
        public ItemStack getButtonItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(removePlayer);

            itemBuilder.name("&b" + offlinePlayer.getName());
            itemBuilder.setSkullOwner(offlinePlayer);

            return itemBuilder.build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            region.getWhitelist().remove(removePlayer);
            player.sendMessage(ChatColor.GREEN + "Removed player!");
            player.closeInventory();
        }

    }

}
