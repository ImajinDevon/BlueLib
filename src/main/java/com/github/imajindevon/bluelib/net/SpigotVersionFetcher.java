package com.github.imajindevon.bluelib.net;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class SpigotVersionFetcher {
    private static final String API_URL = "https://api.spigotmc.org/simple/0.1/index.php?action=getResource&id=";

    private SpigotVersionFetcher() {
    }

    /**
     * Fetch the latest version of this Spigot resource.
     *
     * @param resourceId the resource's ID
     *
     * @return the latest version
     *
     * @throws IOException        if an IO error occurs
     * @throws ApiStatusException if the API returns an error
     */
    @NotNull
    public static String fetchLatestVersion(int resourceId) throws IOException {
        JsonObject info = NetJsonUtil.fetchFrom(API_URL + resourceId);

        if (info.has("code")) {
            throw new ApiStatusException(info.get("code").getAsInt(), info.get("message").getAsString());
        }
        return info.get("current_version").getAsString();
    }
}
