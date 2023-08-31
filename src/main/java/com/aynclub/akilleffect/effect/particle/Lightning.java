package com.aynclub.akilleffect.effect.particle;

import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import com.aynclub.akilleffect.utils.inventory.Heads;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Arrays;

public class Lightning extends MainEffectKill {

    public Lightning() {
        super("lightning", YAMLUtils.get("messages").getFile().exists() ? ((String) Utils.gfc("messages", "effectKill.lightning.name")) : ("§cLightning"), new ArrayList<>(Arrays.asList("&7A simple gadget...", "&7To make your kills even more entertaining!")), Heads.Lightning.getTexture());
    }

    @Override
    public void update(User user) {
        Location loc = user.getPlayer().getLocation();
        World world = loc.getWorld();
        world.strikeLightningEffect(loc);
    }
}
