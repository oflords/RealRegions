package dev.oflords.realregions.util.menu.pagination;

import java.util.Arrays;

import dev.oflords.realregions.util.ItemBuilder;
import dev.oflords.realregions.util.menu.Button;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class JumpToPageButton extends Button {

    public JumpToPageButton(int page, PaginatedMenu menu, boolean current) {
        this.page = page;
        this.menu = menu;
        this.current = current;
    }

    private int page;
    private PaginatedMenu menu;
    private boolean current;

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemBuilder itemBuilder = new ItemBuilder(this.current ? Material.ENCHANTED_BOOK : Material.BOOK);
        itemBuilder.amount(this.page);

        itemBuilder.name(ChatColor.AQUA + "Page " + this.page);

        if (this.current) {
            itemBuilder.lore(Arrays.asList(
                    "",
                    ChatColor.GREEN + "Current page"
            ));
        }

        return itemBuilder.build();
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        this.menu.modPage(player, this.page - this.menu.getPage());
        Button.playNeutral(player);
    }

}
