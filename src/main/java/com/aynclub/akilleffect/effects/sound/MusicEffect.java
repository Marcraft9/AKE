package com.aynclub.akilleffect.effects.sound;

import com.aynclub.akilleffect.core.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MusicEffect implements Effect {

    @Override
    public void play(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            // Play different music sounds at the location
            world.playSound(location, Sound.MUSIC_DISC_FAR, 0.7F, 1.0F);
            try {
                Thread.sleep(1000); // Wait 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            world.playSound(location, Sound.BLOCK_NOTE_BLOCK_PLING, 0.7F, 1.2F);
            try {
                Thread.sleep(500); // Wait 0.5 seconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.7F, 1.0F);
        }
    }

    @Override
    public String getName() {
        return "Music";
    }
}