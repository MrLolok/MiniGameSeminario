package org.unina.seminario.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.unina.seminario.MiniGamePlugin;
import org.unina.seminario.items.ItemBuilder;
import org.unina.seminario.tasks.FireballTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TargetShootingGame {
    private final Map<UUID, Integer> scoreboard = new HashMap<>();
    private Entity target;
    private boolean started = false;

    public Map<UUID, Integer> getScoreboard() {
        return scoreboard;
    }

    public Entity getTarget() {
        return target;
    }

    public boolean isPlaying(Player player) {
        return scoreboard.containsKey(player.getUniqueId());
    }

    public void addPlayer(Player player) {
        if (isPlaying(player)) return;
        scoreboard.put(player.getUniqueId(), 0);
    }

    public void removePlayer(Player player) {
        scoreboard.remove(player.getUniqueId());
    }

    public int getScore(Player player) {
        if (!isPlaying(player))
            throw new IllegalStateException("Il giocatore non è in partita!");
        return scoreboard.get(player.getUniqueId());
    }

    public void setScore(Player player, int score) {
        if (!isPlaying(player))
            throw new IllegalStateException("Il giocatore non è in partita!");
        scoreboard.replace(player.getUniqueId(), score);
    }

    public void start() {
        started = true;
        this.target = getZombieTarget();
        Bukkit.getScheduler().runTaskTimer(MiniGamePlugin.getInstance(), new FireballTask(this), 20, 100);
        for (UUID uuid : scoreboard.keySet()) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            ItemStack bow = getBow();
            ItemStack arrow = new ItemStack(Material.ARROW);
            player.getInventory().clear();
            player.getInventory().addItem(bow, arrow);
            player.sendTitle("PARTITA AVVIATA", "Spara allo zombie", 20, 60, 20);
        }
    }

    public void stop() {
        started = false;
        if (target == null)
            target.remove();
        scoreboard.clear();
    }

    private ItemStack getBow() {
        return new ItemBuilder()
                .setMaterial(Material.BOW)
                .setDisplayName("§a§lArco")
                .setUnbreakable(true)
                .setLore(List.of("§7Arco super potente"))
                .setEnchantments(Map.of(Enchantment.ARROW_INFINITE, 1))
                .build();
    }

    private Entity getZombieTarget() {
        if (scoreboard.isEmpty()) return null;
        Player player = Bukkit.getPlayer(scoreboard.keySet().iterator().next());
        Location location = player.getLocation();
        Entity entity = location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        Zombie zombie = (Zombie) entity;
        zombie.setAI(false);
        zombie.setCustomName("§6§lTARGET");
        zombie.setCustomNameVisible(true);
        return entity;
    }
}
