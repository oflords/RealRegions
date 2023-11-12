package dev.oflords.realregions.util.menu.pagination;

import dev.oflords.realregions.util.menu.Button;
import dev.oflords.realregions.util.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BackButton extends Button {

    public BackButton(Menu menu) {
        this.back = menu;
    }

    private Menu back;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemStack(Material.REDSTONE);
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        Button.playNeutral(player);
        back.openMenu(player);
    }

}
