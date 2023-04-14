package com.lumaa.libu;

import com.lumaa.libu.client.TestScreen;
import com.lumaa.libu.client.ui.LibuToast;
import com.lumaa.libu.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class LibuLibClient implements ClientModInitializer {
    public static final Logger logger = LoggerFactory.getLogger("libu");
    private static final String ID = "libu";

    private static final String versionId = FabricLoader.getInstance()
            .getModContainer("libu")
            .orElseThrow()
            .getMetadata()
            .getVersion()
            .getFriendlyString();

    @Override
    public void onInitializeClient() {
        logger.info("[LibuLib] Awakened");

        ModItems.registerAll();
    }
}
