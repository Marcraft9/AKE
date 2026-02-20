package com.aynclub.akilleffect.effect.color;

import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.Particle;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Black1 extends MainEffectKill {

    public Black1() {
        super("black1", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.black1.name")) : ("§cBlack1"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.Blacksoup.getTexture());
    }

    @Override
    public void update(User user) {
        Player player = user.getPlayer();
        Location location = player.getLocation();
        Location locationy = player.getLocation();
        double ly = location.getY() + 2;
        locationy.setY(ly);

        double radius = 4; // 半圆半径
        int particles = 30; // 粒子数量

        for (int i = 0; i < particles; i++) {
            double angle = 360.0 / particles * i;
            double radians = Math.toRadians(angle);

            double x = location.getX() + radius * Math.cos(radians);
            double y = location.getY();
            double z = location.getZ() + radius * Math.sin(radians);

            Location particleLocation = new Location(location.getWorld(), x, y, z);
            Particle.play(particleLocation, Effect.PARTICLE_SMOKE);
            Particle.play(locationy, Effect.PARTICLE_SMOKE);
        }
    }
}
