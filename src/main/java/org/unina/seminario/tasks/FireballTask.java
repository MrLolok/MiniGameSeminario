package org.unina.seminario.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.unina.seminario.game.TargetShootingGame;

import java.util.List;

public class FireballTask implements Runnable {
    private final TargetShootingGame game;

    public FireballTask(TargetShootingGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        List<Player> players = game.getScoreboard().keySet().stream().map(Bukkit::getPlayer).toList();
        for (Player player : players) {
            Fireball fireball = player.getWorld().spawn(game.getTarget().getLocation(), Fireball.class);
            fireball.setDirection(player.getLocation().toVector().subtract(game.getTarget().getLocation().toVector()).normalize());
        }
    }
}
