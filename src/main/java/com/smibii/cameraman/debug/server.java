package com.smibii.cameraman.debug;

import com.smibii.cameraman.CameraMan;
import com.smibii.cameraman.network.CameraPlayerToPointPacket;
import com.smibii.cameraman.network.CameraSetInUsePacket;
import com.smibii.cameraman.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = CameraMan.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class server {
    private static final Map<ServerPlayer, Long> lastTeleportTick = new HashMap<>();
    private static final Map<ServerPlayer, Long> teleportDelays = new HashMap<>();

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        long currentTick = event.getServer().getTickCount();

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            double y = player.getY();

            if (y < 70) {
                Long lastTick = lastTeleportTick.get(player);
                if (lastTick == null || currentTick - lastTick > 100) {
                    NetworkHandler.INSTANCE.sendTo(
                            new CameraPlayerToPointPacket(register.SPAWN.name()),
                            player.connection.connection,
                            NetworkDirection.PLAY_TO_CLIENT
                    );
                    player.setGameMode(GameType.SPECTATOR);

                    teleportDelays.put(player, currentTick + 20); // 20 ticks = 1 second
                    lastTeleportTick.put(player, currentTick);
                }
            } else {
                lastTeleportTick.remove(player);
            }
        }

        teleportDelays.entrySet().removeIf(entry -> {
            ServerPlayer target = entry.getKey();
            long scheduledTick = entry.getValue();

            if (currentTick >= scheduledTick) {
                target.teleportTo(
                        target.serverLevel(),
                        register.SPAWN.point().pos().x,
                        register.SPAWN.point().pos().y - 1.62,
                        register.SPAWN.point().pos().z,
                        register.SPAWN.point().yaw(),
                        register.SPAWN.point().pitch()
                );

                NetworkHandler.INSTANCE.sendTo(
                        new CameraSetInUsePacket(false),
                        target.connection.connection,
                        NetworkDirection.PLAY_TO_CLIENT
                );

                target.setGameMode(GameType.DEFAULT_MODE);
                return true; // remove after running
            }

            return false;
        });
    }
}