package com.aynclub.akilleffect.core;

import com.aynclub.akilleffect.commands.ConfigChecker;
import com.aynclub.akilleffect.commands.Help;
import com.aynclub.akilleffect.managers.FlatFile;
import com.aynclub.akilleffect.effects.MainEffectKill;
import com.aynclub.akilleffect.events.Events;
import com.aynclub.akilleffect.utils.Manager;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.YAMLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public static String PREFIX;
    private static Main instance;
    private static Manager manager;
    private final Map<String, MainEffectKill> effectKillMap = new HashMap<>();
    private String token;

    public static Main getInstance() {
        return instance;
    }

    public static Manager getManager() {
        return manager;
    }

    public static Main get() {
        return getPlugin(Main.class);
    }

    @Override
    public void onEnable() {
        instance = this;
        manager = new Manager();

        String version = getDescription().getVersion();
        if (!Main.get().getDescription().getAuthors().contains("73m9")) {
            getLogger().severe("");
            getLogger().severe("aKilleffect V" + version);
            getLogger().severe("");
            getLogger().severe("");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!Main.get().getDescription().getName().contains("aKilleffect")) {
            getLogger().severe("");
            getLogger().severe("aKilleffect V" + version);
            getLogger().severe("");
            getLogger().severe("");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Manager.buildConfigs("config");


        // 获取FileConfiguration对象
        FileConfiguration config = getConfig();
        // 从config.yml中获取token的值
        token = config.getString("token");
        getLogger().info("-------------------------------------");
        getLogger().info("Verifier: Please stand by......");
        getLogger().info("Your Token: " + token);
        getLogger().info("");
        if (!new ConfigChecker(token, "http://v.ayn.asia/verify.php", this).setConsoleLog(ConfigChecker.LogType.LOW).register()) return;

        getServer().getPluginManager().registerEvents(new Events(), this);
        manager.loadMinions();
        Manager.buildConfigs("messages");
        FlatFile.checkDatabase();
        PREFIX = Utils.colorize((String) Utils.gfc("messages", "prefix"));
        Bukkit.getOnlinePlayers().forEach(p -> FlatFile.getValue(p.getUniqueId()));


        MainEffectKill.instanceList.forEach(effect -> {
            // 使用 Bukkit 的 Permission 接口创建权限
            String permissionName = "akilleffect.effect." + effect.getName().toLowerCase();

            Permission permission = Bukkit.getPluginManager().getPermission(permissionName);
            if (permission == null) {
                // 创建并注册权限
                permission = new Permission(permissionName);
                Bukkit.getPluginManager().addPermission(permission);
            }
            // 注册权限
        });
    }
    public void inputtoken(YAMLUtils yaml){
        String userInput = JOptionPane.showInputDialog(null, "Please input your aKilleffect Token:\n", "aKilleffect", JOptionPane.PLAIN_MESSAGE);
        Map<String, Object> configMap = new LinkedHashMap<>();
        configMap.put("userInput", userInput);
        yaml.getConfig().set(token,userInput);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(p -> FlatFile.setValue(p.getUniqueId()));
    }

    public Map<String, MainEffectKill> getEffectKill() {
        return effectKillMap;
    }

    //验证
}

