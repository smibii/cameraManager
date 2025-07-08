package com.smibii.cameraman.debug.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.smibii.cameraman.CameraMan;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CameraMan.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class registerCommands {
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        transitionFromPlayer.register(dispatcher);
        transitionToPlayer.register(dispatcher);
    }
}
