package com.fessencials;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class EventTime implements Listener {

    @EventHandler
    public void onToggle(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
