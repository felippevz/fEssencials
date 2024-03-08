package com.fessencials;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventVanish implements Listener {

    private VanishManager vanishManager;

    public EventVanish(VanishManager vanishManager) {

        this.vanishManager = vanishManager;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {

        if(!e.getPlayer().hasPermission(CmdVanish.permVanish))
            for(String p : vanishManager.getPlayers())
                try {
                    Player vanished = Bukkit.getPlayer(p);
                    e.getPlayer().hidePlayer(vanished);
                } catch (NullPointerException exception) {
                    vanishManager.removePlayer(p);
                }
        if(vanishManager.containsPlayer(e.getPlayer().getName()))
            for(Player players : Bukkit.getOnlinePlayers())
                if(!players.hasPermission(CmdVanish.permVanish))
                    players.hidePlayer(e.getPlayer());
    }
}