package com.smibii.cameraman.network;

import com.smibii.cameraman.listeners.Camera;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CameraPointToPointPacket {
    private final String pointAName;
    private final String pointBName;

    public CameraPointToPointPacket(String pointAName, String pointBName) {
        this.pointAName = pointAName;
        this.pointBName = pointBName;
    }

    public static void encode(CameraPointToPointPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.pointAName);
        buf.writeUtf(msg.pointBName);
    }

    public static CameraPointToPointPacket decode(FriendlyByteBuf buf) {
        return new CameraPointToPointPacket(buf.readUtf(), buf.readUtf());
    }

    public static void handle(CameraPointToPointPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Camera.manager.transitionFromPointToPoint(msg.pointAName, msg.pointBName);
        });
        ctx.get().setPacketHandled(true);
    }
}
