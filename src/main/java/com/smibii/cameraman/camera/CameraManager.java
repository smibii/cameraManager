package com.smibii.cameraman.camera;

import com.smibii.cameraman.math.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CameraManager {
    private final Map<String, CameraPoint> pointMap = new HashMap<>();
    private String currentPointName = "#player";

    private Vec3 currentPos = Vec3.ZERO;
    private float currentYaw = 0, currentPitch = 0, currentFov = 0.50f;
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

    private CameraPoint getPlayerPoint() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) throw new RuntimeException("LocalPlayer doesn't exist");

        playerYaw = player.getYRot();
        playerPitch = player.getXRot();

        return CameraPoint.of(
                new Vec3(
                        player.getX(),
                        player.getY() + 1.62,
                        player.getZ()
                ),
                playerYaw,
                playerPitch,
                (float) mc.options.fov().get() / 100,
                1000,
                Easing::easeInOutQuad,
                false
        );
    }

    public CameraPointAccessor registerPoint(String name, CameraPoint point) {
        pointMap.put(name, point);
        return new CameraPointAccessor(name, point);
    }

    public void transitionFromPointToPoint(CameraPoint pointA, CameraPoint pointB, String pointBName) {
        if (pointA == null || pointB == null) throw new IllegalArgumentException("pointA or pointB is null!");

        startPoint = pointA;
        currentPointName = pointBName;
        endPoint = pointB;

        currentPos = pointA.pos();
        currentYaw = pointA.yaw();
        currentPitch = pointA.pitch();
        currentFov = pointA.fov();

        detached = pointB.detached();

        startTime = System.currentTimeMillis();
        isTransitioning = true;
    }

    public void transitionToPoint(String name) {
        if (!inUse) return;
        if (!pointMap.containsKey(name)) throw new IllegalArgumentException("Key " + name + " does not exist in pointMap");
        transitionFromPointToPoint(endPoint, pointMap.get(name), name);
    }

    public void transitionFromPlayerToPoint(String name) {
        if (inUse) return;
        if (!pointMap.containsKey(name)) throw new IllegalArgumentException("Key " + name + " does not exist in pointMap");

        endPoint = getPlayerPoint();

        setInUse(true);
        transitionToPoint(name);
    }

    public void transitionToPlayer() {
        transitionToPlayer(1000);
    }

    public void transitionToPlayer(long duration) {
        transitionToPlayer(duration, Easing::easeInOutQuad);
    }

    public void transitionToPlayer(long duration, Function<Double, Double> easing) {
        if (!inUse) return;

        CameraPoint playerPoint = getPlayerPoint();

        transitionFromPointToPoint(endPoint, playerPoint, "#player");
        Executors.newSingleThreadScheduledExecutor()
                .schedule(() -> setInUse(false), duration, TimeUnit.MILLISECONDS);
    }

    public void setInUse(boolean value) { inUse = value; }

    public void tickFrame() {
        if (!isTransitioning) return;

        long elapsed = System.currentTimeMillis() - startTime;
        double t = Math.min(1.0, (double) elapsed / endPoint.duration());
        Function<Double, Double> easing = endPoint.easing();
        if (easing == null) easing = Easing::easeInOutQuad;
        double eased = easing.apply(t);

        currentPos = lerp(startPoint.pos(), endPoint.pos(), eased);
        currentYaw = lerpAngle(startPoint.yaw(), endPoint.yaw(), eased);
        currentPitch = (float) Mth.lerp(eased, startPoint.pitch(), endPoint.pitch());
        currentFov = (float) Mth.lerp(eased, startPoint.fov(), endPoint.fov());

        if (t >= 1.0) {
            isTransitioning = false;
        };
    }

    private Vec3 lerp(Vec3 from, Vec3 to, double t) {
        return new Vec3(
                Mth.lerp(t, from.x, to.x),
                Mth.lerp(t, from.y, to.y),
                Mth.lerp(t, from.z, to.z)
        );
    }

    private float lerpAngle(float a, float b, double t) {
        float delta = (((b - a) + 540f) % 360f) - 180f;
        return a + (float) t * delta;
    }

    public Vec3 getCurrentPos() { return currentPos; }
    public float getCurrentYaw() { return currentYaw; }
    public float getCurrentPitch() { return currentPitch; }
    public float getCurrentFov() { return currentFov; }
    public String getCurrentPointName() { return currentPointName; }
    public float getPlayerYaw() { return playerYaw; }
    public float getPlayerPitch() { return playerPitch; }
    public boolean isDetached() { return detached; }
    public boolean isInUse() { return inUse; }
}
