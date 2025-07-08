package com.smibii.cameraman.camera;

import com.smibii.cameraman.math.Easing;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public record CameraPoint (
        Vec3 pos,
        float yaw,
        float pitch,
        float fov,
        long duration,
        Function<Double, Double> easing,
        boolean detached
) {
    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov) {
        return new CameraPoint(pos, yaw, pitch, fov, 1000, Easing::easeInOutQuad, true);
    }

    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov, long duration) {
        return new CameraPoint(pos, yaw, pitch, fov, duration, Easing::easeInOutQuad, true);
    }

    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov, long duration, Function<Double, Double> easing) {
        return new CameraPoint(pos, yaw, pitch, fov, duration, easing != null ? easing : Easing::easeInOutQuad, true);
    }

    public static CameraPoint of(Vec3 pos, float yaw, float pitch, float fov, long duration, Function<Double, Double> easing, boolean detached) {
        return new CameraPoint(pos, yaw, pitch, fov, duration, easing != null ? easing : Easing::easeInOutQuad, detached);
    }
}
