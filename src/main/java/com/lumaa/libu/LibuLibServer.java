package com.lumaa.libu;

import com.lumaa.libu.update.ModrinthMod;
import com.lumaa.libu.update.UpdateChecker;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Environment(EnvType.SERVER)
public class LibuLibServer implements DedicatedServerModInitializer {
    private static final Logger logger = LoggerFactory.getLogger("libu");

    private static ArrayList<UpdateChecker> updates = new ArrayList<UpdateChecker>();
    private static final String versionId = FabricLoader.getInstance()
            .getModContainer("libu")
            .orElseThrow()
            .getMetadata()
            .getVersion()
            .getFriendlyString();
    public static final UpdateChecker updateChecker = new UpdateChecker(new ModrinthMod("LibuLib", "libu", versionId));

    @Override
    public void onInitializeServer() {
        updates.add(updateChecker); // dodge logs
    }

    /**
     * Add an update to the update queue
     * @param update An {@link UpdateChecker}
     */
    public static void addUpdate(UpdateChecker update) {
        if (updates != null && !updates.contains(update)) {
            updates.add(update);
            logger.info("[LibuLib] Found compatibility with " + update.getMod().name);
        }
    }

    /**
     * Prints all the required updates if there are
     */
    public static void printUpdates() {
        if (updates.size() > 0) {
            updates.forEach(update -> {
                if (!update.getString("version_number").equals(update.getMod().versionId) && !update.isShown()) {
                    if (update.getMod().versionId.toLowerCase().trim().equals("dev")) {
                        update.setShown(true);
                        logger.info(update.getMod().name + "'s Developer version prevented an update message.");
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
     * Send a message to a player all the required updates if there are
     */
    public static void sendUpdates(ServerPlayerEntity player) {
        if (updates.size() > 0) {
            updates.forEach(update -> {
                if (!update.getString("version_number").equals(update.getMod().versionId) && !update.isShown()) {
                    if (update.getMod().versionId.toLowerCase().trim().equals("dev")) {
                        update.setShown(true);
                        Text a = Text.literal("[LibuLib] This server uses a developer version of %s.".formatted(update.getMod().name));
                        player.sendMessageToClient(a.getWithStyle(a.getStyle().withColor(Color.brand)).get(0), false);
                    } else {
                        update.setShown(true);
                        Text b = Text.literal("[LibuLib] This server uses an outdated version of %s.".formatted(update.getMod().name));
                        player.sendMessageToClient(b.getWithStyle(b.getStyle().withColor(Color.brand)).get(0), false);
                    }
                }
            });
        } else {
            logger.error("[LibuLib] No mods in UpdateChecker list");
        }
    }
}
