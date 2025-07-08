package com.smibii.cameraman.listeners;

import com.smibii.cameraman.CameraMan;
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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CameraMan.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class Camera {
    public static CameraManager manager = new CameraManager(
            new CameraManagerSettings(
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
                    false,
                    false
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
        if (manager.isInUse() && !manager.settings.playerCanInteract()) {
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
        if (event.phase != TickEvent.Phase.END || !manager.isInUse() || manager.settings.playerCanLookAround()) return;

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

        if (!settings.drawAir() && overlayId.equals("air_level")) event.setCanceled(true);
        else if (!settings.drawArmor() && overlayId.equals("armor_level")) event.setCanceled(true);
        else if (!settings.drawBossbar() && overlayId.equals("boss_event_progress")) event.setCanceled(true);
        else if (!settings.drawCrosshair() && overlayId.equals("crosshair")) event.setCanceled(true);
        else if (!settings.drawExperience() && overlayId.equals("experience_bar")) event.setCanceled(true);
        else if (!settings.drawFood() && overlayId.equals("food_level")) event.setCanceled(true);
        else if (!settings.drawFrostbite() && overlayId.equals("frostbite")) event.setCanceled(true);
        else if (!settings.drawHealth() && overlayId.equals("player_health")) event.setCanceled(true);
        else if (!settings.drawHelmet() && overlayId.equals("helmet")) event.setCanceled(true);
        else if (!settings.drawHotbar() && overlayId.equals("hotbar")) event.setCanceled(true);
        else if (!settings.drawItemName() && overlayId.equals("item_name")) event.setCanceled(true);
        else if (!settings.drawJumpBar() && overlayId.equals("jump_bar")) event.setCanceled(true);
        else if (!settings.drawMountHealth() && overlayId.equals("mount_health")) event.setCanceled(true);
        else if (!settings.drawPortal() && overlayId.equals("portal")) event.setCanceled(true);
        else if (!settings.drawPotions() && overlayId.equals("potion_icons")) event.setCanceled(true);
        else if (!settings.drawSleepFade() && overlayId.equals("sleep_fade")) event.setCanceled(true);
        else if (!settings.drawSpyglass() && overlayId.equals("spyglass")) event.setCanceled(true);
        else if (!settings.drawVignette() && overlayId.equals("vignette")) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {
        if (!manager.isInUse()) return;
        if (!manager.settings.renderHand()) event.setCanceled(true);
    }
}