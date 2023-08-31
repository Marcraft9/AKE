package com.aynclub.akilleffect.effect.particle;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.Particle;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class Wave extends MainEffectKill {

    public Wave() {
        super("wave", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.wave.name")) : ("§cWave"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.WAVE.getTexture());
    }

    @Override
    public void update(User user) {
        Player player = user.getPlayer();
        Location loc = player.getLocation();
        new BukkitRunnable() {
            double t = Math.PI / 4;

            public void run() {
                t = t + 0.1 * Math.PI;
                for (double theta = 0; theta <= 2 * Math.PI; theta = theta + Math.PI / 32) {
                    double x = t * Math.cos(theta);
                    double y = 2 * Math.exp(-0.1 * t) * Math.sin(t) + 1.5;
                    double z = t * Math.sin(theta);
                    loc.add(x, y, z);
                    Particle.play(loc, Effect.WATERDRIP);
                    Particle.play(loc, Effect.SNOW_SHOVEL);
                    loc.subtract(x, y, z);
                    theta = theta + Math.PI / 64;
                }
                if (t > 8) {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 4, 0);
    }
}
