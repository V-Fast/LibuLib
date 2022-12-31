package com.lumaa.libu;

import com.lumaa.libu.update.ModrinthMod;
import com.lumaa.libu.update.UpdateChecker;
import com.lumaa.libu.update.UpdateScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

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
            updates.add(updateChecker); // add independently to avoid log
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

    /**
     * Displays all the added updates if there are
     * @see com.lumaa.libu.LibuLib#addUpdate(UpdateChecker)
     */
    public static void displayUpdates() {
        if (getUpdates().size() > 0) {
            getUpdates().forEach(update -> {
                try {
                    if (!update.getString("version_number").equals(update.getMod().versionId) && !update.isShown()) {
                        MinecraftClient client = MinecraftClient.getInstance();
                        if (update.getMod().versionId.toLowerCase(Locale.ROOT).trim().equals("dev")) {
                            update.setShown(true);
                            logger.info(update.getMod().name + "'s Developer version prevented an update.");
                        } else {
                            update.setShown(true);
                            client.setScreen(new UpdateScreen(client.currentScreen, update.getMod(), Text.translatable("update.libulib.title", update.getMod().name), Text.translatable("update.libulib.description", update.getMod().name, update.getMod().versionId, update.getString("version_number"), update.getMod().name)));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            logger.error("[LibuLib] No mods in UpdateChecker list");
        }
    }
}
