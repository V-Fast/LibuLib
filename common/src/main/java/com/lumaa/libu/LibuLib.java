package com.lumaa.libu;

import com.lumaa.libu.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibuLib {
    public static final Logger logger = LoggerFactory.getLogger("libu");
    public static final String ID = "libu";

    public static void init() {
        ModItems.registerAll();
    }
}
