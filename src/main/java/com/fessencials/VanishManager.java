package com.fessencials;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class VanishManager {

    private ArrayList<String> playersVanish = new ArrayList<>();
    private JavaPlugin plugin;

    public VanishManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public List<String> getPlayers() {
        return this.playersVanish;
    }

    public void addPlayer(Player player) {
        this.playersVanish.add(player.getName());
        this.sendActionBar(player);
    }

    public void removePlayer(String name) {
        this.playersVanish.remove(name);
    }

    public boolean containsPlayer(String name) {
        return this.playersVanish.contains(name);
    }

    private void sendActionBar(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (containsPlayer(p.getName())){

                    PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + "§7Vanish ativo" + "\"}"), (byte) 2);
                    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);

                }else
                    cancel();
            }
        }.runTaskTimer(plugin, 1L, 20L);
    }
}
