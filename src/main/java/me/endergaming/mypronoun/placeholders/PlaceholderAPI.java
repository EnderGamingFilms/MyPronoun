package me.endergaming.mypronoun.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.endergaming.mypronoun.MyPronoun;
import me.endergaming.mypronoun.storage.Pronoun;
import org.bukkit.entity.Player;

public class PlaceholderAPI extends PlaceholderExpansion {
    private final MyPronoun plugin;

    public PlaceholderAPI(final MyPronoun instance) {
        this.plugin = instance;
    }

    @Override
    public String getIdentifier() {
        return plugin.getName().toLowerCase();
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (params.equalsIgnoreCase("pronoun")) {
            int pronounID = plugin.getStorageHelper().getPronounID(player.getUniqueId());
            if (pronounID == -1) {
                return "&8Not Set";
            } else {
                Pronoun pronoun = plugin.getConfigController().getPronounByID(pronounID);
                if (pronoun == null) return "&cError (GP)";
                return pronoun.getColor() + pronoun.getPronoun();
            }
        }
        return null;
    }
}
