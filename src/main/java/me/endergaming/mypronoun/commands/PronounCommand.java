package me.endergaming.mypronoun.commands;

import me.endergaming.enderlibs.command.MainCommand;
import me.endergaming.enderlibs.command.SubCommand;
import me.endergaming.enderlibs.text.MessageUtils;
import me.endergaming.mypronoun.MyPronoun;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static me.endergaming.enderlibs.file.config.CoreMessages.INVALID_PERMISSION;
import static me.endergaming.enderlibs.file.config.CoreMessages.NON_PLAYER;

public class PronounCommand extends MainCommand {
    public PronounCommand(@NotNull JavaPlugin plugin, String command) {
        super(plugin, command);
    }

    @Override
    public void run(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.openInventory(JavaPlugin.getPlugin(MyPronoun.class).getGuiManager().getGUI(player.getUniqueId()));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Do permission check
        if (!sender.hasPermission(permission)) {
            MessageUtils.send(sender, INVALID_PERMISSION);
            return true;
        }
        // Player Check
        if (playerOnly) {
            if (!(sender instanceof Player)) {
                MessageUtils.send(sender, NON_PLAYER);
                return true;
            }
        }
        // Argument Check
        if (hasCommandArgs) {
            if (args.length == 0) {
                // Run Main Logic
                this.run(sender, cmd, label, args);
                return true;
            }
            if (!subCommandMap.containsKey(args[0])) {
                MessageUtils.send(sender, this);
            } else {
                subCommandMap.get(args[0]).run(sender, cmd, label, args);
            }
            return true;
        }
        // Run() w/o Args
        this.run(sender, cmd, label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            return subCommandMap.keySet().stream()
                    .map(String::toLowerCase)
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
