package com.smibii.cameraman.network;

import com.smibii.cameraman.listeners.Camera;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CameraSetInUsePacket {
    private final boolean inUse;

    public CameraSetInUsePacket(boolean inUse) {
        this.inUse = inUse;
    }

    public static void encode(CameraSetInUsePacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.inUse);
    }

    public static CameraSetInUsePacket decode(FriendlyByteBuf buf) {
        return new CameraSetInUsePacket(buf.readBoolean());
    }

    public static void handle(CameraSetInUsePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Camera.manager.setInUse(msg.inUse);
        });
        ctx.get().setPacketHandled(true);
    }
}