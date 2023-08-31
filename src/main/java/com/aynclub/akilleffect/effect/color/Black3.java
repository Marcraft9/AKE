package com.aynclub.akilleffect.effect.color;

import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.Particle;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import com.aynclub.akilleffect.utils.maths.MathUtils;
import org.bukkit.Effect;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Arrays;

public class Black3 extends MainEffectKill {

    public Black3() {
        super("block3", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.block3.name")) : ("§cBlock3"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.Blacksoup.getTexture());
    }

    @Override
    public void update(User user) {
        Location loc = user.getPlayer().getLocation();
        for (double height = 0.0; height < 1.0; height += 0.2) {
            Particle.play(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), height, MathUtils.randomRange(-1.0f, 1.0f)), Effect.POTION_SWIRL_TRANSPARENT);
            Particle.play(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), height, MathUtils.randomRange(-1.0f, 1.0f)), Effect.POTION_SWIRL_TRANSPARENT);
        }
    }
}
