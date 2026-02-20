package com.aynclub.akilleffect.effects.particle;

import com.aynclub.akilleffect.core.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SpiralEffect implements Effect {

    @Override
    public void play(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            // Create spiral particle effect
            double height = 0;
            double radius = 0;
            int particles = 100;
            
            for (int i = 0; i < particles; i++) {
                double angle = 0.5 * i; // Angle increases gradually
                radius = 0.02 * i;      // Radius increases gradually
                height = 0.1 * i;       // Height increases gradually
                
                // Calculate the position based on angle and radius
                double x = Math.cos(angle) * radius;
                double z = Math.sin(angle) * radius;
                
                Location particleLocation = location.clone().add(x, height, z);
                
                // Spawn particle
                world.spawnParticle(Particle.CRIT_MAGIC, particleLocation, 1);
            }
        }
    }

    @Override
    public String getName() {
        return "Spiral";
    }
}