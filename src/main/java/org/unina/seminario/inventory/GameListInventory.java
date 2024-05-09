package org.unina.seminario.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.unina.seminario.MiniGamePlugin;
import org.unina.seminario.game.TargetShootingGame;
import org.unina.seminario.items.ItemBuilder;

import java.util.List;

public class GameListInventory implements InventoryHolder {

    private final Inventory inventory;

    public GameListInventory(MiniGamePlugin plugin) {
        this.inventory = plugin.getServer().createInventory(this, 27, "Game List");

        ItemBuilder gameItemBuilder = new ItemBuilder().setAmount(1).setMaterial(Material.GREEN_WOOL);

        for(TargetShootingGame game: plugin.getService().getGames()) {
            ItemStack gameItem = gameItemBuilder
                    .setDisplayName("Game #"  + game.hashCode())
                    .setLore(List.of("Giocatori connessi: " + game.getScoreboard().size()))
                    .build();
            this.inventory.addItem(gameItem);
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
