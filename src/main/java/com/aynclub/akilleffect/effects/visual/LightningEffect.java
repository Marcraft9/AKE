package com.aynclub.akilleffect.effects.visual;

import com.aynclub.akilleffect.core.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;

public class LightningEffect implements Effect {

    @Override
    public void play(Player player, Location location) {
        World world = location.getWorld();
        if (world != null) {
            // Create a lightning strike effect at the location (without damage)
            LightningStrike lightning = world.strikeLightningEffect(location);
        }
    }

    @Override
    public String getName() {
        return "LightningVisual";
    }
}