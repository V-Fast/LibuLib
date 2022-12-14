package com.lumaa.libu;

import com.lumaa.libu.update.ModrinthMod;
import com.lumaa.libu.update.UpdateChecker;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LibuLib implements ClientModInitializer {
    public static final Logger logger = LoggerFactory.getLogger("libu");
    private static final String ID = "libu";

    private static List<UpdateChecker> updates = Arrays.asList();
    public static final UpdateChecker updateChecker = new UpdateChecker(new ModrinthMod("LibuLib", "libulib", "Dev"));
    public static final String version = "Dev";
    public static final boolean published = false;

    @Override
    public void onInitializeClient() {
        logger.info("LibuLib has awaken");

        if (published) {
            try {
                updateChecker.findLatestVersion();
                addUpdate(updateChecker);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<UpdateChecker> getUpdates() {
        return updates;
    }

    public static void addUpdate(UpdateChecker update) {
        LibuLib.updates.add(update);
        LibuLib.logger.info("[LibuLib] Will check %d".formatted(update.getMod().name));
    }
}
