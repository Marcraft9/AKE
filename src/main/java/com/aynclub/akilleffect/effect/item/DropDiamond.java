package com.aynclub.akilleffect.effect.item;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.ItemFactory;
import com.aynclub.akilleffect.utils.Particle;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class DropDiamond extends MainEffectKill {

    Random r = new Random();
    ArrayList<Item> items = new ArrayList<Item>();

    public DropDiamond() {
        super("dropdiamond", YAMLUtils.get("messages").getFile().exists()?((String) Utils.gfc("messages", "effectKill.dropdiamond.name")) : ("§cDropDiamond"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.DIAMOND.getTexture());
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < 30; i++) {
            Item ITEM = user.getPlayer().getWorld().dropItem(user.getPlayer().getLocation(), ItemFactory.create(Material.DIAMOND, (byte)0, UUID.randomUUID().toString()));
            ITEM.setPickupDelay(300);
            items.add(ITEM);
            ITEM.setVelocity(new Vector(r.nextDouble() - 0.5D, r.nextDouble() / 2.0D, r.nextDouble() - 0.5D));
        }
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable(){
            public void run() {

                for (Item i : items) {
                    Particle.play(i.getLocation(), Effect.COLOURED_DUST);
                    i.remove();
                }
            }
        },50L);
    }
}

