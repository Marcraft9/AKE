package com.aynclub.akilleffect.utils;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.effect.color.Black3;
import com.aynclub.akilleffect.effect.item.DropFlowers;
import com.aynclub.akilleffect.effect.item.DropDiamond;
import com.aynclub.akilleffect.effect.item.DropSoup;
import com.aynclub.akilleffect.effect.particle.*;
import com.aynclub.akilleffect.effect.color.Black0;
import com.aynclub.akilleffect.effect.color.Black1;
import com.aynclub.akilleffect.effect.color.Black2;
import com.aynclub.akilleffect.effect.special.Meteorite;
import com.aynclub.akilleffect.effect.special.Satan;
import com.aynclub.akilleffect.effect.special.SnowFall;
import com.aynclub.akilleffect.effect.special.Squid;
import com.aynclub.akilleffect.effect.special.Tornado;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.CustomInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Manager {
    private final Map<String, MainEffectKill> effectKills = new HashMap<>();

    public static void buildConfigs(String... configs) {
        for (String config : configs) {
            if (config.equalsIgnoreCase("config")) {
                YAMLUtils yaml = YAMLUtils.get("config");
                checkAndSetConfigText(yaml, "token", "");
                checkAndSetConfigText(yaml, "help-command-message",
                        "aKilleffect - A Killeffect plugin made by aynclub.\n" +
                                "\n"+
                         "/akilleffect - Show this message.\n" +
                         "/akilleffect menu - Open effects menu.\n"+
                        "\n");
                yaml.save();
            } else if (config.equalsIgnoreCase("messages")) {
                YAMLUtils yaml = YAMLUtils.get("messages");
                checkAndSetConfigText(yaml, "prefix", "&4■ &cKilleffect &7-");
                checkAndSetConfigText(yaml, "no-permission", "%prefix% &cYou do not have the required permission!");
                checkAndSetConfigText(yaml, "no-player", "%prefix% &cThis player %player% doesn't exist");
                checkAndSetConfigText(yaml, "list-effect", "%prefix% &cThe Effect &e%effectname% &cdoes not exist. Here is the list of effects:&a ");
                checkAndSetConfigText(yaml, "remove", "%prefix% &cYou deleted your effect");
                checkAndSetConfigText(yaml, "spawn", "%prefix% &fYou selected %effectname%");
                checkAndSetConfigText(yaml, "check-permission-yes", "&a&nYou can switch this effect! ");
                checkAndSetConfigText(yaml, "check-permission-no", "&c&nYou don't have this effect yet! :(");
                checkAndSetConfigText(yaml, "menu.effectKill", "&7EffectMenu");
                checkAndSetConfigText(yaml, "menu.spawn", "&2✔  ");
                checkAndSetConfigText(yaml, "menu.despawn", "&c✘ ");
                checkAndSetConfigText(yaml, "menu.effect", "&fState -> ");

                for (MainEffectKill effectKill : MainEffectKill.instanceList) {
                    String effectName = effectKill.getName();
                    //System.out.println(effectName);
                    checkAndSetConfigText(yaml, "effectKill." + effectName + ".name", "§c"+capitalizeFirstLetter(effectKill.getName()));
                    checkAndSetConfigText(yaml, "effectKill." + effectName + ".description", effectKill.getDescription());
                }
                yaml.save();
            } else {
                throw new NullPointerException(config + " is not defined!");
            }
        }
    }

    private static void checkAndSetConfigText(YAMLUtils yaml, String key, Object defaultValue) {
        if (!yaml.getConfig().contains(key)) {
            yaml.getConfig().set(key, defaultValue);
        }
    }
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public void loadMinions() {
        MainEffectKill.instanceList.addAll(Arrays.asList(

                // color
                new Black0(),
                new Black1(),
                new Black2(),
                new Black3(),
                //item
                new DropSoup(),
                new DropDiamond(),
                new DropFlowers(),
                //particle
                new Heart(),
                new Wave(),
                new FrostFlame(),
                new TNT(),
                new TNTBreak(),
                new Redstone(),
                new Lightning(),
                //special
                new Tornado(),
                new Squid(),
                new Satan(),
                new SnowFall(),
                new Meteorite()));
        MainEffectKill.instanceList.forEach(o -> MainEffectKill.effectList.add(o.getClass()));
    }


    public Map<String, MainEffectKill> getEffectKills() {
        return effectKills;
    }

    @SuppressWarnings("unchecked")
    public CustomInventory buildInventory(User user) {
        return new CustomInventory(Main.getInstance(), "effectkill", false, null, Utils.colorize((String) Utils.gfc("messages", "menu.effectKill")), 45).advManipule(customInventory -> {
            if (user.getEffectKill() != null) {
                ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skull = (SkullMeta) item.getItemMeta();
                skull.setDisplayName(Utils.colorize((String) Utils.gfc("messages", "menu.effect")) + user.getEffectKill().getDisplayName());
                skull.setOwner(user.getPlayer().getName());
                item.setItemMeta(skull);
                customInventory.addItem(item, 0);
                customInventory.addItem(ItemsUtils.create(Material.BARRIER, (byte) 0, Utils.colorize((String) Utils.gfc("messages", "menu.despawn"))), 8);
            }
            int i = 9;
            for (MainEffectKill effectKills : MainEffectKill.instanceList) {
                String itemName = (String) Utils.gfc("messages", "menu.spawn");
                if (user.getEffectKill() != null && user.getEffectKill().getName().equalsIgnoreCase(effectKills.getName()))
                    itemName = (String) Utils.gfc("messages", "menu.despawn");
                List<String> lores = ((List<String>) Utils.gfc("messages", "effectKill." + effectKills.getName() + ".description")).stream().map(Utils::colorize).collect(Collectors.toList());
                if (user.getPlayer().hasPermission("akilleffect.effect." + effectKills.getName())) {
                    lores.add(" "); // 获取权限显示文本
                    lores.add(Utils.colorize(Utils.colorize((String) Utils.gfc("messages",  "check-permission-yes")))); // 获取权限显示文本
                } else {
                    lores.add(" ");
                    lores.add(Utils.colorize(Utils.colorize((String) Utils.gfc("messages", "check-permission-no")))); // 获取权限显示文本
                }
                customInventory.addItem(
                        ItemsUtils.create(effectKills.getItemStack(), Utils.colorize(itemName + " " + Utils.gfc("messages", "effectKill." + effectKills.getName() + ".name") + ChatColor.GRAY +" ("+effectKills.getName()+")"), lores)
                        , i);
                i++;
            }
        });
    }
}
