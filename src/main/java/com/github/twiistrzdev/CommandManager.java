package com.github.twiistrzdev;

import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private final List<Command> commands;

    public CommandManager() {
        commands = new ArrayList<>();
    }

    public void add(@NotNull Command command) {
        commands.add(command);
    }

    public void add(@NotNull Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public List<Command> getAll() {
        return commands;
    }

    public Command getByName(String commandName) {
        for (Command command : commands) {
            if (command.name().equals(commandName)) {
                return command;
            }
        }
        return null;
    }
}
