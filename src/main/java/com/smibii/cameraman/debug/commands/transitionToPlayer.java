package com.smibii.cameraman.debug.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.smibii.cameraman.listeners.Camera;
import com.smibii.cameraman.network.CameraPlayerToPointPacket;
import com.smibii.cameraman.network.CameraToPlayerPacket;
import com.smibii.cameraman.network.NetworkHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;

public class transitionToPlayer {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("transitionToPlayer").requires((command) -> {
            return command.hasPermission(2);
        }).executes((command) -> {
            return execute(command.getSource());
        }));
    }

    private static int execute(CommandSourceStack source) {
        ServerPlayer player = source.getPlayer();

        if (player == null) {
            source.sendFailure(Component.literal("This command must be run by a player."));
            return 0;
        }

        // Send packet to client
        NetworkHandler.INSTANCE.sendTo(
                new CameraToPlayerPacket(),
                player.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );

        source.sendSuccess(() -> Component.literal("Told client to transition to camera point 'player'"), false);
        return 1;
    }
}
