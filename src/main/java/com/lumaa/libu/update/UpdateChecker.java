package com.lumaa.libu.update;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker {
    private static ModrinthMod mod;
    public UpdateChecker(ModrinthMod mod) {

    }

    /**
     * Find the latest Modrinth version
     * @param modrinthUrl Something like "https://api.modrinth.com/v2/project/backrooms/version"
     * @return JSON of the latest Modrinth version
     */
    public static JsonObject findLatestVersion(URL modrinthUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) modrinthUrl.openConnection();
        InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());

        BufferedReader reader = new BufferedReader(inputStream);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return JsonParser.parseString(sb.toString()).getAsJsonArray().get(0).getAsJsonObject();
    }
}
