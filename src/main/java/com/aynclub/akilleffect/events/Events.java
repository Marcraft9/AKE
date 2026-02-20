package com.aynclub.akilleffect.event;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.database.FlatFile;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class Events implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        FlatFile.getValue(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        FlatFile.setValue(player.getUniqueId());
    }
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory() == null || event.getCurrentItem() == null || event.getWhoClicked() == null) {
            return;
        }
        if (event.getView().getTitle().equalsIgnoreCase(Utils.colorize((String) Utils.gfc("messages", "menu.effectKill")))) {
            event.setCancelled(true);
            if (!event.getCurrentItem().hasItemMeta() || !event.getCurrentItem().getItemMeta().hasDisplayName()) {
                return;
            }
            String despawn = Utils.colorize((String) Utils.gfc("messages", "menu.despawn"));
            String spawn = Utils.colorize((String) Utils.gfc("messages", "menu.spawn"));
            User user = User.getUser(event.getWhoClicked().getUniqueId());
            if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(despawn) && user.getEffectKill() != null) {
                user.getEffectKill().despawn(user);
                event.getWhoClicked().closeInventory();
            }
            if (event.getCurrentItem().getItemMeta().getDisplayName().startsWith(spawn)) {
                if (user.getEffectKill() != null) {
                    user.getEffectKill().despawn(user);
                }
                String displayName = event.getCurrentItem().getItemMeta().getDisplayName();
               // System.out.println(displayName);

                String result = extractContent(displayName);
                //System.out.println(result);
                String name = getEffectByName(result);
                //System.out.println("name " + name);

                MainEffectKill ek = Main.getInstance().getEffectKill().get(name);

                String input = String.valueOf(ek);
                int lastDotIndex = input.lastIndexOf(".");
                int atSymbolIndex = input.indexOf("@");
                String extractedText = input.substring(lastDotIndex + 1, atSymbolIndex);
                if (!user.getPlayer().hasPermission("akilleffect.effect." + extractedText)) {
                    user.getPlayer().sendMessage(Utils.colorize(((String) Utils.gfc("messages", "no-permission")).replace("%prefix%", Main.prefix)));
                    event.getWhoClicked().closeInventory();
                    return;
                }
                if (ek != null) {
                        user.setEffectKill(ek);
                        System.out.println(ek);
                        String input1 = String.valueOf(ek);
                        int lastDotIndex1 = input1.lastIndexOf(".");
                        int atSymbolIndex1 = input1.indexOf("@");
                        String extractedText1 = input1.substring(lastDotIndex1 + 1, atSymbolIndex1);
                        String lowerCaseString = extractedText1.toLowerCase();
                        String ename = (String) Utils.gfc("messages", "effectKill." + lowerCaseString + ".name");
                        //System.out.println("ex " + extractedText1);
                        //System.out.println("ename " + ename);
                        event.getWhoClicked().sendMessage
                                (Utils.colorize(((String) Utils.gfc("messages", "spawn")).replaceAll("%effectname%", ename)).replaceAll("%prefix%", Main.prefix));
                        event.getWhoClicked().closeInventory();
                    } else{
                        event.getWhoClicked().sendMessage("null");
                    }
                }
        }


    }

    public String getEffectByName(String name) {
        for (MainEffectKill effectKills : MainEffectKill.instanceList) {
            String displayname = name.replaceAll(Utils.colorize((String) Utils.gfc("messages", "menu.spawn")) + " ", "");
            if (effectKills.getDisplayName().equalsIgnoreCase(displayname)) {
                return effectKills.getName();
            }
        }
        return null;
    }

    public static String extractContent(String input) {
        int startIndex = input.indexOf("(");
        int endIndex = input.indexOf(")");

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return input.substring(startIndex + 1, endIndex);
        }

        return "";  // 如果没有找到匹配的括号，则返回空字符串
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity() != null) {
            if (event.getEntity().getKiller() != null){
                Player killer = event.getEntity().getKiller();
                Player dead = event.getEntity();
                User Dead = User.getUser(dead.getUniqueId());
                User userKill = User.getUser(killer.getUniqueId());
                if (userKill.getEffectKill() != null) {
                    userKill.getEffectKill().update(Dead);
                }
            }
        }
    }
}

