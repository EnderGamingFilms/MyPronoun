package me.endergaming.mypronoun.listeners;

import me.endergaming.mypronoun.MyPronoun;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class OnPlayerJoin implements Listener {
    private final MyPronoun plugin;

    public OnPlayerJoin(@NotNull final MyPronoun instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Check if player is in the database
        if (!plugin.getStorageHelper().playerExists(event.getPlayer().getUniqueId())) {
            // If not prompt user to select pronoun from GUI
            System.out.println("--> player does not exist in database");
            plugin.getStorageHelper().createPlayer(event.getPlayer().getUniqueId());
            System.out.println("--> Here is when the GUI would open...");
            event.getPlayer().openInventory(plugin.getGuiManager().getGUI(-1));
        }
    }
}
