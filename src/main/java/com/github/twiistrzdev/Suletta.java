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

import com.github.twiistrzdev.commands.HelpCommand;
import com.github.twiistrzdev.database.MySQL;
import com.github.twiistrzdev.listeners.ReadyListener;
import com.github.twiistrzdev.listeners.SlashCommandInteractionListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.sql.SQLException;

/**
 * @author Emmanuel See Te
 *
 * @since 1.0.0-alpha.1
 */
public class Suletta {
    private final Dotenv dotenv;
    private final Logger logger;
    private final MySQL mySQL;
    private final UUIDManager uuidManager;
    private final CommandManager commandManager;
    private final ShardManager shardManager;

    public Suletta() {
        dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
        logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
        mySQL = new MySQL(this);
        uuidManager = new UUIDManager();
        commandManager = new CommandManager();

        try {
            mySQL.connect();
            logger.info("Connected to MySQL Database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        commandManager.add(new HelpCommand(this));

        DefaultShardManagerBuilder shardManagerBuilder;
        shardManagerBuilder = DefaultShardManagerBuilder.createDefault(dotenv.get("CLIENT_TOKEN"));
        shardManagerBuilder.setShardsTotal(-1);
        shardManagerBuilder.setStatus(OnlineStatus.ONLINE);
        shardManagerBuilder.setActivity(Activity.playing("/help"));
        shardManagerBuilder.setBulkDeleteSplittingEnabled(true);
        shardManagerBuilder.setLargeThreshold(50);
        shardManagerBuilder.setMemberCachePolicy(MemberCachePolicy.ALL);
        shardManagerBuilder.setChunkingFilter(ChunkingFilter.ALL);
        shardManagerBuilder.enableCache(CacheFlag.MEMBER_OVERRIDES);
        shardManagerBuilder.disableCache(
                CacheFlag.VOICE_STATE,
                CacheFlag.ACTIVITY,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.EMOJI,
                CacheFlag.ONLINE_STATUS
        );
        shardManagerBuilder.enableIntents(
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.MESSAGE_CONTENT
        );
        shardManagerBuilder.addEventListeners(
                new ReadyListener(this),
                new SlashCommandInteractionListener(this)
        );

        shardManager = shardManagerBuilder.build();
    }

    public static void main(String[] args) {
        try {
            new Suletta();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nonnull
    @CheckReturnValue
    public Dotenv getDotenv() {
        return dotenv;
    }

    @Nonnull
    @CheckReturnValue
    public Logger getLogger() {
        return logger;
    }

    @Nonnull
    @CheckReturnValue
    public MySQL getMySQL() {
        return mySQL;
    }

    @Nonnull
    @CheckReturnValue
    public UUIDManager getUuidManager() {
        return uuidManager;
    }

    @Nonnull
    @CheckReturnValue
    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Nonnull
    @CheckReturnValue
    public ShardManager getShardManager() {
        return shardManager;
    }
}
