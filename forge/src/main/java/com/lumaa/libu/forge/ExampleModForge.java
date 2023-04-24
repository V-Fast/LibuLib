package com.lumaa.libu.forge;

import com.lumaa.libu.LibuLib;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LibuLib.ID)
public class ExampleModForge {
    public ExampleModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(LibuLib.ID, FMLJavaModLoadingContext.get().getModEventBus());
        LibuLib.init();
    }
}
