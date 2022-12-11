package com.lumaa.libu;

import net.fabricmc.api.ClientModInitializer;

import java.util.logging.Logger;

public class LibuLib implements ClientModInitializer {
    private static final Logger logger = Logger.getLogger("libu");
    private static final String ID = "libu";

    public static final String version = "A1";

    @Override
    public void onInitializeClient() {
        logger.info("LibuLib has awaken");
    }
}
