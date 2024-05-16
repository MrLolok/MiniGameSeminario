package org.unina.seminario.game;

import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class TargetShootingService {
    private final List<TargetShootingGame> games = new LinkedList<>();

    public TargetShootingGame create() {
        TargetShootingGame game = new TargetShootingGame();
        games.add(game);
        return game;
    }

    public List<TargetShootingGame> getGames() {
        return games;
    }

    public TargetShootingGame getGame(Player player) {
        for (TargetShootingGame game : games)
            if (game.isPlaying(player))
                return game;
        return null;
    }
}
