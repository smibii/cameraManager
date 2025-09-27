package com.smibii.cameraman.camera;

import com.smibii.cameraman.events.TransitionCompletedEvent;
import com.smibii.cameraman.math.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CameraManager {
    private final Map<String, CameraPoint> pointMap = new HashMap<>();
    private String currentPointName = "#player";

    private Vec3 currentPos = Vec3.ZERO;
    private float currentYaw = 0f, currentPitch = 0f, currentFov = 70f, currentTilt = 0f;
    private boolean detached = true;

    private CameraPoint startPoint, endPoint;
    private long startTime;
    private boolean isTransitioning = false;

    private boolean inUse = false;
    private float playerYaw = 0f;
    private float playerPitch = 0f;

    public CameraManagerSettings settings;

    public CameraManager(CameraManagerSettings settings) {
        this.settings = settings;
    }

    private CameraPoint getPlayerPoint(long duration, Function<Double, Double> easing) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) throw new RuntimeException("LocalPlayer doesn't exist");

        playerYaw = player.getYRot();
        playerPitch = player.getXRot();

        return CameraPoint.of(
                new Vec3(player.getX(), player.getY() + 1.62, player.getZ()),
                playerYaw,
                playerPitch,
                ((float) mc.options.fov().get() / 100),
                duration,
                easing,
                false
        );
    }

    public CameraPointAccessor registerPoint(String name, CameraPoint point) {
        pointMap.put(name, point);
        return new CameraPointAccessor(name, point);
    }

    public void transitionFromPointToPoint(CameraPoint pointA, CameraPoint pointB, String pointBName) {
        if (pointA == null || pointB == null) throw new IllegalArgumentException("pointA or pointB is null!");

        this.startPoint = pointA;
        this.endPoint = pointB;
        this.currentPointName = pointBName;

        this.currentPos = pointA.pos;
        this.currentYaw = pointA.yaw;
        this.currentPitch = pointA.pitch;
        this.currentFov = pointA.fov;
        this.currentTilt = pointA.tilt;

        this.detached = pointB.detached;

        this.startTime = System.currentTimeMillis();
        this.isTransitioning = true;
    }

    public void transitionToPoint(String name) {
        if (!pointMap.containsKey(name)) throw new IllegalArgumentException("Key " + name + " does not exist in pointMap");

        if (currentPointName.equals("#player")) {
            transitionFromPlayerToPoint(name);
            return;
        }

        setInUse(true);
        transitionFromPointToPoint(endPoint, pointMap.get(name), name);
    }

    public void transitionFromPlayerToPoint(String name) {
        if (!pointMap.containsKey(name)) throw new IllegalArgumentException("Key " + name + " does not exist in pointMap");
        CameraPoint playerPoint = getPlayerPoint(1000, Easing::easeInOutQuad);
        setInUse(true);
        transitionFromPointToPoint(playerPoint, pointMap.get(name), name);
    }

    public void transitionToPlayer() {
        transitionToPlayer(1000);
    }

    public void transitionToPlayer(long duration) {
        transitionToPlayer(duration, Easing::easeInOutQuad);
    }

    public void transitionToPlayer(long duration, Function<Double, Double> easing) {
        if (!inUse) return;
        CameraPoint playerPoint = getPlayerPoint(duration, easing);
        transitionFromPointToPoint(endPoint != null ? endPoint : playerPoint, playerPoint, "#player");
        Executors.newSingleThreadScheduledExecutor()
                .schedule(() -> setInUse(false), duration, TimeUnit.MILLISECONDS);
    }

    public void tickFrame() {
        if (!isTransitioning) return;
        if (startPoint == null || endPoint == null) {
            isTransitioning = false;
            return;
        }

        long elapsed = System.currentTimeMillis() - startTime;
        double t = Math.min(1.0, (double) elapsed / endPoint.duration);
        Function<Double, Double> easing = endPoint.easing;
        double eased = easing.apply(t);

        Minecraft mc = Minecraft.getInstance();
        float endFov = endPoint.fov == -1 ? ((float) mc.options.fov().get() / 100) : endPoint.fov;

        currentPos = lerp(startPoint.pos, endPoint.pos, eased);
        currentYaw = lerpAngle(startPoint.yaw, endPoint.yaw, eased);
        currentPitch = Mth.lerp((float) eased, startPoint.pitch, endPoint.pitch);
        currentFov = Mth.lerp((float) eased, startPoint.fov, endFov);
        currentTilt = lerpAngle(startPoint.tilt, endPoint.tilt, eased);

        if (t >= 1.0) {
            MinecraftForge.EVENT_BUS.post(new TransitionCompletedEvent(endPoint, currentPointName));
            isTransitioning = false;
        }
    }

    private Vec3 lerp(Vec3 from, Vec3 to, double t) {
        return new Vec3(
                Mth.lerp((float) t, (float) from.x, (float) to.x),
                Mth.lerp((float) t, (float) from.y, (float) to.y),
                Mth.lerp((float) t, (float) from.z, (float) to.z)
        );
    }

    private float lerpAngle(float a, float b, double t) {
        float delta = (((b - a) + 540f) % 360f) - 180f;
        return a + (float) t * delta;
    }

    public void setInUse(boolean value) {
        inUse = value;
        if (!value) currentPointName = "#player";
    }

    public void setCurrentPointName(String name) {
        currentPointName = name;
    }

    public Vec3 getCurrentPos() {
        return currentPos;
    }

    public float getCurrentYaw() {
        return currentYaw;
    }

    public float getCurrentPitch() {
        return currentPitch;
    }

    public float getCurrentFov() {
        return currentFov;
    }

    public String getCurrentPointName() {
        return currentPointName;
    }

    public float getPlayerYaw() {
        return playerYaw;
    }

    public float getPlayerPitch() {
        return playerPitch;
    }

    public float getCurrentTilt() {
        return currentTilt;
    }

    public boolean isDetached() {
        return detached;
    }

    public boolean isInUse() {
        return inUse;
    }
}