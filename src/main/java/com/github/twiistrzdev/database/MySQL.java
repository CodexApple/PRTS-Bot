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

package com.github.twiistrzdev.database;

import com.github.twiistrzdev.Suletta;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
    private final String host;
    private final String port;
    private final String name;
    private final String username;
    private final String password;
    private Connection connection;

    public MySQL(@NotNull Suletta suletta) {
        host = suletta.getDotenv().get("MYSQL_HOST");
        port = suletta.getDotenv().get("MYSQL_PORT");
        name = suletta.getDotenv().get("MYSQL_NAME");
        username = suletta.getDotenv().get("MYSQL_USERNAME");
        password = suletta.getDotenv().get("MYSQL_PASSWORD");
    }

    public Connection getConnection() {
        return connection;
    }

    public synchronized void connect() throws SQLException {
        String jdbc = "jdbc:mysql://" + host + ":" + port + "/" + name;
        connection = DriverManager.getConnection(jdbc, username, password);
    }

    public synchronized boolean checkConnection(int timeout) throws SQLException {
        if (connection == null) connect();

        try {
            return connection.isValid(timeout);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
