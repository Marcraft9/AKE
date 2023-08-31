package com.aynclub.akilleffect.effect.special;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.Particle;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import com.aynclub.akilleffect.utils.maths.MathUtils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class Satan extends MainEffectKill {

    public Satan() {
        super("satan", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.satan.name")) : ("§cSatan"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.DEVIL.getTexture());
    }

    @Override
    public void update(User user) {
        Location loc = user.getPlayer().getLocation();
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(user.getPlayer().getName());
        skull.setItemMeta(skullMeta);
        ArmorStand armor = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armor.setVisible(false);
        armor.setCustomName("§c§l" + user.getPlayer().getName() + " Fall asleep! :)");
        armor.setCustomNameVisible(true);
        armor.setHelmet(skull);
        armor.setGravity(false);
        as.add(armor);
        new BukkitRunnable() {
            int i = 0;

            public void run() {
                i++;
                for (int i = 0; i < 2; i++) {
                    Particle.play(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 3D, MathUtils.randomRange(-1.0F, 1.0F)), Effect.LARGE_SMOKE);
                    Particle.play(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 3D, MathUtils.randomRange(-1.0F, 1.0F)), Effect.LARGE_SMOKE);
                    Particle.play(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 3D, MathUtils.randomRange(-1.0F, 1.0F)), Effect.LARGE_SMOKE);
                    Particle.play(loc.clone().add(MathUtils.randomRange(-1.0F, 1.0F), 3D, MathUtils.randomRange(-1.0F, 1.0F)), Effect.LARGE_SMOKE);
                }
                Particle.play(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.5D, MathUtils.randomRange(-0.8F, 0.8F)), Effect.FLAME);
                Particle.play(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.5D, MathUtils.randomRange(0.8F, -0.8F)), Effect.FLAME);
                Particle.play(loc.clone().add(MathUtils.randomRange(-0.8F, 0.8F), 2.5D, MathUtils.randomRange(-0.8F, 0.8F)), Effect.LAVADRIP);
                if (i == 100) {
                    as.remove(armor);
                    armor.remove();
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 1, 0);
    }
}
