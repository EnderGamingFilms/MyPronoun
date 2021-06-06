package me.endergaming.mypronoun.inventorygui;

import me.endergaming.mypronoun.MyPronoun;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class SelectionGUIListener implements Listener {
    private final MyPronoun plugin;

    public SelectionGUIListener(@NotNull final MyPronoun instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        // Check GUI inventory name & if clicked outside
        if (event.getClickedInventory() != null && event.getView().getTitle().contains(SelectionGUI.inventory_name)) {
            // If the GUI is the Selection GUI then cancel any click events
            event.setCancelled(true);
            // Check if clicked item == null
            ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
            if (clickedItem == null || clickedItem.getItemMeta() == null) return;
            // Check if item has persistent data
            PersistentDataContainer container = clickedItem.getItemMeta().getPersistentDataContainer();
            if (!container.has(plugin.getPronounKey(), PersistentDataType.INTEGER)) return;
            int pronounID = container.get(plugin.getPronounKey(), PersistentDataType.INTEGER) == null ? 3 : container.get(plugin.getPronounKey(), PersistentDataType.INTEGER);
            plugin.getStorageHelper().setPronoun(event.getWhoClicked().getUniqueId(), pronounID);
            // TODO: Send success messages
            // Close inventory
            event.getWhoClicked().closeInventory();
        }
    }
}
