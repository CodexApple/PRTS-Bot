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

    public ReadyListener(Suletta suletta) {
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
