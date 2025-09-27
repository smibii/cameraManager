package com.smibii.cameraman.listeners;

import com.mojang.blaze3d.systems.RenderSystem;
import com.smibii.cameraman.Bruno;
import com.smibii.cameraman.camera.CameraManager;
import com.smibii.cameraman.camera.CameraManagerSettings;
import com.smibii.cameraman.events.CameraManagerSetupEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Quaternionf;

@Mod.EventBusSubscriber(modid = Bruno.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class Camera {
    public static CameraManager manager = new CameraManager(
            new CameraManagerSettings(
                    true,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    true
            ));

    private static boolean initialized = false;

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        if (!initialized) {
            MinecraftForge.EVENT_BUS.post(new CameraManagerSetupEvent(manager));
            initialized = true;
        }

        manager.tickFrame();
    }

    @SubscribeEvent
    public static void modifyFov(ComputeFovModifierEvent event) {
        if (!manager.isInUse()) return;
        event.setNewFovModifier(manager.getCurrentFov());
    }

    @SubscribeEvent
    public static void onInteraction(InputEvent.InteractionKeyMappingTriggered event) {
        if (manager.isInUse()) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onBlockHighlight(RenderHighlightEvent.Block event) {
        if (manager.isInUse() && !manager.settings.isPlayerCanInteract()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onInputUpdate(MovementInputUpdateEvent event) {
        if (!manager.isInUse()) return;

        Input input = event.getInput();
        input.leftImpulse = 0;
        input.forwardImpulse = 0;
        input.jumping = false;
        input.shiftKeyDown = false;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !manager.isInUse() || manager.settings.isPlayerCanLookAround()) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        player.setYRot(manager.getPlayerYaw());
        player.setXRot(manager.getPlayerPitch());
        player.yHeadRot = manager.getPlayerYaw();
        player.yBodyRot = manager.getPlayerPitch();
    }

    @SubscribeEvent
    public static void onRender(RenderGuiOverlayEvent.Pre event) {
        if (!manager.isInUse()) return;

        String overlayId = event.getOverlay().id().getPath();
        CameraManagerSettings settings = manager.settings;

        if (!settings.isDrawAir() && overlayId.equals("air_level")) event.setCanceled(true);
        else if (!settings.isDrawArmor() && overlayId.equals("armor_level")) event.setCanceled(true);
        else if (!settings.isDrawBossbar() && overlayId.equals("boss_event_progress")) event.setCanceled(true);
        else if (!settings.isDrawCrosshair() && overlayId.equals("crosshair")) event.setCanceled(true);
        else if (!settings.isDrawExperience() && overlayId.equals("experience_bar")) event.setCanceled(true);
        else if (!settings.isDrawFood() && overlayId.equals("food_level")) event.setCanceled(true);
        else if (!settings.isDrawFrostbite() && overlayId.equals("frostbite")) event.setCanceled(true);
        else if (!settings.isDrawHealth() && overlayId.equals("player_health")) event.setCanceled(true);
        else if (!settings.isDrawHelmet() && overlayId.equals("helmet")) event.setCanceled(true);
        else if (!settings.isDrawHotbar() && overlayId.equals("hotbar")) event.setCanceled(true);
        else if (!settings.isDrawItemName() && overlayId.equals("item_name")) event.setCanceled(true);
        else if (!settings.isDrawJumpBar() && overlayId.equals("jump_bar")) event.setCanceled(true);
        else if (!settings.isDrawMountHealth() && overlayId.equals("mount_health")) event.setCanceled(true);
        else if (!settings.isDrawPortal() && overlayId.equals("portal")) event.setCanceled(true);
        else if (!settings.isDrawPotions() && overlayId.equals("potion_icons")) event.setCanceled(true);
        else if (!settings.isDrawSleepFade() && overlayId.equals("sleep_fade")) event.setCanceled(true);
        else if (!settings.isDrawSpyglass() && overlayId.equals("spyglass")) event.setCanceled(true);
        else if (!settings.isDrawVignette() && overlayId.equals("vignette")) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        if (!manager.isInUse()) return;
        if (!manager.settings.isRenderHand()) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) return;
        if (manager.getCurrentTilt() == 0) return;

        RenderSystem.getModelViewStack().pushPose();
        float radians = (float) Math.toRadians(manager.getCurrentTilt());

        Quaternionf quaternionf = new Quaternionf().rotateZ(manager.getCurrentTilt());
        RenderSystem.getModelViewStack().mulPose(quaternionf);
    }

    @SubscribeEvent
    public static void onRenderWorldEnd(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) return;
        if (manager.getCurrentTilt() == 0) return;

        RenderSystem.getModelViewStack().popPose();
        RenderSystem.applyModelViewMatrix();
    }
}