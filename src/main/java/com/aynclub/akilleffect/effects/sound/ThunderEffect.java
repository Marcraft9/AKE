package com.aynclub.akilleffect.effects.sound;

import com.aynclub.akilleffect.core.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ThunderEffect implements Effect {

    @Override
    public void play(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            // Play thunder sound effect
            world.playSound(location, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F);
            try {
                Thread.sleep(500); // Wait half a second before playing the impact sound
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 0.8F, 0.8F);
        }
    }

    @Override
    public String getName() {
        return "Thunder";
    }
}