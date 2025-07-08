package com.smibii.cameraman.network;

import com.smibii.cameraman.listeners.Camera;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CameraToPlayerPacket {
    public CameraToPlayerPacket() {

    }

    public static void encode(CameraToPlayerPacket msg, FriendlyByteBuf buf) {

    }

    public static CameraToPlayerPacket decode(FriendlyByteBuf buf) {
        return new CameraToPlayerPacket();
    }

    public static void handle(CameraToPlayerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Camera.manager.transitionToPlayer();
        });
        ctx.get().setPacketHandled(true);
    }
}
