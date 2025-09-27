package com.smibii.cameraman.network;

import com.smibii.cameraman.camera.CameraPoint;
import com.smibii.cameraman.listeners.Camera;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CameraRegisterPointPacket {
    private final String name;
    private final CameraPoint point;

    public CameraRegisterPointPacket(String name, CameraPoint point) {
        this.name = name;
        this.point = point;
    }

    public static void encode(CameraRegisterPointPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.name);      // write the name
        msg.point.write(buf);        // then write the CameraPoint
    }

    public static CameraRegisterPointPacket decode(FriendlyByteBuf buf) {
        String name = buf.readUtf();             // read the name first
        CameraPoint point = CameraPoint.read(buf); // then read the CameraPoint
        return new CameraRegisterPointPacket(name, point);
    }

    public static void handle(CameraRegisterPointPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Camera.manager.registerPoint(msg.name, msg.point); // use the name properly
        });
        ctx.get().setPacketHandled(true);
    }
}
