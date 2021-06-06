package me.endergaming.mypronoun.commands;

import me.endergaming.enderlibs.command.CommandManager;
import me.endergaming.mypronoun.MyPronoun;
import org.jetbrains.annotations.NotNull;

public class CommandRegistry {
    private final MyPronoun plugin;
    protected CommandManager commandManager = new CommandManager();

    public CommandRegistry(@NotNull final MyPronoun instance) {
        this.plugin = instance;
    }

    public void register() {
        PronounCommand pronounCommand = (PronounCommand) new PronounCommand(plugin, "pronoun")
                .setAlias("pn")
                .setPlayerOnly(true)
                .setHasCommandArgs(true)
                .setUsage("&c/pn <gui/get> &f- &dThese are the commands.");

        commandManager.register(pronounCommand,
                new GetPronoun(plugin, "get"),
                new GuiCommand(plugin, "gui"));
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
