package dev.oflords.realregions.menus;

import dev.oflords.realregions.region.Region;
import dev.oflords.realregions.region.RegionPlayer;
import dev.oflords.realregions.util.menu.Button;
import dev.oflords.realregions.util.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageRegionMenu extends Menu {

    public ManageRegionMenu(Region region) {
        this.region = region;
    }

    private Region region;

    @Override
    public String getTitle(Player player) {
        return "&dManaging " + region.getName();
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, getPlaceholderButton());
        buttons.put(1, new RenameButton(region)); // Rename
        buttons.put(2, getPlaceholderButton());
        buttons.put(3, new AddRegionButton(region)); // Add
        buttons.put(4, getPlaceholderButton());
        buttons.put(5, new RemoveRegionButton(region)); // Remove
        buttons.put(6, getPlaceholderButton());
        buttons.put(7, new RedefineButton(region)); // Redefine
        buttons.put(8, getPlaceholderButton());

        return buttons;
    }

    public static class RenameButton extends Button {

        private Region region;

        public RenameButton(Region region) {
            this.region = region;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemStack(Material.WRITABLE_BOOK);
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            RegionPlayer regionPlayer = RegionPlayer.getByUUID(player.getUniqueId());
            regionPlayer.setRename(region);
            player.sendMessage(ChatColor.YELLOW + "Type the new name of your region in chat...");
        }
    }

    public static class RemoveRegionButton extends Button {

        private Region region;

        public RemoveRegionButton(Region region) {
            this.region = region;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemStack(Material.RED_BANNER);
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            new RegionRemoveMenu(region).openMenu(player);
        }
    }

    public static class AddRegionButton extends Button {

        private Region region;

        public AddRegionButton(Region region) {
            this.region = region;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemStack(Material.GREEN_BANNER);
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            new RegionAddMenu(region).openMenu(player);
        }
    }

    public static class RedefineButton extends Button {

        private Region region;

        public RedefineButton(Region region) {
            this.region = region;
        }

        @Override
        public ItemStack getButtonItem(Player player) {
            return new ItemStack(Material.STICK);
        }

        @Override
        public void clicked(Player player, ClickType clickType) {
            ItemStack wand = new ItemStack(Material.STICK);
            ItemMeta wandMeta = wand.getItemMeta();
            wandMeta.setDisplayName(ChatColor.YELLOW + "Region Wand");
            wand.setItemMeta(wandMeta);
            player.getInventory().addItem(wand);
            player.sendMessage(ChatColor.GREEN + "You have been given a Region Wand to redefine your region!");

            RegionPlayer regionPlayer = RegionPlayer.getByUUID(player.getUniqueId());
            regionPlayer.setRedefine(this.region);
        }
    }

}
