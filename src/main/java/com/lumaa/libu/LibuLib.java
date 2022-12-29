package com.lumaa.libu;

import com.lumaa.libu.update.ModrinthMod;
import com.lumaa.libu.update.UpdateChecker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class LibuLib implements ClientModInitializer {
    public static final Logger logger = LoggerFactory.getLogger("libu");
    private static final String ID = "libu";

    private static ArrayList<UpdateChecker> updates = new ArrayList<UpdateChecker>();
    private static final String versionId = FabricLoader.getInstance()
            .getModContainer("libu")
            .orElseThrow()
            .getMetadata()
            .getVersion()
            .getFriendlyString();
    public static final UpdateChecker updateChecker = new UpdateChecker(new ModrinthMod("LibuLib", "libu", versionId));

    @Override
    public void onInitializeClient() {
        logger.info("[LibuLib] has awakened");

        try {
            updateChecker.findLatestVersion();
            LibuLib.updates.add(updateChecker); // add independently to avoid log
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<UpdateChecker> getUpdates() {
        return updates;
    }

    public static void addUpdate(UpdateChecker update) {
        if (updates instanceof ArrayList<UpdateChecker> && !updates.contains(update)) {
            LibuLib.updates.add(update);
            LibuLib.logger.info("[LibuLib] Found compatibility with " + update.getMod().name);
        }
    }
}
