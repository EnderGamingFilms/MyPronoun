package me.endergaming.mypronoun.inventorygui;

import me.endergaming.mypronoun.MyPronoun;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GuiManager {
    private SelectionGUI gui;
    private final MyPronoun plugin;

    public GuiManager(@NotNull final MyPronoun instance) {
        this.plugin = instance;
    }

    public void init() {
        this.gui = new SelectionGUI(plugin);
        plugin.getListenerManager().register(new SelectionGUIListener(plugin));
    }

    public Inventory getGUI(UUID uuid) {
        return this.gui.GUI(plugin.getStorageHelper().getPronounID(uuid));
    }

    public Inventory getGUI(int activeID) {
        return this.gui.GUI(activeID);
    }
}
