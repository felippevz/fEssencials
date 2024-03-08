package com.fhologram;

import java.lang.reflect.Field;
import java.util.*;

import com.mojang.authlib.GameProfile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.properties.Property;

public class Hologram {
	
	private String Name;
	private Location Location;
	private HashMap<Integer, Line> Lines = new HashMap<Integer, Line>();
	private List<ArmorStand> ArmorStand = new ArrayList<ArmorStand>();
	
	public Hologram(String name, Location location) {
		
		this.setName(name);
		this.setLocation(location);
	}
	
	public void addLine(String line) {
		this.Lines.put((Lines.isEmpty() ? 0 : Lines.size()), new Line(line, Type.STRING, (Lines.isEmpty() ? 0 : Lines.size())));
	}

	public void delete() {
		for(ArmorStand as : ArmorStand) {
			if(as.getPassenger() != null)
				as.getPassenger().remove();
			as.remove();
		}
	}
	
	public void editLine(int amount, String line) {
		this.Lines.get(amount).setTxt(line);
		this.ArmorStand.get(amount).setCustomName(line);
	}
	
	public void addLineItem(String url) {
		this.Lines.put(Lines.size(), new Line(Location.getWorld().dropItem(Location, getSkull(url, EventosHologram.nameItem)), Type.ITEM, Lines.size()));
	}
	
	public void addLineItem(Material type) {
		this.Lines.put(Lines.size(), new Line(Location.getWorld().dropItem(Location, criar(EventosHologram.nameItem, type)), Type.ITEM, Lines.size()));
	}
	
	public Line getLine(int amount) {
		return this.Lines.get(amount);
	}
	
	public void spawn() {
		
		World w = Location.getWorld();
		Location loc = Location;
		Double space = 0.255;
		
		for(Line l : Lines.values()) {
			
			loc.setY(loc.getY() - space);

			ArmorStand as = (org.bukkit.entity.ArmorStand) w.spawnEntity(loc, EntityType.ARMOR_STAND);
			as.setCanPickupItems(false);
			as.setGravity(false);
			as.setVisible(false);
			as.setRemoveWhenFarAway(true);
			as.setCustomNameVisible(false);
			
			if(l.getType() == Type.STRING) {
				
				if(!l.getTxt().isEmpty()) {
					
					as.setCustomNameVisible(true);
					as.setCustomName(l.getTxt());
				}
			
			} else
				as.setPassenger(l.getItem());
				
			this.ArmorStand.add(as);
		}
	}
	
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public Location getLocation() {
		return Location;
	}
	
	public void setLocation(Location location) {
		Location = location;
	}

	public List<ArmorStand> getArmorStand() {
		return ArmorStand;
	}

	public void setArmorStand(List<ArmorStand> armorStand) {
		ArmorStand = armorStand;
	}

	public HashMap<Integer, Line> getLines() {
		return Lines;
	}

	public void setLines(HashMap<Integer, Line> lines) {
		Lines = lines;
	}
	
	private ItemStack getSkull(String url, String name) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        if(url.isEmpty())return item;


        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return item;
    }
	
	private ItemStack criar(String nomeDoItem, Material tipoItem) {
		ItemStack item = new ItemStack(tipoItem);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(nomeDoItem);
		item.setItemMeta(itemMeta);
		return item;
	}
}
