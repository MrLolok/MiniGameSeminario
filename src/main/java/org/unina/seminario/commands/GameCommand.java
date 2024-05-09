package org.unina.seminario.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.unina.seminario.MiniGamePlugin;
import org.unina.seminario.game.TargetShootingGame;
import org.unina.seminario.inventory.GameListInventory;

public class GameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§eUsa il comando /game create o /game list");
            return true;
        }
        switch (args[0].toUpperCase()) {
            case "CREATE":
                if (sender instanceof Player player) {
                    if (MiniGamePlugin.getInstance().getService().getGame(player) != null) {
                        player.sendMessage("§dSei già all'interno di un game!!!");
                        break;
                    }

                    // crea un nuovo game
                    TargetShootingGame game = MiniGamePlugin.getInstance().getService().create();
                    sender.sendMessage("§aPartita creata con successo!");
                    game.addPlayer(player);
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
                    game.start();
                }
                break;
            case "LIST":
                sender.sendMessage("§aCaricamento delle partite in corso...");
                if (sender instanceof Player player) {
                    GameListInventory inventory = new GameListInventory(MiniGamePlugin.getInstance());
                    player.openInventory(inventory.getInventory());
                }
                break;
            case "STOP":
                if (sender instanceof Player player) {
                    if (MiniGamePlugin.getInstance().getService().getGame(player) != null)
                        MiniGamePlugin.getInstance().getService().getGame(player).stop();
                }
                break;
            default:
                sender.sendMessage("§cHai sbagliato comando!");
                break;
        }
        return true;
    }
}
