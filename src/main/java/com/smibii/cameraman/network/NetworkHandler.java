package com.smibii.cameraman.network;

import com.smibii.cameraman.CameraMan;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            CameraMan.asResource("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        INSTANCE.registerMessage(
                packetId++,
                CameraPointToPointPacket.class,
                CameraPointToPointPacket::encode,
                CameraPointToPointPacket::decode,
                CameraPointToPointPacket::handle
        );
        INSTANCE.registerMessage(
                packetId++,
                CameraToPointPacket.class,
                CameraToPointPacket::encode,
                CameraToPointPacket::decode,
                CameraToPointPacket::handle
        );
        INSTANCE.registerMessage(
                packetId++,
                CameraPlayerToPointPacket.class,
                CameraPlayerToPointPacket::encode,
                CameraPlayerToPointPacket::decode,
                CameraPlayerToPointPacket::handle
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
    }
}
