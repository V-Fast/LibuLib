package com.lumaa.libu.client.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lumaa.libu.LibuLib;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LibuSettings {
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve( "libu.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static LibuSettings INSTANCE;

    private static LibuSettings getInstance() {
        if (INSTANCE == null) {
            loadConfig();
        }

        return INSTANCE;
    }

    public static void saveConfig() {
        if (INSTANCE != null) {
            writeFile(INSTANCE);
        }
    }

    public static void loadConfig() {
        LibuLib.logger.info(INSTANCE == null ? "Loading config..." : "Reloading config...");

        INSTANCE = readFile();

        if (INSTANCE == null) {
            INSTANCE = new LibuSettings();
        }

        writeFile(INSTANCE);
    }

    @Nullable
    private static LibuSettings readFile() {
        if (!Files.isRegularFile(CONFIG_FILE))
            return null;

        try (BufferedReader reader = Files.newBufferedReader(CONFIG_FILE)) {
            return GSON.fromJson(reader, LibuSettings.class);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void writeFile(LibuSettings instance) {
        try (BufferedWriter writer = Files.newBufferedWriter(CONFIG_FILE)) {
            GSON.toJson(instance, writer);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
