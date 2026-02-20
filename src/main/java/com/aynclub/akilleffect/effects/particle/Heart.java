package com.aynclub.akilleffect.effect.particle;

import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.Particle;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import com.aynclub.akilleffect.utils.maths.MathUtils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class Heart extends MainEffectKill {

    public Heart() {
        super("heart", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.heart.name")) : ("§cHeart"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.HEART.getTexture());
    }

    @Override
    public void update(User user) {
        Location loc = user.getPlayer().getLocation();
        for (double height = 0.0; height < 1.0; height += 0.2) {
            Particle.play(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), height, MathUtils.randomRange(-1.0f, 1.0f)), Effect.HEART);
            Particle.play(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), height, MathUtils.randomRange(-1.0f, 1.0f)), Effect.HEART);
        }
    }
}
