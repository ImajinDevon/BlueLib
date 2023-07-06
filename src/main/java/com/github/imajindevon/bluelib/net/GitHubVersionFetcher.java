package com.github.imajindevon.bluelib.net;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class GitHubVersionFetcher {
    private static final String API_URL = "https://api.github.com/repos/%s/%s/releases/latest";

    private GitHubVersionFetcher() {
    }

    /**
     * Fetch the tag of the given repository's latest release.
     *
     * @param author     the author of the repository
     * @param repository the repository name
     *
     * @return the tag of the latest release
     *
     * @throws IOException  if an I/O error occurs
     * @throws ApiException if an API error occurs
     */
    @NotNull
    public static String fetchLatestRelease(@NotNull String author, @NotNull String repository)
    throws IOException
    {
        String url = API_URL.formatted(author, repository);
        JsonObject json = NetJsonUtil.fetchFrom(url);

        String errorMessage = json.get("message").getAsString();

        if (errorMessage != null) {
            throw new ApiException(errorMessage);
        }
        return json.get("tag_name").getAsString();
    }
}
