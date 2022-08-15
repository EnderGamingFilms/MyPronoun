package me.endergaming.mypronoun.controllers;

import me.endergaming.enderlibs.file.FileUtils;
import me.endergaming.mypronoun.MyPronoun;
import me.endergaming.mypronoun.storage.Pronoun;
import me.endergaming.mypronoun.storage.StorageType;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConfigController {
    private final MyPronoun plugin;
    private FileConfiguration config;

    // Config Settings
    protected List<Pronoun> pronouns = new ArrayList<>();
    protected StorageType storageType;
    public static boolean debug;

    public ConfigController(@NotNull final MyPronoun instance) {
        this.plugin = instance;
    }

    public void init() {
        config = FileUtils.getConfig("config", "yml");
        this.readConfig();
    }

    public void readConfig() {
        debug = config.getBoolean("debug");
        storageType = StorageType.isValid(config.getString("Storage")) ? StorageType.valueOf(config.getString("Storage")) : StorageType.SQLITE;

        if (debug) System.out.println("--> StorageType: " + storageType);

        ConfigurationSection pronounConfig = config.getConfigurationSection("pronouns");

        for (String key : pronounConfig.getKeys(false)) {
            String pronoun = pronounConfig.getString(key + ".pronoun");
            Material display = Material.getMaterial(pronounConfig.getString(key + ".display") == null ? "STONE" : pronounConfig.getString(key + ".display"));
            String color = pronounConfig.getString(key + ".color") == null || pronounConfig.getString(key + ".color").equals("") ? "&7" : pronounConfig.getString(key + ".color");
            int pos = pronounConfig.getInt(key + ".position");
            Pronoun newPronoun = new Pronoun(Integer.parseInt(key), pronoun == null ? "ERROR" : pronoun, display == null ? Material.STONE : display, color, pos);
            pronouns.add(newPronoun);
            if (debug) System.out.println("--> Pronoun: " + newPronoun);
        }
    }

    @Nullable
    public Pronoun getPronounByID(int ID) {
        return pronouns.stream().filter(pronoun -> pronoun.getId() == ID).findFirst().orElse(null);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public List<Pronoun> getPronouns() {
        return pronouns;
    }
}
