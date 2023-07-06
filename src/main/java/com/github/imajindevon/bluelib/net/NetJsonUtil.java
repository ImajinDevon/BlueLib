package com.github.imajindevon.bluelib.net;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Class containing utility methods for fetching JSON from the internet.
 */
public final class NetJsonUtil {
    private NetJsonUtil() {
    }

    /**
     * Try to fetch a {@link JsonObject} from the given URL.
     *
     * @param url the url
     *
     * @return the json object
     *
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("OverlyBroadThrowsClause")
    @NotNull
    public static JsonObject fetchFrom(@NotNull String url) throws IOException {
        String response = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        return JsonParser.parseString(response).getAsJsonObject();
    }
}
