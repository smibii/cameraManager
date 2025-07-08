package com.smibii.cameraman.debug;

import com.smibii.cameraman.CameraMan;
import com.smibii.cameraman.camera.CameraPoint;
import com.smibii.cameraman.camera.CameraPointAccessor;
import com.smibii.cameraman.events.CameraManagerSetupEvent;
import com.smibii.cameraman.math.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CameraMan.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class register {
    public static CameraPointAccessor POINT;
    public static CameraPointAccessor SPAWN;

    @SubscribeEvent
    public static void onCameraManagerSetup(CameraManagerSetupEvent event) {
        POINT = event.manager.registerPoint("point", CameraPoint.of(
                new Vec3(
                        0.5,
                        100.5,
                        -1.2
                ),
                0,
                0,
                0.5f,
                1000,
                Easing::easeInOutQuad,
                false
        ));
        SPAWN = event.manager.registerPoint("spawn", CameraPoint.of(
                new Vec3(
                        0.5,
                        100.62,
                        -1.5
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
