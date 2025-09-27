package com.smibii.cameraman.network;

import com.smibii.cameraman.Bruno;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            Bruno.asResource("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        INSTANCE.registerMessage(
                packetId++,
                CameraToPointPacket.class,
                CameraToPointPacket::encode,
                CameraToPointPacket::decode,
                CameraToPointPacket::handle
        );
        INSTANCE.registerMessage(
                packetId++,
                CameraToPlayerPacket.class,
                CameraToPlayerPacket::encode,
                CameraToPlayerPacket::decode,
                CameraToPlayerPacket::handle
        );
        INSTANCE.registerMessage(
                packetId++,
                CameraSetInUsePacket.class,
                CameraSetInUsePacket::encode,
                CameraSetInUsePacket::decode,
                CameraSetInUsePacket::handle
        );
        INSTANCE.registerMessage(
                packetId++,
                CameraRegisterPointPacket.class,
                CameraRegisterPointPacket::encode,
                CameraRegisterPointPacket::decode,
                CameraRegisterPointPacket::handle
        );
    }

    public static <MSG> void sendToClient(MSG message, ServerPlayer player) {
        NetworkHandler.INSTANCE.sendTo(
                message,
                player.connection.connection,
                NetworkDirection.PLAY_TO_CLIENT
        );
    }
}
