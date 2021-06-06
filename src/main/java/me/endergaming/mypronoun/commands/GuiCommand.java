package me.endergaming.mypronoun.commands;

import me.endergaming.enderlibs.command.SubCommand;
import me.endergaming.mypronoun.MyPronoun;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class GuiCommand extends SubCommand {
    public GuiCommand(@NotNull JavaPlugin plugin, String command) {
        super(plugin, command);
    }

    @Override
    public void run(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.openInventory(JavaPlugin.getPlugin(MyPronoun.class).getGuiManager().getGUI(player.getUniqueId()));
    }
}
