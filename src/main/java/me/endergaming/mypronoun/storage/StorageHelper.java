package me.endergaming.mypronoun.storage;

import me.endergaming.enderlibs.sql.SQLCache;
import me.endergaming.enderlibs.sql.SQLHelper;
import me.endergaming.enderlibs.text.MessageUtils;
import me.endergaming.mypronoun.MyPronoun;
import me.endergaming.mypronoun.controllers.ConfigController;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.UUID;

public class StorageHelper {
    private final MyPronoun plugin;
    protected SQLHelper sql;
    protected SQLCache playerCache;

    public StorageHelper(@NotNull final MyPronoun instance) {
        this.plugin = instance;
    }

    public void init() {
        if (plugin.getConfigController().getStorageType() == StorageType.SQLITE) {
            // SQLITE Setup
            File liteDB = new File(plugin.getDataFolder(), "storage.db");
            sql = new SQLHelper(SQLHelper.openSQLite(liteDB.toPath()));
        } else {
            // MYSQL Setup
            ConfigurationSection config = plugin.getConfigController().getConfig().getConfigurationSection("MySQL");

            if (config == null) {
                MessageUtils.log(MessageUtils.LogLevel.SEVERE, "There was an issue setting up MySQL");
                return;
            }

            String host = config.getString("Host");
            int port = config.getInt("Port");
            String db = config.getString("Database");
            String user = config.getString("Username");
            String pass = config.getString("Password");
            boolean useSSL = config.getBoolean("useSSL");
            if (ConfigController.debug) {
                sql = new SQLHelper(SQLHelper.openMySQL(user, pass, db)); // localhost database
            } else {
                sql = new SQLHelper(SQLHelper.openMySQL(host, port, user, pass, db, useSSL)); // normal database
            }
        }

        createTable();

        playerCache = createCache();

        sql.setCommitInterval(plugin.getConfigController().getConfig().getInt("Update") == 0 ? -1 : plugin.getConfigController().getConfig().getInt("Update"));

        if (isConnected()) {
            MessageUtils.log(MessageUtils.LogLevel.INFO, "&aDatabase connected");
        } else {
            MessageUtils.log(MessageUtils.LogLevel.SEVERE, "&cFailed to connect to storage database");
        }
    }

    public boolean isConnected() {
        return sql != null && sql.getConnection() != null;
    }

    public SQLHelper getSql() {
        return sql;
    }

    public void createTable() {
        sql.execute("CREATE TABLE IF NOT EXISTS my_pronoun (uuid VARCHAR(36), pronoun_id INTEGER, PRIMARY KEY (uuid))");
    }

    public void createPlayer(UUID uuid) {
        if (!playerExists(uuid)) {
            // Commit cache, add player, then create new cache
            sql.execute("INSERT OR IGNORE INTO my_pronoun (uuid,pronoun_id) VALUES (?,?)", uuid, String.valueOf(-1));
            sql.commit();
            playerCache = createCache();
        }
    }

    public boolean playerExists(UUID uuid) {
        return !(playerCache.<Integer>select(uuid) == null);
//        return !sql.queryResults("SELECT * FROM my_pronoun WHERE uuid=?", uuid).isEmpty();
    }

    public void setPronoun(UUID uuid, int pronounID) {
        if (!playerExists(uuid)) {
            createPlayer(uuid);
        }
        playerCache.update(pronounID, uuid);
//        sql.execute("UPDATE my_pronoun SET pronoun_id=? WHERE uuid=?", pronounID, uuid);
    }

    public int getPronounID(UUID uuid) {
        if (!playerExists(uuid)) {
            createPlayer(uuid);
        }
        Object mem = playerCache.select(uuid);
        if (mem == null) {
            return -1;
        }
        return playerCache.<Integer>select(uuid);
//        return sql.querySingleResultLong("SELECT pronoun_id FROM my_pronoun WHERE uuid=?", uuid).intValue();
    }

    public void clearTable() {
        sql.execute("TRUNCATE TABLE my_pronoun");
    }

    public SQLCache getPlayerCache() {
        return playerCache;
    }

    private SQLCache createCache() {
        return sql.createCache("my_pronoun", "pronoun_id", "uuid");
    }
}
