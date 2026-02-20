package com.aynclub.akilleffect.effect.special;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.Particle;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class Meteorite extends MainEffectKill {

    public Meteorite() {
        super("meteorite", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.meteorite.name")) : ("§cMeteorite"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.Fire.getTexture());
    }

    @Override
    public void update(User user) {
        Player player = user.getPlayer();
        Location location = player.getLocation();
        int i = -9;
        int x = (int) location.getX();
        int y = (int) (location.getY() + 11);
        int z = (int) (location.getZ() + i);

        Location spawnLocation = new Location(location.getWorld(), x, y, z);

        Fireball fireball = (Fireball) location.getWorld().spawnEntity(spawnLocation, EntityType.FIREBALL);
        fireball.setShooter(player);

        fireball.setIsIncendiary(false); // 设置火焰弹不会引燃方块
        fireball.setYield(0);

        Vector velocity = new Vector(0, -1, 0);
        fireball.setVelocity(velocity.normalize());
    }
}