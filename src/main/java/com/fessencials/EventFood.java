package com.fessencials;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class EventFood implements Listener {

    @EventHandler
    public void foodEvent(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }
}
