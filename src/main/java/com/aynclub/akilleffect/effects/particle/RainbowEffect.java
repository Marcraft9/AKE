package com.aynclub.akilleffect.effects.particle;

import com.aynclub.akilleffect.core.Effect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RainbowEffect implements Effect {

    @Override
    public void play(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            // Create a rainbow circle effect with different colored particles
            float radius = 2.0f;
            int particles = 36; // Number of particles in the circle
            
            for (int i = 0; i < particles; i++) {
                double angle = 2 * Math.PI * i / particles;
                double x = Math.cos(angle) * radius;
                double z = Math.sin(angle) * radius;
                
                Location particleLocation = location.clone().add(x, 0.5, z);
                
                // Calculate color based on position in the circle
                Color color = getColorForPosition(i, particles);
                
                // Create colored dust particle
                world.spawnParticle(Particle.REDSTONE, particleLocation, 1, new Particle.DustOptions(color, 1.0f));
            }
        }
    }

    private Color getColorForPosition(int position, int total) {
        // Create rainbow colors by mapping position to hue
        float hue = (float) position / total;
        return Color.fromHSV(hue * 360, 1.0f, 1.0f);
    }

    @Override
    public String getName() {
        return "RainbowCircle";
    }
}