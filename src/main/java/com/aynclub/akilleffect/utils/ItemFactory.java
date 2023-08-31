package com.aynclub.akilleffect.utils;

import com.aynclub.akilleffect.effect.MainEffectKill;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemFactory {
    public static ItemStack create(Material material, byte data, String displayName, String... lore) {
        @SuppressWarnings("deprecation")
        ItemStack itemStack = new MaterialData(material, data).toItemStack(1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            List<String> finalLore = new ArrayList<String>();
            Collections.addAll(finalLore, lore);
            itemMeta.setLore(finalLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack create(Material material, byte data, String displayName) {
        return create(material, data, displayName, (String[]) null);
    }

    public static void addHiddenLore(ItemStack item, String lore) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsItem.getTag();

        if (tag == null) {
            tag = new NBTTagCompound();
        }

        NBTTagCompound displayTag = tag.getCompound("display");

        if (!displayTag.hasKey("Lore")) {
            displayTag.set("Lore", new net.minecraft.server.v1_8_R3.NBTTagList());
        }

        net.minecraft.server.v1_8_R3.NBTTagList loreTag = displayTag.getList("Lore", 8);
        loreTag.add(new net.minecraft.server.v1_8_R3.NBTTagString(lore));
        displayTag.set("Lore", loreTag);
        tag.set("display", displayTag);
        nmsItem.setTag(tag);

        item.setItemMeta(CraftItemStack.getItemMeta(nmsItem));
    }
}
