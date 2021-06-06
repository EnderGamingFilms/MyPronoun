package me.endergaming.mypronoun.controllers;

//import me.clip.placeholderapi.PlaceholderAPI;
import me.endergaming.mypronoun.MyPronoun;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class ResponseController {
    private final MyPronoun plugin;
    private FileConfiguration config;
    public static String FRIENDLY_FIRE;
    public static String BROADCAST_GAME_START;
    public static String BROADCAST_GAME_OVER;
    public static String BROADCAST_NEXT_ROUND;
    public static String BROADCAST_STARING;
    public static String BROADCAST_RETURNING_TO_LOBBY;
    public static String BROADCAST_TP_LOBBY_WARMUP;
    public static String BROADCAST_OBJECTIVE_FOUND;
    public static String BROADCAST_BONUS_OBJECTIVE_FOUND;
    public static String BROADCAST_TIME_REMAINING;
    public static String NEW_OBJECTIVE;
    public static String FOUND_ALL_OBJECTIVES;


    public ResponseController(@NotNull final MyPronoun instance) {
        this.plugin = instance;
    }

    public static String replace(String message, int value) {
        message = message.replaceAll("%delay%", String.valueOf(value));
        return message;
    }

    public static String replace(String message, String value) {
        message = message.replaceAll("%objective%", value);
        return message;
    }

    public static String replace(String message, int time, boolean timer) {
        int numberOfMinutes = ((time % 86400) % 3600) / 60;
        if (numberOfMinutes > 1) {
            message = message.replaceAll("%time%", String.valueOf(numberOfMinutes));
            message = message.replaceAll("%type%", "minute(s)");
        } else {
            message = message.replaceAll("%time%", String.valueOf(time));
            message = message.replaceAll("%type%", "second(s)");
        }
        return message;
    }

    public void init() {
        config = plugin.getFileManager().getConfig("messages.yml");
        // Parse teams
        this.setMessages();
    }

    public void setMessages() {
        FRIENDLY_FIRE = this.config.getString("friendly-fire");
        BROADCAST_GAME_START = this.config.getString("broadcast-game-start");
        BROADCAST_GAME_OVER = this.config.getString("broadcast-game-over");
        BROADCAST_RETURNING_TO_LOBBY = this.config.getString("broadcast-returning-to-lobby");
        BROADCAST_NEXT_ROUND = this.config.getString("broadcast-next-round");
        BROADCAST_STARING = this.config.getString("broadcast-starting");
        BROADCAST_TP_LOBBY_WARMUP = this.config.getString("broadcast-lobby-tp-warmup");
        BROADCAST_OBJECTIVE_FOUND = this.config.getString("broadcast-objective-found");
        BROADCAST_BONUS_OBJECTIVE_FOUND = this.config.getString("broadcast-bonus-objective-found");
        BROADCAST_TIME_REMAINING = this.config.getString("broadcast-time-remaining");
        NEW_OBJECTIVE = this.config.getString("new-objective");
        FOUND_ALL_OBJECTIVES = this.config.getString("found-all-objectives");
    }
}
