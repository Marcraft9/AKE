package com.aynclub.akilleffect.effects.visual;

import com.aynclub.akilleffect.core.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class FireTrailEffect implements Effect {

    @Override
    public void play(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            // Create a fire trail around the player
            for (int i = 0; i < 8; i++) {
                double angle = 2 * Math.PI * i / 8;
                double x = Math.cos(angle) * 0.5;
                double z = Math.sin(angle) * 0.5;
                
                Location fireLocation = location.clone().add(x, 0.1, z);
                
                // Spawn fire particles
                world.spawnParticle(Particle.FLAME, fireLocation, 3, 0.1, 0.1, 0.1, 0.05);
            }
        }
    }

    @Override
    public String getName() {
        return "FireTrail";
    }
}