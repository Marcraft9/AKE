package com.aynclub.akilleffect.Command;

import com.aynclub.akilleffect.Main;
import com.aynclub.akilleffect.utils.User;
import com.aynclub.akilleffect.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Help implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length < 1) {
                Player p = (Player) sender;
                p.sendMessage(Utils.colorize(((String) Utils.gfc("config", "help-command-message"))));
            } else {
                String menu = args[0];
                if (menu.equals("menu")) {
                    Player p = (Player) sender;
                    Main.getManager().buildInventory(User.getUser(p.getUniqueId())).open(p);
                } else {
                    Player p = (Player) sender;
                    p.sendMessage("Unknown command.");
                }
            }
        } else {
            System.out.println("Only players in game can use this command.");
        }
        return true;
    }
}