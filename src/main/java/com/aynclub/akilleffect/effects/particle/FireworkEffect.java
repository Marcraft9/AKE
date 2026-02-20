package com.aynclub.akilleffect.effects.particle;

import com.aynclub.akilleffect.core.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class FireworkEffect implements Effect {

    @Override
    public void play(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            // Create firework explosion effect
            world.playEffect(location, Effect.FIREWORK_EXPLODE, 0);
            
            // Add some particle effects
            world.spawnParticle(Particle.FLAME, location, 20, 0.5, 0.5, 0.5, 0.1);
            world.spawnParticle(Particle.SMOKE_LARGE, location, 15, 0.3, 0.3, 0.3, 0.05);
            world.spawnParticle(Particle.SPARK, location, 10, 0.2, 0.2, 0.2, 0.2);
        }
    }

    @Override
    public String getName() {
        return "Firework";
    }
}