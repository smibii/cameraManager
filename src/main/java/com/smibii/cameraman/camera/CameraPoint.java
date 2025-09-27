package com.smibii.cameraman.camera;

import com.smibii.cameraman.math.Easing;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.io.Serializable;
import java.util.function.Function;

public class CameraPoint implements Serializable {
    public final Vec3 pos;
    public final float yaw;
    public final float pitch;
    public final float fov;
    public final long duration;
    public final Function<Double, Double> easing;
    public final boolean detached;
    public final float tilt;

    public CameraPoint(Vec3 pos, float yaw, float pitch, float fov, long duration,
                       Function<Double, Double> easing, boolean detached, float tilt) {
        this.pos = pos;
        this.yaw = yaw;
        this.pitch = pitch;
        this.fov = fov;
        this.duration = duration;
        this.easing = easing != null ? easing : Easing::easeInOutQuad;
        this.detached = detached;
        this.tilt = tilt;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(pos.x);
        buf.writeDouble(pos.y);
        buf.writeDouble(pos.z);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
        buf.writeFloat(fov);
        buf.writeLong(duration);
        buf.writeBoolean(detached);
        buf.writeFloat(tilt);

        buf.writeUtf("easeInOutQuad");
    }

    public static CameraPoint read(FriendlyByteBuf buf) {
        Vec3 pos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        float yaw = buf.readFloat();
        float pitch = buf.readFloat();
        float fov = buf.readFloat();
        long duration = buf.readLong();
        boolean detached = buf.readBoolean();
        float tilt = buf.readFloat();

        String easingName = buf.readUtf();
        Function<Double, Double> easing = Easing::easeInOutQuad;

        return new CameraPoint(pos, yaw, pitch, fov, duration, easing, detached, tilt);
    }

    // === Factories ===
    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov) {
        return new CameraPoint(pos, yaw, pitch, fov, 1000, Easing::easeInOutQuad, true, 0);
    }

    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov, long duration) {
        return new CameraPoint(pos, yaw, pitch, fov, duration, Easing::easeInOutQuad, true, 0);
    }

    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov, long duration, Function<Double, Double> easing) {
        return new CameraPoint(pos, yaw, pitch, fov, duration, easing, true, 0);
    }

    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov, long duration, Function<Double, Double> easing, boolean detached) {
        return new CameraPoint(pos, yaw, pitch, fov, duration, easing, detached, 0);
    }

    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov, long duration, Function<Double, Double> easing, boolean detached, float tilt) {
        return new CameraPoint(pos, yaw, pitch, fov, duration, easing, detached, tilt);
    }
}
