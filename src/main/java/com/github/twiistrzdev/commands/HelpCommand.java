/*
 * Copyright 2023 Emmanuel See Te
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.twiistrzdev.commands;

import com.github.twiistrzdev.Command;
import com.github.twiistrzdev.CommandManager;
import com.github.twiistrzdev.Suletta;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class HelpCommand implements Command {
    private final CommandManager commandManager;

    public HelpCommand(Suletta suletta) {
        commandManager = suletta.getCommandManager();
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String description() {
        return "provides help on available commands";
    }

    @Override
    public List<OptionData> optionData() {
        return null;
    }

    @Override
    public void run(@NotNull SlashCommandInteractionEvent event) {
        event.deferReply().queue();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        StringBuilder helpCommand = new StringBuilder();

        embedBuilder.setAuthor("help", "https://www.facebook.com/izushiki/", event.getUser().getAvatarUrl());
        embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setColor(new Color(182, 51, 59));

        for (Command command : commandManager.getAll()) {
            helpCommand.append(command.name());
            helpCommand.append(" - ");
            helpCommand.append(command.description());
        }

        embedBuilder.addField("Commands", helpCommand.toString(), true);

        event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
