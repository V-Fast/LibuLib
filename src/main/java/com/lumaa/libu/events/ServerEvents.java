package com.lumaa.libu.events;

import com.lumaa.libu.LibuLibServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

@Environment(EnvType.SERVER)
public class ServerEvents implements ServerTickEvents.StartTick {
    @Override
    public void onStartTick(MinecraftServer server) {
        LibuLibServer.printUpdates();
    }
}
