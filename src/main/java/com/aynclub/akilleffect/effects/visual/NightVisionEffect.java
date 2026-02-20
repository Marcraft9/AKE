package com.aynclub.akilleffect.effects.visual;

import com.aynclub.akilleffect.core.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionEffect implements Effect {

    @Override
    public void play(Player player, Location location) {
        // Give the player night vision effect
        PotionEffect nightVision = new PotionEffect(
            PotionEffectType.NIGHT_VISION, 
            20 * 10,  // Duration: 10 seconds (20 ticks per second)
            1         // Amplifier
        );
        
        // Add the effect to the player
        player.addPotionEffect(nightVision);
    }

    @Override
    public String getName() {
        return "NightVision";
    }
}