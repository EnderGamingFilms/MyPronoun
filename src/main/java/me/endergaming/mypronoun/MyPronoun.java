package me.endergaming.mypronoun;

import me.endergaming.enderlibs.event.ListenerManager;
import me.endergaming.enderlibs.file.FileManager;
import me.endergaming.mypronoun.commands.CommandRegistry;
import me.endergaming.mypronoun.controllers.ConfigController;
import me.endergaming.mypronoun.controllers.ResponseController;
import me.endergaming.mypronoun.inventorygui.GuiManager;
import me.endergaming.mypronoun.listeners.OnPlayerJoin;
import me.endergaming.mypronoun.storage.StorageHelper;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyPronoun extends JavaPlugin {
    private final CommandRegistry commandRegistry = new CommandRegistry(this);
    private FileManager fileManager = new FileManager(this);
    private ConfigController configController = new ConfigController(this);
    private ResponseController responseController = new ResponseController(this);
    private StorageHelper storageHelper = new StorageHelper(this);
    private ListenerManager listenerManager = new ListenerManager(this);
    private GuiManager guiManager = new GuiManager(this);

    public final NamespacedKey pronounKey = new NamespacedKey(this, "pronounDat");

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register Commands
        if (!Bukkit.getPluginManager().isPluginEnabled("EnderLibs")) {
            System.out.println("EnderLibs is not enabled!");
        } else {
            commandRegistry.register();
        }
        // Setup Files
        try {
            fileManager.registerConfig("config.yml");
            configController.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Try to setup database
        System.out.println("--> Database Status (Before): " + storageHelper.isConnected());
        storageHelper.init();
        System.out.println("--> Database Status: " + storageHelper.isConnected());
        // Register listeners
        listenerManager.register(new OnPlayerJoin(this));
        // Initializing GUI
        guiManager.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        storageHelper.getSql().close();
        listenerManager.unregisterAll();
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public ConfigController getConfigController() {
        return configController;
    }

    public StorageHelper getStorageHelper() {
        return storageHelper;
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public NamespacedKey getPronounKey() {
        return pronounKey;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
