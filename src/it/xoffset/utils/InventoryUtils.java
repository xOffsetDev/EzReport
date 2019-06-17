package it.xoffset.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InventoryUtils {

    public static ItemStack getItem(Material item, String name, String[] lore){
        ItemStack itemstack = new ItemStack(item);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(StringUtils.translate(name));
        meta.setLore(Arrays.asList(lore));
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static ItemStack getItem(Material item, String name){
        ItemStack itemstack = new ItemStack(item);
        ItemMeta meta = itemstack.getItemMeta();
        meta.setDisplayName(StringUtils.translate(name));
        itemstack.setItemMeta(meta);
        return itemstack;
    }

    public static Inventory getInventory(String title,int size){
        Inventory inventory = Bukkit.createInventory(null,size,StringUtils.translate(title));
        return inventory;
    }
}
