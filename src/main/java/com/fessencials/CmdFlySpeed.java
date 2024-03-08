package com.fessencials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdFlySpeed implements CommandExecutor {

    String permFlySpeed = "fEssencials.cmdFlySpeed.use";

    @Override
    public boolean onCommand( CommandSender s, Command cmd, String string, String[] a) {

        if(!(s instanceof Player))
            return false;

        if(!s.hasPermission(permFlySpeed)) {
            s.sendMessage("§cVocê não tem permissão para utilizar este comando!");
            return true;
        }

        Player p = (Player) s;

        if(a.length == 0) {

            p.sendMessage("FlySpeed colocado em seu " + (p.getFlySpeed() == 1F ? "minímo!" : "máximo!"));
            p.setFlySpeed(p.getFlySpeed() == 1 ? 0.1F : 1F);
            return true;
        }

        if(a.length == 1) {

            float valueSpeed;

            try {

                valueSpeed = Float.valueOf(a[0]);

            } catch (Exception e) {

                s.sendMessage("§cVocê digitou um número inválido!");
                return true;
            }

            if(valueSpeed < 1 || valueSpeed > 10) {

                p.sendMessage("Valor inválido, digite um valor de 1 a 10.");
                return true;
            }

            p.setFlySpeed(valueSpeed / 10);
        }

        return true;
    }
}

