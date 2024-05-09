package org.unina.seminario.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.unina.seminario.MiniGamePlugin;
import org.unina.seminario.game.TargetShootingGame;
import org.unina.seminario.inventory.GameListInventory;

public class TargetShootingListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Zombie zombie && event.getDamager() instanceof Arrow arrow && arrow.getShooter() instanceof Player player) {
            TargetShootingGame game = MiniGamePlugin.getInstance().getService().getGame(player);
            if (game == null) return;
            event.setDamage(0D);
            game.setScore(player, game.getScore(player) + 1);
            player.sendMessage("§aBersaglio colpito! §f" + game.getScore(player));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        // Check if the holder is our MyInventory,
        // if yes, use instanceof pattern matching to store it in a variable immediately.
        if (!(inventory.getHolder() instanceof GameListInventory myInventory)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        System.out.println(player.getName() + " has left the game");
        if (MiniGamePlugin.getInstance().getService().getGame(player) != null)
            MiniGamePlugin.getInstance().getService().getGame(player).removePlayer(player);
    }
}
