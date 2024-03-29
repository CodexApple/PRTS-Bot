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

package com.github.twiistrzdev;

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
