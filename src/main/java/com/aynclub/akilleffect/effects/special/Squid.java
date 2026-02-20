package com.aynclub.akilleffect.effect.special;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.Particle;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class Squid extends MainEffectKill {

    public Squid() {
        super("squid", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.squid.name")) : ("§cSquid"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.SQUID.getTexture());
    }

    @Override
    public void update(User user) {
        Location loc = user.getPlayer().getLocation().add(0, -0.3, 0);
        ArmorStand armor = (ArmorStand) loc.getWorld().spawnEntity(loc.add(0, -1, 0), EntityType.ARMOR_STAND);
        armor.setVisible(false);
        armor.setGravity(false);
        Entity e = user.getPlayer().getWorld().spawnEntity(loc, EntityType.SQUID);
        armor.setPassenger(e);
        as.add(armor);
        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                i++;
                Entity passenger = armor.getPassenger();
                armor.eject();
                armor.teleport(armor.getLocation().add(0, 0.5, 0));
                armor.setPassenger(passenger);
                Particle.play(armor.getLocation().add(0.0, -0.2, 0.0), Effect.FLAME);
                if (i == 20) {
                    as.remove(armor);
                    armor.remove();
                    e.remove();
                    Particle.play(armor.getLocation().add(0.0, 0.5, 0.0), Effect.EXPLOSION_HUGE, 1);
                    float power = 0f; // 爆炸强度
                    boolean setFire = false; // 是否引燃方块
                    Location loc = armor.getLocation();
                    World world = loc.getWorld();
                    world.createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire, false);
                    i = 0;
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 1, 0);
    }
}
