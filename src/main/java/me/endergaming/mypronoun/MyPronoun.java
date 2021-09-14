package me.endergaming.mypronoun;

import me.endergaming.enderlibs.event.ListenerManager;
import me.endergaming.enderlibs.file.FileManager;
import me.endergaming.enderlibs.text.MessageUtils;
import me.endergaming.mypronoun.commands.CommandRegistry;
import me.endergaming.mypronoun.controllers.ConfigController;
import me.endergaming.mypronoun.controllers.ResponseController;
import me.endergaming.mypronoun.inventorygui.GuiManager;
import me.endergaming.mypronoun.listeners.OnPlayerJoin;
import me.endergaming.mypronoun.placeholders.PlaceholderAPI;
import me.endergaming.mypronoun.storage.StorageHelper;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class MyPronoun extends JavaPlugin {
    private final CommandRegistry commandRegistry = new CommandRegistry(this);
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
            log(MessageUtils.LogLevel.SEVERE, "EnderLibs is not enabled!");
        } else {
            log(MessageUtils.LogLevel.INFO, "&dRegistering commands &f(1/4)");
            commandRegistry.register();
        }
        // Setup Files
        try {
            configController.init();
            responseController.init();
            log(MessageUtils.LogLevel.INFO, "&dSetting up configs &f(2/4)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Try to setup database
        storageHelper.init();
        // Register listeners
        listenerManager.register(new OnPlayerJoin(this));
        // Initializing GUI
        guiManager.init();
        log(MessageUtils.LogLevel.INFO, "&dConfiguring GUI &f(3/4)");
        // Register Placeholder
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPI(this).register();
            log(MessageUtils.LogLevel.INFO, "&dEnabling PlaceholderAPI &f(4/4)");
        } else {
            log(MessageUtils.LogLevel.WARNING, "&cPlaceholderAPI could not be enabled! &f(4/4)");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        storageHelper.getSql().commit();
        storageHelper.getSql().close();
        listenerManager.unregisterAll();
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

    public ResponseController getResponseController() {
        return responseController;
    }

    public static void log(MessageUtils.LogLevel logLevel, String message) {
        MessageUtils.log(logLevel, message, "&b" + JavaPlugin.getPlugin(MyPronoun.class).getName() + " ");
    }
}
