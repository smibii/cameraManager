package com.smibii.cameraman.mixin;

import com.smibii.cameraman.camera.CameraManager;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private boolean initialized;

    @Shadow private BlockGetter level;

    @Shadow private net.minecraft.world.entity.Entity entity;

    @Shadow private boolean detached;

    @Shadow protected abstract void setRotation(float pYRot, float pXRot);

    @Shadow protected abstract void setPosition(Vec3 pPos);

    @Inject(method = "setup", at = {@At("HEAD")}, cancellable = true)
    private void overrideCamera(BlockGetter pLevel, Entity pEntity, boolean pDetached, boolean pThirdPersonReverse, float pPartialTick, CallbackInfo ci) {
        CameraManager manager = com.smibii.cameraman.listeners.Camera.manager;

        if (!manager.isInUse()) return;

        this.initialized = true;
        this.level = pLevel;
        this.entity = pEntity;
        this.detached = manager.isDetached();
        this.setPosition(manager.getCurrentPos());
        this.setRotation(manager.getCurrentYaw(), manager.getCurrentPitch());

        ci.cancel();
    }
}
