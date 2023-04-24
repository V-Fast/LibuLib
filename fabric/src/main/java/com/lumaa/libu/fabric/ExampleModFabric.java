package com.lumaa.libu.fabric;

import com.lumaa.libu.LibuLib;
import net.fabricmc.api.ModInitializer;

public class ExampleModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        LibuLib.init();
    }
}
