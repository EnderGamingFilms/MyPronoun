package me.endergaming.mypronoun.commands;

import me.endergaming.enderlibs.command.SubCommand;
import me.endergaming.enderlibs.text.MessageUtils;
import me.endergaming.mypronoun.MyPronoun;
import me.endergaming.mypronoun.controllers.ResponseController;
import me.endergaming.mypronoun.storage.Pronoun;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import static me.endergaming.enderlibs.file.config.CoreMessages.INVALID_PLAYER;
import static me.endergaming.mypronoun.controllers.ResponseController.*;

public class GetPronoun extends SubCommand {
    public GetPronoun(@NotNull JavaPlugin plugin, String command) {
        super(plugin, command);
    }

    @Override
    public void run(CommandSender sender, Command cmd, String label, String[] args) {
        MyPronoun plugin = JavaPlugin.getPlugin(MyPronoun.class);
        Player player = (Player) sender;
        if (args.length == 1) {
            MessageUtils.send(player, NO_PLAYER);
        } else if (args.length > 1) {
            Player player1 = Bukkit.getPlayer(args[1]);
            if (player1 == null) {
                MessageUtils.send(player, INVALID_PLAYER);
                return;
            }
            int pronounID = plugin.getStorageHelper().getPronounID(player.getUniqueId());
            if (pronounID == -1) {
                MessageUtils.send(player, OTHER_NOT_SELECTED);
            } else {
                Pronoun pronoun = plugin.getConfigController().getPronounByID(pronounID);
                if (pronoun == null) {
                    MessageUtils.send(player, GET_ERROR);
                    return;
                }
                MessageUtils.send(player, replace(GET_COMMAND, player1));
            }
        }
    }
}
