package com.lumaa.libu.update;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class UpdateChecker {
    private static ModrinthMod mod;
    private static JsonObject modObject;

    public UpdateChecker(ModrinthMod mod) {
        this.mod = mod;
        this.mod.setChecker(this);
    }

    /**
     * Find the latest Modrinth version
     * @return JSON of the latest Modrinth version
     */
    public JsonObject findLatestVersion() throws IOException {
        if (modObject != null) {
            return modObject;
        }

        URL modrinthUrl = new URL("https://api.modrinth.com/v2/project/%d/version".formatted(this.mod.slang));

        HttpURLConnection connection = (HttpURLConnection) modrinthUrl.openConnection();
        InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());

        BufferedReader reader = new BufferedReader(inputStream);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        modObject = JsonParser.parseString(sb.toString()).getAsJsonArray().get(0).getAsJsonObject();
        return modObject;
    }

    /**
     * Get a value from the JSON
     * @param memberName The name of the member
     * @return The value affiliated to the member
     */
    public String getString(String memberName) {
        Objects.requireNonNull(modObject);
        return modObject.get(memberName).getAsString().trim();
    }

    public static ModrinthMod getMod() {
        return mod;
    }
}
