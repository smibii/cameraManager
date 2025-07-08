package com.smibii.cameraman.network;

import com.smibii.cameraman.listeners.Camera;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CameraToPointPacket {
    private final String pointName;

    public CameraToPointPacket(String pointName) {
        this.pointName = pointName;
    }

    public static void encode(CameraToPointPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.pointName);
    }

    public static CameraToPointPacket decode(FriendlyByteBuf buf) {
        return new CameraToPointPacket(buf.readUtf());
    }

    public static void handle(CameraToPointPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Camera.manager.transitionToPoint(msg.pointName);
        });
        ctx.get().setPacketHandled(true);
    }
}