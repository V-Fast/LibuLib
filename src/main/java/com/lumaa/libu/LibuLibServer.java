package com.lumaa.libu;

import com.lumaa.libu.items.ModItems;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.SERVER)
public class LibuLibServer implements DedicatedServerModInitializer {
    private static final Logger logger = LoggerFactory.getLogger("libu");

    private static final String versionId = FabricLoader.getInstance()
            .getModContainer("libu")
            .orElseThrow()
            .getMetadata()
            .getVersion()
            .getFriendlyString();

    @Override
    public void onInitializeServer() {
        ModItems.registerAll();
    }
}
