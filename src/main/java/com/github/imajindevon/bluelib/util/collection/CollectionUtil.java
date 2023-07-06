package com.github.imajindevon.bluelib.util.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class CollectionUtil {
    private CollectionUtil() {
    }

    /**
     * Returns a list containing each element of {@code collection} that starts with the given string.
     *
     * @param string     the argument being typed
     * @param collection the collection to filter
     *
     * @return the list
     */
    @Contract("_, _ -> new")
    public static List<String> filterStartsWith(
        @NotNull String string,
        @NotNull Collection<String> collection
    )
    {
        return collection
            .stream()
            .filter(r -> r.startsWith(string))
            .collect(Collectors.toList());
    }
}
