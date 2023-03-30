package com.github.twiistrzdev.commands;

import com.github.twiistrzdev.Command;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandHelp implements Command {
    @Override
    public String name() {
        return "help";
    }

    @Override
    public String description() {
        return "Help command";
    }

    @Override
    public List<OptionData> optionData() {
        return null;
    }

    @Override
    public void run(@NotNull SlashCommandInteractionEvent event) {
        event.reply("What?").queue();
    }
}
