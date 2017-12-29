package com.untamemadman.plugins.sponge.dropparty;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.format.TextColors;

import java.nio.file.Path;

@Plugin(id = "dropparty", name = "Drop Party", version = "1.0")
public class Main
{
    public Object plugin = this;

    // Setup Logger
    private Logger logger;

    @Inject
    private void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    @Inject
    @DefaultConfig(sharedRoot = true)
    private Path defaultConfig;

    @Inject
    @DefaultConfig(sharedRoot = true)
    private ConfigurationLoader<CommentedConfigurationNode> configManager;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path privateConfigDir;

    // Listener for Plugin Pre Init
    @Listener
    public void onPluginPreInit(GamePreInitializationEvent event)
    {

    }

    // Listener for Plugin Init
    @Listener
    public void onPluginInit(GameInitializationEvent event)
    {
        CommandSpec myCommandSpec = CommandSpec.builder()
                .description(Text.of("Drop Party Command"))
                .permission("dropparty.admin")
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("Identifier"))))
                .executor(new DropPartyCommand(this))
                .build();

        Sponge.getCommandManager().register(this, myCommandSpec, "dropparty", "drop", "dp");
    }

    // Listener for Plugin Loaded
    @Listener
    public void onPluginLoad(GameLoadCompleteEvent event)
    {
        getLogger().info("Plugin loaded");
    }
}