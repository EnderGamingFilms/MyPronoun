package me.endergaming.mypronoun.commands;

import me.endergaming.enderlibs.command.SubCommand;
import me.endergaming.enderlibs.text.MessageUtils;
import me.endergaming.mypronoun.MyPronoun;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class GetPronoun extends SubCommand {
    public GetPronoun(@NotNull JavaPlugin plugin, String command) {
        super(plugin, command);
    }

    @Override
    public void run(CommandSender sender, Command cmd, String label, String[] args) {
        // TODO: Do get command for other players
        System.out.println("--> get pronoun");
        Player player = (Player) sender;
        MessageUtils.send(player, "&6" +  JavaPlugin.getPlugin(MyPronoun.class).getStorageHelper().getPronounID(player.getUniqueId()));
    }
}
