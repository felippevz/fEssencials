package com.fessencials;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class CmdVanish implements CommandExecutor {

    private VanishManager vanishManager;
    private JavaPlugin plugin;
    public static String permVanish = "fVanish.usage";


    public CmdVanish(JavaPlugin plugin, VanishManager manager) {
        this.plugin = plugin;
        this.vanishManager = manager;
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String string, String[] a) {

        if(!(s instanceof Player))
            return false;

        if(!s.hasPermission(CmdVanish.permVanish)) {
            s.sendMessage("§cVocê não tem permissão para utilizar este comando!");
            return true;
        }

        Player p = (Player) s;

        if(vanishManager.containsPlayer(s.getName())) {

            for(Player player: Bukkit.getOnlinePlayers())
                    player.showPlayer(p);

            vanishManager.removePlayer(p.getName());

            s.sendMessage("§8Vanish desativado.");
            return true;
        } else {

            for (Player player : Bukkit.getOnlinePlayers())
                if(!player.hasPermission(permVanish))
                    player.hidePlayer(p);

            vanishManager.addPlayer(p);

            s.sendMessage("§8Vanish ativado.");
            return true;
        }
    }
}
