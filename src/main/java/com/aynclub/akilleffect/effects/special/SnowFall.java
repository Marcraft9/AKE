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

public class SnowFall extends MainEffectKill{

    public SnowFall() {
        super("snowfall", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.snowfall.name")) : ("§cSnowFall"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.Snow.getTexture());
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
                int lines = 10;
                double height_increasement = 0.3;
                double radius_increasement = max_radius / max_height;
                for (int l = 0; l < lines; l++) {
                    for (double y = 0; y < max_height; y+=height_increasement ) {
                        double radius = y * radius_increasement;
                        double x = Math.cos(Math.toRadians(180/lines*l + y*30 + angle)) * radius;
                        double z = Math.sin(Math.toRadians(180/lines*l + y*180 *  angle)) * radius;
                        Particle.play(location.clone().add(x,y,z), Effect.SNOW_SHOVEL);
                    }
                }
                angle++;
                if(angle == 70) {
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 2, 0);
    }
}
