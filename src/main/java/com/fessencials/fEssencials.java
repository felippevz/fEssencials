package com.fessencials;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class fEssencials extends JavaPlugin {

    @Override
    public void onEnable() {

        VanishManager vanishManager = new VanishManager(this);

        getCommand("gm").setExecutor(new CmdGm());
        getCommand("vanish").setExecutor(new CmdVanish(this, vanishManager));
        getCommand("fly").setExecutor(new CmdFly());
        getCommand("flyspeed").setExecutor(new CmdFlySpeed());
        getCommand("time").setExecutor(new CmdTime(this));
        getCommand("ping").setExecutor(new CmdPing());

        getServer().getPluginManager().registerEvents(new EventTime(), this);
        getServer().getPluginManager().registerEvents(new EventVanish(vanishManager), this);
        getServer().getPluginManager().registerEvents(new EventFood(), this);

        new EventReloadPlugin(this);

        Bukkit.getConsoleSender().sendMessage("§6[fEssencials] §fPlugin §2ATIVADO §Fcom sucesso");
    }

    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage("§6[fEssencials] §fPlugin §4DESATIVADO §Fcom sucesso");
    }
}