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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class Tornado extends MainEffectKill {

    public Tornado() {
        super("tornado", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.tornado.name")) : ("§cTornado"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.TORNADO.getTexture());
    }

    @Override
    public void update(User user) {
        Location location = user.getPlayer().getLocation();
        new BukkitRunnable() {
            int angle = 0;

            @Override
            public void run() {
                int max_height = 7;
                double max_radius = 5;
                int lines = 5;
                double height_increasement = 0.25;
                double radius_increasement = max_radius / max_height;
                for (int l = 0; l < lines; l++) {
                    for (double y = 0; y < max_height; y += height_increasement) {
                        double radius = y * radius_increasement;
                        double x = Math.cos(Math.toRadians(360 / lines * l + y * 30 - angle)) * radius;
                        double z = Math.sin(Math.toRadians(360 / lines * l + y * 30 - angle)) * radius;
                        Particle.play(location.clone().add(x, y, z), Effect.CLOUD);
                    }
                }
                angle++;
                if (angle == 70) {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 2, 0);
    }
}
