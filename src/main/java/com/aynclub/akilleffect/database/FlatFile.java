package com.aynclub.akilleffect.database;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.utils.User;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FlatFile {
    private static final File cfgFile = new File("plugins/aKilleffect/database.yml");
    private static final YamlConfiguration cfg = YamlConfiguration.loadConfiguration(cfgFile);

    public static void checkDatabase() {
        if (!cfgFile.exists()) {
            try {
                cfgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setValue(UUID uuid) {
        if (User.getUsers().containsKey(uuid) && User.getUsers().get(uuid).getEffectKill() != null) {
            String value = User.getUser(uuid).getEffectKill().getName();
            cfg.set(uuid.toString(), value);
        } else {
            cfg.set(uuid.toString(), null);
        }
        try {
            cfg.save(cfgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getValue(UUID uuid) {
        if (cfg.contains(uuid.toString())) {
            User user = User.getUser(uuid);
            String ek = cfg.getString(uuid.toString());
            MainEffectKill m = Main.getInstance().getEffectKill().get(ek);
            user.setEffectKill(m);
        }
    }
}
