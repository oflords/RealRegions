package dev.oflords.realregions.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder implements Listener {

    private ItemStack is;

    public ItemBuilder(Material mat) {
        is = new ItemStack(mat);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder amount(int amount) {
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setSkullOwner(Player player) {
        SkullMeta skullMeta = (SkullMeta) is.getItemMeta();
        skullMeta.setOwnerProfile(player.getPlayerProfile());
        is.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setSkullOwner(OfflinePlayer player) {
        SkullMeta skullMeta = (SkullMeta) is.getItemMeta();
        skullMeta.setOwnerProfile(player.getPlayerProfile());
        is.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder unbreakable(Boolean hide) {
        ItemMeta meta = is.getItemMeta();
        if (meta == null) return this;
        meta.setUnbreakable(true);
        if (hide) meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hidePotionDetails() {
        ItemMeta meta = is.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String name) {
        ItemMeta meta = is.getItemMeta();
        List<String> lore = meta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
        }

        lore.add(ChatColor.translateAlternateColorCodes('&', name));
        meta.setLore(lore);

        is.setItemMeta(meta);

        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        List<String> toSet = new ArrayList<>();
        ItemMeta meta = is.getItemMeta();

        for (String string : lore) {
            toSet.add(ChatColor.translateAlternateColorCodes('&', string));
        }

        meta.setLore(toSet);
        is.setItemMeta(meta);

        return this;
    }

    public ItemBuilder damage(int damage) {
        is.setDurability((short) damage);
        return this;
    }

    public ItemBuilder colour(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) is.getItemMeta();
        leatherArmorMeta.setColor(color);
        is.setItemMeta(leatherArmorMeta);

        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        is.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment) {
        is.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder type(Material material) {
        is.setType(material);
        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta meta = is.getItemMeta();

        meta.setLore(new ArrayList<>());
        is.setItemMeta(meta);

        return this;
    }

    public ItemBuilder clearEnchantments() {
        for (Enchantment e : is.getEnchantments().keySet()) {
            is.removeEnchantment(e);
        }

        return this;
    }

    public ItemStack build() {
        ItemMeta meta = is.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        is.setItemMeta(meta);
        return is;
    }

}