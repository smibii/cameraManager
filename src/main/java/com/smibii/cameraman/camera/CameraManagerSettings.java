package com.smibii.cameraman.camera;

public class CameraManagerSettings {
    private boolean playerCanMove;
    private boolean playerCanInteract;
    private boolean playerCanLookAround;
    private boolean renderHand;
    private boolean drawAir;          // air_level
    private boolean drawArmor;        // armor_level
    private boolean drawBossbar;      // boss_event_progress
    private boolean drawCrosshair;    // crosshair
    private boolean drawExperience;   // experience_bar
    private boolean drawFood;         // food_level
    private boolean drawFrostbite;    // frostbite
    private boolean drawHealth;       // player_health
    private boolean drawHelmet;       // helmet
    private boolean drawHotbar;       // hotbar
    private boolean drawItemName;     // item_name
    private boolean drawJumpBar;      // jump_bar
    private boolean drawMountHealth;  // mount_health
    private boolean drawPortal;       // portal
    private boolean drawPotions;      // potion_icons
    private boolean drawSleepFade;    // sleep_fade
    private boolean drawSpyglass;     // spyglass
    private boolean drawVignette;     // vignette

    public CameraManagerSettings(
            boolean playerCanMove,
            boolean playerCanInteract,
            boolean playerCanLookAround,
            boolean renderHand,
            boolean drawAir,
            boolean drawArmor,
            boolean drawBossbar,
            boolean drawCrosshair,
            boolean drawExperience,
            boolean drawFood,
            boolean drawFrostbite,
            boolean drawHealth,
            boolean drawHelmet,
            boolean drawHotbar,
            boolean drawItemName,
            boolean drawJumpBar,
            boolean drawMountHealth,
            boolean drawPortal,
            boolean drawPotions,
            boolean drawSleepFade,
            boolean drawSpyglass,
            boolean drawVignette
    ) {
        this.playerCanMove = playerCanMove;
        this.playerCanInteract = playerCanInteract;
        this.playerCanLookAround = playerCanLookAround;
        this.renderHand = renderHand;
        this.drawAir = drawAir;
        this.drawArmor = drawArmor;
        this.drawBossbar = drawBossbar;
        this.drawCrosshair = drawCrosshair;
        this.drawExperience = drawExperience;
        this.drawFood = drawFood;
        this.drawFrostbite = drawFrostbite;
        this.drawHealth = drawHealth;
        this.drawHelmet = drawHelmet;
        this.drawHotbar = drawHotbar;
        this.drawItemName = drawItemName;
        this.drawJumpBar = drawJumpBar;
        this.drawMountHealth = drawMountHealth;
        this.drawPortal = drawPortal;
        this.drawPotions = drawPotions;
        this.drawSleepFade = drawSleepFade;
        this.drawSpyglass = drawSpyglass;
        this.drawVignette = drawVignette;
    }

    // Getters & Setters
    public boolean isPlayerCanMove() { return playerCanMove; }
    public void setPlayerCanMove(boolean playerCanMove) { this.playerCanMove = playerCanMove; }

    public boolean isPlayerCanInteract() { return playerCanInteract; }
    public void setPlayerCanInteract(boolean playerCanInteract) { this.playerCanInteract = playerCanInteract; }

    public boolean isPlayerCanLookAround() { return playerCanLookAround; }
    public void setPlayerCanLookAround(boolean playerCanLookAround) { this.playerCanLookAround = playerCanLookAround; }

    public boolean isRenderHand() { return renderHand; }
    public void setRenderHand(boolean renderHand) { this.renderHand = renderHand; }

    public boolean isDrawAir() { return drawAir; }
    public void setDrawAir(boolean drawAir) { this.drawAir = drawAir; }

    public boolean isDrawArmor() { return drawArmor; }
    public void setDrawArmor(boolean drawArmor) { this.drawArmor = drawArmor; }

    public boolean isDrawBossbar() { return drawBossbar; }
    public void setDrawBossbar(boolean drawBossbar) { this.drawBossbar = drawBossbar; }

    public boolean isDrawCrosshair() { return drawCrosshair; }
    public void setDrawCrosshair(boolean drawCrosshair) { this.drawCrosshair = drawCrosshair; }

    public boolean isDrawExperience() { return drawExperience; }
    public void setDrawExperience(boolean drawExperience) { this.drawExperience = drawExperience; }

    public boolean isDrawFood() { return drawFood; }
    public void setDrawFood(boolean drawFood) { this.drawFood = drawFood; }

    public boolean isDrawFrostbite() { return drawFrostbite; }
    public void setDrawFrostbite(boolean drawFrostbite) { this.drawFrostbite = drawFrostbite; }

    public boolean isDrawHealth() { return drawHealth; }
    public void setDrawHealth(boolean drawHealth) { this.drawHealth = drawHealth; }

    public boolean isDrawHelmet() { return drawHelmet; }
    public void setDrawHelmet(boolean drawHelmet) { this.drawHelmet = drawHelmet; }

    public boolean isDrawHotbar() { return drawHotbar; }
    public void setDrawHotbar(boolean drawHotbar) { this.drawHotbar = drawHotbar; }

    public boolean isDrawItemName() { return drawItemName; }
    public void setDrawItemName(boolean drawItemName) { this.drawItemName = drawItemName; }

    public boolean isDrawJumpBar() { return drawJumpBar; }
    public void setDrawJumpBar(boolean drawJumpBar) { this.drawJumpBar = drawJumpBar; }

    public boolean isDrawMountHealth() { return drawMountHealth; }
    public void setDrawMountHealth(boolean drawMountHealth) { this.drawMountHealth = drawMountHealth; }

    public boolean isDrawPortal() { return drawPortal; }
    public void setDrawPortal(boolean drawPortal) { this.drawPortal = drawPortal; }

    public boolean isDrawPotions() { return drawPotions; }
    public void setDrawPotions(boolean drawPotions) { this.drawPotions = drawPotions; }

    public boolean isDrawSleepFade() { return drawSleepFade; }
    public void setDrawSleepFade(boolean drawSleepFade) { this.drawSleepFade = drawSleepFade; }

    public boolean isDrawSpyglass() { return drawSpyglass; }
    public void setDrawSpyglass(boolean drawSpyglass) { this.drawSpyglass = drawSpyglass; }

    public boolean isDrawVignette() { return drawVignette; }
    public void setDrawVignette(boolean drawVignette) { this.drawVignette = drawVignette; }
}
