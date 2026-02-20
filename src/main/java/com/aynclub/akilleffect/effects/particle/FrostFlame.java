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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class FrostFlame extends MainEffectKill {

    public FrostFlame() {
        super("frostflame", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.frostflame.name")) : ("§cFrostFlame"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.FLAME.getTexture());
    }

    @Override
    public void update(User user) {
        Location loc = user.getPlayer().getLocation();
        new BukkitRunnable() {
            double t = 0.0D;

            public void run() {
                t += 0.3;
                for (double phi = 0.0D; phi <= 6; phi += 1.5) {
                    double x = 0.11D * (12.5 - t) * Math.cos(t + phi);
                    double y = 0.23D * t;
                    double z = 0.11D * (12.5 - t) * Math.sin(t + phi);
                    loc.add(x, y, z);
                    Particle.play(loc, Effect.FLAME);
                    loc.subtract(x, y, z);

                    if (t >= 12.5) {
                        loc.add(x, y, z);
                        if (phi > Math.PI) {
                            cancel();
                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 1L, 1L);
    }
}
