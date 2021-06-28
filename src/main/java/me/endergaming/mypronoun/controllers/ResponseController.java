package me.endergaming.mypronoun.controllers;

import me.endergaming.mypronoun.MyPronoun;
import me.endergaming.mypronoun.storage.Pronoun;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ResponseController {
    private final MyPronoun plugin;
    private FileConfiguration config;
    public static String PRONOUN_CHANGED;
    public static String REOPEN_COMMAND;
    public static String NOT_SELECTED;
    public static String OTHER_NOT_SELECTED;
    public static String GET_COMMAND;
    public static String GET_ERROR;
    public static String NO_PLAYER;


    public ResponseController(@NotNull final MyPronoun instance) {
        this.plugin = instance;
    }

    public static String replace(String message, Player player) {
        Pronoun pronoun = JavaPlugin.getPlugin(MyPronoun.class).getConfigController().getPronounByID(JavaPlugin.getPlugin(MyPronoun.class).getStorageHelper().getPronounID(player.getUniqueId()));
        if (pronoun != null)
            message = message.replaceAll("%pronoun%", pronoun.getPronoun());
        message = message.replaceAll("%player%", player.getDisplayName());
        return message;
    }

    public static String replace(String message, int ID) {
        Pronoun pronoun = JavaPlugin.getPlugin(MyPronoun.class).getConfigController().getPronounByID(ID);
        if (pronoun != null)
            message = message.replaceAll("%pronoun%", pronoun.getPronoun());
        return message;
    }

    public void init() {
        config = plugin.getFileManager().getConfig("messages.yml");
        // Parse teams
        this.setMessages();
    }

    public void setMessages() {
        PRONOUN_CHANGED = this.config.getString("pronoun_changed");
        REOPEN_COMMAND = this.config.getString("reopen_command");
        NOT_SELECTED = this.config.getString("not_selected");
        OTHER_NOT_SELECTED = this.config.getString("other_not_selected");
        GET_COMMAND = this.config.getString("get_command");
        GET_ERROR = this.config.getString("get_error");
        NO_PLAYER = this.config.getString("no_player");
    }
}
