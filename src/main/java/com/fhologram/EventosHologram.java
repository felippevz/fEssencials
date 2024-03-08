package com.fhologram;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EventosHologram implements Listener {
	
	public static String nameItem = "Hologram";


	@EventHandler (priority = EventPriority.HIGHEST)
	public void item(ItemDespawnEvent e) {
		try {
			if(e.getEntity().getName().equals(nameItem))
				e.setCancelled(true);
		} catch (Exception ex) {
			//?
		}
	}


	@EventHandler
	public void pickUp(PlayerPickupItemEvent e) {
		try {
			if(e.getItem().getItemStack().getItemMeta().getDisplayName().equals(nameItem))
				e.setCancelled(true);
		} catch (Exception ex) {
			//?
		}
	}


	@EventHandler (priority = EventPriority.HIGHEST)
	public void item(EntityDamageEvent e) {
		try {
			if(e.getEntity().getName().equals(nameItem))
				e.setCancelled(true);
		} catch (Exception ex) {
			//?
		}
	}
}
