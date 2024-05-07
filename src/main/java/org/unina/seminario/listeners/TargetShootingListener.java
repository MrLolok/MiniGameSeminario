package org.unina.seminario.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.unina.seminario.MiniGamePlugin;
import org.unina.seminario.game.TargetShootingGame;

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
}
