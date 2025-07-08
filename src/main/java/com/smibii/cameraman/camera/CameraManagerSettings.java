package com.smibii.cameraman.camera;

import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;

public record CameraManagerSettings(
        boolean playerCanMove,
        boolean playerCanInteract,
        boolean playerCanLookAround,
        boolean renderHand,
        boolean drawAir,           // air_level
        boolean drawArmor,         // armor_level
        boolean drawBossbar,       // boss_event_progress
        boolean drawCrosshair,     // crosshair
        boolean drawExperience,    // experience_bar
        boolean drawFood,          // food_level
        boolean drawFrostbite,     // frostbite
        boolean drawHealth,        // player_health
        boolean drawHelmet,        // helmet
        boolean drawHotbar,        // hotbar
        boolean drawItemName,      // item_name
        boolean drawJumpBar,       // jump_bar
        boolean drawMountHealth,   // mount_health
        boolean drawPortal,        // portal
        boolean drawPotions,       // potion_icons
        boolean drawSleepFade,     // sleep_fade
        boolean drawSpyglass,      // spyglass
        boolean drawVignette       // vignette
) {
}
