package com.untamemadman.plugins.sponge.dropparty;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DropPartyCommand implements CommandExecutor
{
    private final Object plugin;

    public DropPartyCommand(Object plugin) {
        this.plugin = plugin;
    }

    Task.Builder taskBuilder = Task.builder();

    Text prefix = Text.builder("[").color(TextColors.GOLD).append(Text.builder("DropParty").color(TextColors.AQUA).build()).append(Text.builder("] ").color(TextColors.GOLD).build()).build();

    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        Integer Identifier = args.<Integer>getOne("Identifier").get();

        if (Identifier.equals(1) || Identifier.equals(2) || Identifier.equals(3) || Identifier.equals(4) || Identifier.equals(5))
        {
            Sponge.getServer().getBroadcastChannel().send(Text.join(prefix, Text.builder("Starting Drop Party at level " + Identifier).color(TextColors.AQUA).build()));

            Task task = Task.builder().execute(new CountDown()).interval(1, TimeUnit.SECONDS).name("Self-Cancelling Timer Task").submit(plugin);

            World world = Sponge.getServer().getWorld("world").get();
            Entity item = world.createEntity(EntityTypes.ITEM, world.getLocation(0.5,6,0.5).getPosition());
            world.spawnEntity(item);
        }
        else
        {
            src.sendMessage(Text.join(prefix, Text.builder("Level not found").color(TextColors.AQUA).build()));
            src.sendMessage(Text.join(prefix, Text.builder("Level's are 1, 2, 3, 4 and 5").color(TextColors.AQUA).build()));
        }
        return CommandResult.success();
    }

    private class CountDown implements Consumer<Task> {
        private int seconds = 11;
        @Override
        public void accept(Task task) {
            seconds--;
            if(seconds != 0) {
                Sponge.getServer().getBroadcastChannel().send(Text.join(prefix, Text.builder("Party starting in " + seconds).color(TextColors.AQUA).build()));
            }
            if (seconds == 0) {
                Sponge.getServer().getBroadcastChannel().send(Text.join(prefix, Text.builder("The party has started!").color(TextColors.AQUA).build()));
                task.cancel();
            }
        }
    }
}