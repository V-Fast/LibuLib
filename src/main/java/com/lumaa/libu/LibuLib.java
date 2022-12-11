package com.lumaa.libu;

import com.lumaa.libu.update.ModrinthMod;
import com.lumaa.libu.update.UpdateChecker;
import net.fabricmc.api.ClientModInitializer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class LibuLib implements ClientModInitializer {
    private static final Logger logger = Logger.getLogger("libu");
    private static final String ID = "libu";

    private static List<UpdateChecker> updates;
    public static final UpdateChecker updateChecker = new UpdateChecker(new ModrinthMod("LibuLib", "libulib", "Dev"));
    public static final String version = "A1";
    public static final boolean published = false;

    @Override
    public void onInitializeClient() {
        logger.info("LibuLib has awaken");

        updates.forEach((update -> {
            try {
                update.findLatestVersion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        if (published) {
            try {
                updateChecker.findLatestVersion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<UpdateChecker> getUpdates() {
        return updates;
    }

    public static void setUpdates(List<UpdateChecker> updates) {
        LibuLib.updates = updates;
    }

    public static void addUpdate(UpdateChecker update) {
        LibuLib.updates.add(update);
    }
}
