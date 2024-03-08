package com.fessencials;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CmdTime implements CommandExecutor {

    String permTime = "fEssencials.cmdTime.use";

    JavaPlugin plugin;

    public CmdTime(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] a) {

        if(!(s instanceof Player))
            return false;

        if(!s.hasPermission(permTime)) {
            s.sendMessage("Ops! Você não tem permissão.");
            return true;
        }

        if(a.length == 0)
            return false;

        if(a[0].equalsIgnoreCase("set")) {

            if(a.length != 3) {

                s.sendMessage("Use /time set <tempo> <segundos> para modificar o tempo");
                return true;
            }

            int tempoAlvo;
            int duracaoSegundos;

            try {

                tempoAlvo = Integer.parseInt(a[1]);
                duracaoSegundos = Integer.parseInt(a[2]);

            } catch (NumberFormatException e) {

                s.sendMessage("Ops! Número inválido.");
                return true;
            }

            Player player = (Player) s;
            World world = player.getWorld();

            if(duracaoSegundos < 0) {

                s.sendMessage("Números negativos não são bem vistos por aqui.");
                return true;
            }

            if(duracaoSegundos == 0) {

                world.setTime(tempoAlvo);
                s.sendMessage("§2Tempo modificado com sucesso!");

                return true;
            }

            long diferenca = tempoAlvo - world.getTime();

            int passo = Math.round(((diferenca < 0 ? 24000 + diferenca : diferenca)) / (duracaoSegundos * 20));
            int[] times = new int[1];

            long delay = 0;
            long intervalo = 1;

            int tempoMax = tempoAlvo + passo;
            int tempoMin = tempoAlvo - passo;

            times[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, new Runnable() {

                @Override
                public void run()
                {
                    int worldTime = (int) player.getWorld().getTime();

                    if (worldTime > tempoMin && worldTime < tempoMax) {

                        Bukkit.getScheduler().cancelTask(times[0]);
                        world.setTime(tempoAlvo);
                        s.sendMessage("§2Tempo modificado com sucesso!");

                    }else{

                        world.setTime(worldTime + passo);

                    }
                }
            }, delay, intervalo);

            return true;
        }

        if(a[0].equalsIgnoreCase("stop")) {

            World w = ((Player) s).getWorld();

            w.setGameRuleValue("doDaylightCycle", "false");

            s.sendMessage("Tempo parado com sucesso!");
            return true;
        }

        if(a[0].equalsIgnoreCase("start")) {

            World w = ((Player) s).getWorld();

            w.setGameRuleValue("doDaylightCycle", "true");

            s.sendMessage("Tempo iniciado com sucesso!");
            return true;
        }

        return true;
    }
}
