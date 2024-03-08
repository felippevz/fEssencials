package com.fessencials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdPing implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] a) {

        if(!(s instanceof Player))
            return false;

        Player p = (Player) s;

        try {
            Object entityp = p.getClass().getMethod("getHandle").invoke(p);
            int pingg = (int)entityp.getClass().getField("ping").get(entityp);

            p.sendMessage("Seu ping é: " + ping(pingg));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private String ping(int ping) {
        if(ping >= 250)
            return "§4" + ping + " §7[instável.]";
        else if(ping >= 100)
            return "§c" + ping + " §7[regular.]";
        else if(ping >= 50)
            return "§2" + ping + " §7[bom!]";
        else
            return "§a" + ping + " §7[ótimo!]";
    }
}

