package com.fessencials;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdGm implements CommandExecutor {

    private String permGm = "fEssencials.cmdGm.use";

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] a) {

        if(!(s instanceof Player))
            return false;

        Player p = (Player) s;

        if(!p.hasPermission(permGm)) {
            p.sendMessage("Ops! Você não tem permissão.");
            return true;
        }

        switch (a.length) {

            case 0:

                p.setGameMode(GameMode.getByValue(p.getGameMode().getValue() > 0 ? 0 : 1));
                p.sendMessage("§aGamemode modificado para " + p.getGameMode().name() + " com sucesso!");
                return true;

            case 1:

                if(isNumeric(a[0])) {

                    if(Double.valueOf(a[0]) > 3) {

                        p.sendMessage("§4Gamemode desconhecido.");
                        return true;
                    }

                    p.setGameMode(GameMode.getByValue(Integer.valueOf(a[0])));
                    p.sendMessage("§aGamemode modificado para " + p.getGameMode().name() + " com sucesso!");
                    return true;

                } else {

                    p.sendMessage("§4Argumentos desconhecidos! §8/gm <gamemode>");
                    return true;
                }

            case 2:

                if(isNumeric(a[0])) {

                    if(Double.valueOf(a[0]) > 3) {

                        p.sendMessage("§4Gamemode desconhecido.");
                        return true;
                    }

                    Player target = Bukkit.getPlayer(a[1]);

                    if(target == null) {

                        p.sendMessage("§4Ops! Player desconhecido. §8{" + a[1] + "}");
                        return true;
                    }

                    target.setGameMode(GameMode.getByValue(Integer.valueOf(a[0])));
                    p.sendMessage("§aGamemode do jogador " + target.getName() + " modificada para " + target.getGameMode().name() + " com sucesso!");
                    target.sendMessage("Seu gamemode foi alterado para " + target.getGameMode().name());
                    return true;
                }

            default:
                p.sendMessage("§4Argumentos desconhecidos! §8/gm <gamemode> <player>");
                return true;
        }
    }

    boolean isNumeric(String s) {
        try {
            Double.valueOf(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
