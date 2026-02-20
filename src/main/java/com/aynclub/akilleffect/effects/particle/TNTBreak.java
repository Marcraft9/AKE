package com.aynclub.akilleffect.effect.particle;

import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import com.aynclub.akilleffect.utils.maths.MathUtils;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public class TNTBreak extends MainEffectKill {

    public TNTBreak() {
        super("tntbreak", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.tntbreak.name")) : ("§cTNTBreak"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.TNT.getTexture());
    }

    @Override
    public void update(User user) {
        Location loc = user.getPlayer().getLocation();
        for (double height = 0.0; height < 1.0; height += 0.8) {
            user.getPlayer().getWorld().playEffect(loc.clone().add(MathUtils.randomRange(-1.0f, 1.0f), height, MathUtils.randomRange(-1.0f, 1.0f)), Effect.STEP_SOUND, Material.TNT);
            user.getPlayer().getWorld().playEffect(loc.clone().add(MathUtils.randomRange(1.0f, -1.0f), height, MathUtils.randomRange(-1.0f, 1.0f)), Effect.STEP_SOUND, Material.TNT);
        }
    }
}
