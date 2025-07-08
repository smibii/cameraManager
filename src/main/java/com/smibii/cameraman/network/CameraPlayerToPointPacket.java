package com.smibii.cameraman.network;

import com.smibii.cameraman.listeners.Camera;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CameraPlayerToPointPacket {
    private final String pointName;

    public CameraPlayerToPointPacket(String pointName) {
        this.pointName = pointName;
    }

    public static void encode(CameraPlayerToPointPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.pointName);
    }

    public static CameraPlayerToPointPacket decode(FriendlyByteBuf buf) {
        return new CameraPlayerToPointPacket(buf.readUtf());
    }

    public static void handle(CameraPlayerToPointPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Camera.manager.transitionFromPlayerToPoint(msg.pointName);
        });
        ctx.get().setPacketHandled(true);
    }
}
