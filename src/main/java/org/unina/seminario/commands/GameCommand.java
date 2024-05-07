package org.unina.seminario.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.unina.seminario.MiniGamePlugin;
import org.unina.seminario.game.TargetShootingGame;

public class GameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§eUsa il comando /game create o /game list");
            return true;
        }
        switch (args[0].toUpperCase()) {
            case "CREATE":
                TargetShootingGame game = MiniGamePlugin.getInstance().getService().create();
                sender.sendMessage("§aPartita creata con successo!");
                if (sender instanceof Player player) {
                    game.addPlayer(player);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
                }
                game.start();
                break;
            case "LIST":
                sender.sendMessage("§aCaricamento delle partite in corso...");
                break;
            default:
                sender.sendMessage("§cHai sbagliato comando!");
                break;
        }
        return true;
    }
}
