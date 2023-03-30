package com.github.twiistrzdev;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Command {
    String name();
    String description();
    List<OptionData> optionData();
    void run(@NotNull SlashCommandInteractionEvent event);
}
