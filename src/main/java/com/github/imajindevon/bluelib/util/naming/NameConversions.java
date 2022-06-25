package com.github.imajindevon.bluelib.util.naming;

import org.jetbrains.annotations.NotNull;

public final class NameConversions {
    private NameConversions() {
    }

    /**
     * Returns an all-lowercase version of the given string, with all uppercase letters preceded by the given separator.
     *
     * @param separator the separator to use
     * @param name      the name to convert
     * @return the converted string
     */
    @NotNull
    public static String separateUppercase(char separator, @NotNull String name) {
        StringBuilder result = new StringBuilder(name.length() << 1);
        boolean lastWasNotUpper = true;

        for (char c : name.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (lastWasNotUpper) {
                    result.append(separator);
                }
                lastWasNotUpper = false;
            } else {
                lastWasNotUpper = true;
            }
            result.append(c);
        }
        return result.toString();
    }
}
