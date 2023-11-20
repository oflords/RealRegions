package dev.oflords.realregions.util.menu;

import dev.oflords.realregions.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Button {

    public static Button placeholder(final Material material, String title) {
        return (new Button() {
            public ItemStack getButtonItem(Player player) {
                ItemBuilder itemBuilder = new ItemBuilder(material);
                itemBuilder.name(title);

                return itemBuilder.build();
            }
        });
    }

    public static void playFail(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 1F, 1F);
    }

    public static void playSuccess(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 20F, 15F);
    }

    public static void playNeutral(Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 20F, 1F);
    }

    public abstract ItemStack getButtonItem(Player player);

    public void clicked(Player player, ClickType clickType) {}

    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot) {}

    public void clicked(Player player, int slot, ClickType clickType, int hotbarSlot, ItemStack cursor) {}

    public boolean shouldCancel(Player player, ClickType clickType) {
        return true;
    }

    public boolean shouldUpdate(Player player, ClickType clickType) {
        return false;
    }

}
