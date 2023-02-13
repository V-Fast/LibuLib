package com.lumaa.libu;

import com.lumaa.libu.items.ModItems;
import com.lumaa.libu.update.ModrinthMod;
import com.lumaa.libu.update.UpdateChecker;
import com.lumaa.libu.update.UpdateScreen;
import com.lumaa.libu.util.ModSettings;
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

@Environment(EnvType.CLIENT)
public class LibuLibClient implements ClientModInitializer {
    public static final ModSettings settings = new ModSettings();
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
    private static boolean updateDisplayed = false;

    @Override
    public void onInitializeClient() {
        logger.info("[LibuLib] Awakened");

        ModItems.registerAll();

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

    /**
     * Add an update to the update queue
     * @param update An {@link UpdateChecker}
     */
    public static void addUpdate(UpdateChecker update) {
        if (updates != null && !updates.contains(update)) {
            LibuLibClient.updates.add(update);
            LibuLibClient.logger.info("[LibuLib] Found compatibility with " + update.getMod().name);
        }
    }

    /**
     * Displays using {@link UpdateScreen} all the required updates if there are
     * @see LibuLibClient#addUpdate(UpdateChecker)
     * @see LibuLibClient#displayUpdates()
     */
    public static void displayUpdates() {
        updateDisplayed = true;
        if (getUpdates().size() > 0) {
            getUpdates().forEach(update -> {
                try {
                    if (!update.getString("version_number").equals(update.getMod().versionId) && !update.isShown()) {
                        MinecraftClient client = MinecraftClient.getInstance();
                        if (update.getMod().versionId.toLowerCase().trim().equals("dev")) {
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

    /**
     * Prints all the required updates if there are
     * @see LibuLibClient#addUpdate(UpdateChecker)
     * @see LibuLibClient#displayUpdates()
     */
    public static void printUpdates() {
        updateDisplayed = true;
        if (getUpdates().size() > 0) {
            getUpdates().forEach(update -> {
                if (!update.getString("version_number").equals(update.getMod().versionId) && !update.isShown()) {
                    if (update.getMod().versionId.toLowerCase().trim().equals("dev")) {
                        update.setShown(true);
                        logger.info(update.getMod().name + "'s Developer version prevented an update.");
                    } else {
                        update.setShown(true);
                        logger.warn("%s requires an update for version %s!".formatted(update.getString(update.getMod().name), update.getString("version_number")));
                    }
                }
            });
        } else {
            logger.error("[LibuLib] No mods in UpdateChecker list");
        }
    }

    /**
     * Returns a boolean if the {@link LibuLibClient#displayUpdates()} has been called before or not
     * @return Boolean if updates were displayed
     */
    public static boolean hasUpdateDisplayed() {
        return updateDisplayed;
    }
}
