package com.smibii.cameraman.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.smibii.cameraman.Bruno;
import com.smibii.cameraman.network.CameraSetInUsePacket;
import com.smibii.cameraman.network.CameraToPlayerPacket;
import com.smibii.cameraman.network.CameraToPointPacket;
import com.smibii.cameraman.network.NetworkHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bruno.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BrunoCommand {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("bruno").requires(
                        (command) -> { return command.hasPermission(2); }
                ).then(Commands.literal("transition")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(ctx -> {
                                    CommandSourceStack source = ctx.getSource();
                                    String name = StringArgumentType.getString(ctx, "name");
                                    return transition(source, name);
                                })
                        )
                ).then(Commands.literal("exit")
                        .executes(ctx -> {
                            CommandSourceStack source = ctx.getSource();
                            return exit(source);
                        })
                ));
    }

    private static int transition(CommandSourceStack source, String name) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();

        if (name.equals("player")) {
            NetworkHandler.sendToClient(
                    new CameraToPlayerPacket(),
                    player
            );
            source.sendSuccess(
                    () -> Component.literal(
                            "Transitioned player " +
                                    player.getName().getString() +
                                    " to its LocalPlayer position"), false);
        } else {
            NetworkHandler.sendToClient(
                    new CameraToPointPacket(name),
                    player
            );
            source.sendSuccess(
                    () -> Component.literal(
                            "Transitioned player " +
                                    player.getName().getString() +
                                    " to " + name), false);
        }

        return 1;
    }

    private static int exit(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();

        NetworkHandler.sendToClient(
                new CameraSetInUsePacket(false),
                player
        );
        source.sendSuccess(
                () -> Component.literal(
                        player.getName().getString() +
                                " exit camera!"), false);

        return 1;
    }
}
