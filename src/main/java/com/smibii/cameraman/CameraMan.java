package com.smibii.cameraman;

import com.mojang.logging.LogUtils;
import com.smibii.cameraman.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CameraMan.MODID)
public class CameraMan {
    public static final String MODID = "cameraman";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public CameraMan() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        NetworkHandler.register();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup done!");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server setup done!");
    }
}
