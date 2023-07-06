package com.github.imajindevon.bluelib.net;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;

public final class HangarVersionFetcher {
    private static final String API_URL = "https://hangar.papermc.io/api/v1/projects/%s/%s/latestrelease";

    private HangarVersionFetcher() {
    }

    /**
     * Fetch the latest version of this Hangar project.
     *
     * @param author the author
     * @param slug   the slug
     *
     * @return the latest version
     *
     * @throws IOException        if an I/O error occurs
     * @throws ApiStatusException if an API error occurs
     */
    @SuppressWarnings("OverlyBroadThrowsClause")
    @NotNull
    public static String fetchLatestVersion(@NotNull String author, @NotNull String slug)
    throws IOException
    {
        URL url = new URL(API_URL.formatted(author, slug));
        String response = IOUtils.toString(url);
        try {
            JsonObject errorJson = JsonParser.parseString(response).getAsJsonObject();
            throw new ApiStatusException(errorJson.get("status").getAsInt(), errorJson.get("title").getAsString());
        } catch (JsonSyntaxException _exception) {
            return response;
        }
    }
}
