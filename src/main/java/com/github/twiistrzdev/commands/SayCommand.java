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
import com.github.twiistrzdev.Suletta;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SayCommand implements Command {
    public SayCommand(Suletta suletta) { }

    @Override
    public String name() {
        return "say";
    }

    @Override
    public String description() {
        return "let the bot say something for you";
    }

    @Override
    public List<OptionData> optionData() {
        List<OptionData> optionData = new ArrayList<>();
        optionData.add(new OptionData(OptionType.CHANNEL, "channel", "channel where you want to send the message", true).setChannelTypes(ChannelType.TEXT));
        optionData.add(new OptionData(OptionType.STRING, "message", "the message you want to send", true));
        return optionData;
    }

    @Override
    public void run(@NotNull SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        OptionMapping messageOption = event.getOption("message");
        assert messageOption != null;
        String message = messageOption.getAsString();

        OptionMapping channelOption = event.getOption("channel");
        assert channelOption != null;
        TextChannel channel = channelOption.getAsChannel().asTextChannel();

        channel.sendMessage(message).queue();

        event.getHook().sendMessage("Message Sent!").queue();
    }
}
