package dev.oflords.realregions.menus;

import dev.oflords.realregions.region.Region;
import dev.oflords.realregions.util.ItemBuilder;
import dev.oflords.realregions.util.menu.Button;
import dev.oflords.realregions.util.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewRegionsMenu extends PaginatedMenu {
    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&5Your Regions";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Region region : Region.regions) {
            if (region.getOwner() == player.getUniqueId()) {
                buttons.put(buttons.size(), new RegionButton(region));
            }
        }

        return buttons;
    }

    public static class RegionButton extends Button {

        public RegionButton(Region region) {
            this.region = region;
        }

        private Region region;

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemBuilder(Material.GREEN_WOOL).name("&fName: &b" + region.getName()).lore("&7Click to manage this Region").build();
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            new ManageRegionMenu(region).openMenu(player);
        }

    }
}
