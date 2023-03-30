package com.github.twiistrzdev.listeners;

import com.github.twiistrzdev.Command;
import com.github.twiistrzdev.CommandManager;
import com.github.twiistrzdev.Suletta;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SlashCommandInteractionListener extends ListenerAdapter {
    private final CommandManager commandManager;

    public SlashCommandInteractionListener(Suletta suletta) {
        commandManager = suletta.getCommandManager();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Command command = commandManager.getByName(event.getName());
        command.run(event);
    }
}
