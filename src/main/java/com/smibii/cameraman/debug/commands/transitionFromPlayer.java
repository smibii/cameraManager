package com.smibii.cameraman.debug.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.smibii.cameraman.CameraMan;
import com.smibii.cameraman.listeners.Camera;
import com.smibii.cameraman.network.CameraPlayerToPointPacket;
import com.smibii.cameraman.network.NetworkHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class transitionFromPlayer {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("transitionFromPlayer").requires((command) -> {
            return command.hasPermission(2);
        }).then(Commands.argument("name", StringArgumentType.word()).executes((command) -> {
            return execute(command.getSource(), StringArgumentType.getString(command, "name"));
        })));
    }

    private static int execute(CommandSourceStack source, String pointName) {
        ServerPlayer player = source.getPlayer();

        if (player == null) {
            source.sendFailure(Component.literal("This command must be run by a player."));
            return 0;
        }

        NetworkHandler.INSTANCE.sendTo(
                new CameraPlayerToPointPacket(pointName),
                player.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );

        source.sendSuccess(() -> Component.literal("Told client to transition to camera point '" + pointName + "'"), false);
        return 1;
    }
}
