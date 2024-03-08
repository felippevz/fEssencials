package com.fessencials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFly implements CommandExecutor {

    String permFly = "fEssencials.cmdFly.use";

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] a) {

        if(!(s instanceof Player))
            return false;

        if (!s.hasPermission(permFly)) {
            s.sendMessage("§cVocê não tem permissão para utilizar este comando!");
            return true;
        }

        Player p = (Player) s;

        p.sendMessage("Modo de voo " + (p.getAllowFlight() ? "§cDESATIVADO" : "§aATIVADO"));

        p.setAllowFlight(!p.getAllowFlight());
        p.setFlying(p.getAllowFlight());

        return true;
    }
}
