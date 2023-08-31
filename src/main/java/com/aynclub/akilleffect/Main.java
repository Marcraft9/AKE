package com.aynclub.akilleffect;

import com.aynclub.akilleffect.Command.ConfigChecker;
import com.aynclub.akilleffect.Command.Help;
import com.aynclub.akilleffect.database.FlatFile;
import com.aynclub.akilleffect.effect.MainEffectKill;
import com.aynclub.akilleffect.event.Events;
import com.aynclub.akilleffect.utils.Manager;
import com.aynclub.akilleffect.utils.Utils;
import com.aynclub.akilleffect.utils.config.YAMLUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import javax.swing.*;
import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends JavaPlugin {

    public static String prefix;
    private static Main instance;
    private static Manager manager;
    private final Map<String, MainEffectKill> effectkill = new HashMap<>();
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
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "aKilleffect" + " V" + version);
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "DON'T CHANGE PLUGIN.YML!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "CHECK PLUGIN.YML AND TRY AGAIN!");
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!Main.get().getDescription().getName().contains("aKilleffect")) {
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "aKilleffect" + " V" + version);
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "DON'T CHANGE PLUGIN.YML!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "CHECK PLUGIN.YML AND TRY AGAIN!");
            Bukkit.getConsoleSender().sendMessage("");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Manager.buildConfigs("config");


        // 获取FileConfiguration对象
        FileConfiguration config = getConfig();
        // 从config.yml中获取token的值
        token = config.getString("token");
        System.out.println("-------------------------------------");
        getLogger().info("Verifier: Please stand by......");
        getLogger().info("Your Token: " + token);
        Bukkit.getConsoleSender().sendMessage("");
        if (!new ConfigChecker(token, "http://v.ayn.asia/verify.php", this).setConsoleLog(ConfigChecker.LogType.LOW).register()) return;

        getServer().getPluginManager().registerEvents(new Events(), this);
        CommandExecutor executor = new Help();
        getCommand("akilleffect").setExecutor(executor);
        manager.loadMinions();
        Manager.buildConfigs("messages");
        FlatFile.checkDatabase();
        prefix = Utils.colorize((String) Utils.gfc("messages", "prefix"));
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
        return effectkill;
    }

    //验证
}

