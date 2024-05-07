package org.unina.seminario;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.unina.seminario.commands.GameCommand;
import org.unina.seminario.game.TargetShootingService;
import org.unina.seminario.listeners.TargetShootingListener;

public class MiniGamePlugin extends JavaPlugin {
    private static MiniGamePlugin instance;
    private TargetShootingService service;

    public static MiniGamePlugin getInstance() {
        return instance;
    }

    public TargetShootingService getService() {
        return service;
    }

    @Override
    public void onEnable() {
        instance = this;
        service = new TargetShootingService();
        GameCommand gameCommand = new GameCommand();
        this.getCommand("game").setExecutor(gameCommand);
        this.getServer().getPluginManager().registerEvents(new TargetShootingListener(), this);
        Bukkit.getConsoleSender().sendMessage("§aHello World!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§ePlugin spento correttamente!");
    }
}
