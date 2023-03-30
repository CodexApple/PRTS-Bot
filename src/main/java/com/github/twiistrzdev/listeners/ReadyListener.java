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

package com.github.twiistrzdev.listeners;

import com.github.twiistrzdev.Command;
import com.github.twiistrzdev.CommandManager;
import com.github.twiistrzdev.Suletta;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {
    private final Dotenv dotenv;
    private final CommandManager commandManager;

    public ReadyListener(@NotNull Suletta suletta) {
        dotenv = suletta.getDotenv();
        commandManager = suletta.getCommandManager();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        if (dotenv.get("ENVIRONMENT").equals("PRODUCTION")) {
            JDA jda = event.getJDA();

            for (Command command : commandManager.getAll()) {
                if (command.optionData() == null) {
                    jda.upsertCommand(command.name(), command.description()).queue();
                } else {
                    jda.upsertCommand(command.name(), command.description()).addOptions(command.optionData()).queue();
                }
            }
        } else {
            Guild guild = event.getJDA().getGuildById(dotenv.get("DEVELOPMENT_GUILD_ID"));

            if (guild == null) return;

            for (Command command : commandManager.getAll()) {
                if (command.optionData() == null) {
                    guild.upsertCommand(command.name(), command.description()).queue();
                } else {
                    guild.upsertCommand(command.name(), command.description()).addOptions(command.optionData()).queue();
                }
            }
        }
    }
}
