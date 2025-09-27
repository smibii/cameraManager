package com.smibii.cameraman.debug;

import com.smibii.cameraman.Bruno;
import com.smibii.cameraman.camera.CameraPoint;
import com.smibii.cameraman.camera.CameraPointAccessor;
import com.smibii.cameraman.events.CameraManagerSetupEvent;
import com.smibii.cameraman.math.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bruno.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class register {
    public static CameraPointAccessor POINT;
    public static CameraPointAccessor SPAWN;

    @SubscribeEvent
    public static void onCameraManagerSetup(CameraManagerSetupEvent event) {
        POINT = event.manager.registerPoint("point", CameraPoint.of(
                new Vec3(
                        11.5,
                        151.5,
                        27.2
                ),
                0,
                0,
                0.5f,
                0,
                Easing::easeInOutQuad,
                true,
                359
        ));
        SPAWN = event.manager.registerPoint("spawn", CameraPoint.of(
                new Vec3(
                        11.5,
                        151.62,
                        26.5
                ),
                0,
                0,
                (float) Minecraft.getInstance().options.fov().get() / 100,
                1000,
                Easing::easeInOutQuad,
                false
        ));
    }
}
