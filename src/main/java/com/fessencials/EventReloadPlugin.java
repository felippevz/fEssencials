package com.fessencials;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.logging.Level;

public class EventReloadPlugin {

    private JavaPlugin plugin;
    private final Map<String, Long> timeSinceLastChanged;
    private final HashMap<String, String> fileToPluginName;
    String pluginsFolder;

    public EventReloadPlugin(JavaPlugin plugin) {

        this.plugin = plugin;
        this.timeSinceLastChanged = new HashMap<String, Long>();
        this.fileToPluginName = new HashMap<String, String>();
        this.pluginsFolder = this.plugin.getDataFolder().getAbsolutePath();
        this.pluginsFolder = this.pluginsFolder.replace(plugin.getName(), "");
        this.getPlugins();
        this.logTimes();
        this.loop();
    }

    private void loop() {
        new BukkitRunnable() {

            public void run() {
                checkIfModified();
            }
        }.runTaskTimerAsynchronously(plugin, 1L, 20L);
    }

    void getPlugins() {

        final Plugin[] plugins = Bukkit.getServer().getPluginManager().getPlugins();

        for (int i = 0; i < plugins.length; ++i) {

            final Plugin plugin = plugins[i];
            String location = plugin.getClass().getProtectionDomain().getCodeSource().getLocation() + "";
            final String[] temp = location.split("/");
            location = temp[temp.length - 1];

            try {

                location = URLDecoder.decode(location, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                plugin.getLogger().log(Level.SEVERE, "JAVA NAO SUPORTA UTF 8");
                return;
            }

            this.fileToPluginName.put(location, plugin.getName());
        }
    }

    void checkIfModified() {

        final File folder = new File(this.pluginsFolder);
        final File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; ++i) {

            final String fileName = listOfFiles[i].getName();

            if (listOfFiles[i].isFile() && fileName.endsWith(".jar") && this.timeSinceLastChanged.containsKey(fileName)) {

                final long time = this.timeSinceLastChanged.get(fileName);

                if (time < listOfFiles[i].lastModified()) {

                    final String pluginName = this.fileToPluginName.get(fileName);

                    if (pluginName != null) {

                        if(pluginName.equalsIgnoreCase(plugin.getName()))
                            return;

                        Bukkit.getScheduler().callSyncMethod(plugin, () -> Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "plugman reload " + pluginName));

                        for(Player p : Bukkit.getOnlinePlayers())
                            if(p.isOp()) {
                                p.sendMessage("§aPlugin " + pluginName + " atualizado com sucesso!");
                                p.playSound(p.getLocation(), Sound.valueOf("LEVEL_UP"), 1F, 2F);
                            }

                        this.timeSinceLastChanged.remove(fileName);
                        this.timeSinceLastChanged.put(fileName, listOfFiles[i].lastModified());
                    }
                }
            }
        }
    }

    void logTimes() {
        final File folder = new File(this.pluginsFolder);
        final File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; ++i)
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(".jar"))
                this.timeSinceLastChanged.put(listOfFiles[i].getName(), listOfFiles[i].lastModified());
    }
}
