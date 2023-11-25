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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionAddMenu extends PaginatedMenu {

    private Region region;

    public RegionAddMenu(Region region) {
        this.region = region;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&5Add Player";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            buttons.put(buttons.size(), new AddPlayerButton(player1, region));
        }

        return buttons;
    }

    public class AddPlayerButton extends Button {

        public AddPlayerButton(Player player, Region region) {
            this.addPlayer = player;
            this.region = region;
        }

        private Player addPlayer;
        private Region region;

        @Override
        public ItemStack getButtonItem(Player player) {
            ItemBuilder itemBuilder = new ItemBuilder(Material.PLAYER_HEAD);

            itemBuilder.name("&b" + addPlayer.getName());
            itemBuilder.setSkullOwner(addPlayer);

            return itemBuilder.build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            region.getWhitelist().add(addPlayer.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "Added new player!");
            player.closeInventory();
        }

    }

}
