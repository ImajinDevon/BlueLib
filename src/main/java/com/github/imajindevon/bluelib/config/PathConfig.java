package com.github.imajindevon.bluelib.config;

import com.github.imajindevon.bluelib.chat.ChatUtil;
import com.github.imajindevon.bluelib.chat.annotation.Colored;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * A class that manages a configuration and its file path.
 */
public class PathConfig {
    private final FileConfiguration config;
    private final String filePath;

    /**
     * @param filePath the path where the config is stored
     * @param config   the file configuration
     */
    public PathConfig(@NotNull String filePath, @NotNull FileConfiguration config) {
        this.filePath = filePath;
        this.config = config;
    }

    /**
     * Get the inner {@link FileConfiguration}.
     *
     * @return the inner configuration
     */
    @NotNull
    public FileConfiguration config() {
        return this.config;
    }

    /**
     * Get the path to this configuration's file.
     *
     * @return the path
     */
    @NotNull
    public String filePath() {
        return this.filePath;
    }

    /**
     * Get a boolean from the config, or false if it doesn't exist.
     *
     * @param path the path to the boolean
     *
     * @return the boolean, or false if it doesn't exist
     */
    public boolean getBoolean(@NotNull String path) {
        return this.config.getBoolean(path);
    }

    /**
     * Get a boolean from the config, or the default value if it doesn't exist.
     *
     * @param path the path to the boolean
     * @param def  the default value
     *
     * @return the boolean, or the default value if it doesn't exist
     */
    public boolean getBoolean(@NotNull String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    /**
     * Get a double from the config, or -1.0 if it doesn't exist.
     *
     * @param path the path to the double
     *
     * @return the double, or -1.0 if it doesn't exist
     */
    public double getDouble(@NotNull String path) {
        return this.config.getDouble(path, -1.0);
    }

    /**
     * Get a double from the config, or the default value if it doesn't exist.
     *
     * @param path the path to the double
     * @param def  the default value
     *
     * @return the double, or the default value if it doesn't exist
     */
    public double getDouble(@NotNull String path, double def) {
        return this.config.getDouble(path, def);
    }

    /**
     * Get an int from the config, or -1 if it doesn't exist.
     *
     * @param path the path to the int
     *
     * @return the int, or -1 if it doesn't exist
     */
    public int getInt(@NotNull String path) {
        return this.config.getInt(path, -1);
    }

    /**
     * Get an int from the config, or the default value if it doesn't exist.
     *
     * @param path the path to the int
     * @param def  the default value
     *
     * @return the int, or the default value if it doesn't exist
     */
    public int getInt(@NotNull String path, int def) {
        return this.config.getInt(path, def);
    }

    /**
     * Get a serializable object from the given path.
     *
     * @param path  the path
     * @param clazz the class of the type
     * @param <T>   the type of object to deserialize
     *
     * @return the object, or null if it doesn't exist
     */
    @Nullable
    public <T extends ConfigurationSerializable> T getSerializable(@NotNull String path, @NotNull Class<T> clazz) {
        return this.config.getSerializable(path, clazz);
    }

    /**
     * Get a serializable object from the given path, or the default if it doesn't exist.
     *
     * @param path  the path
     * @param clazz the class of the type
     * @param def   the default value
     * @param <T>   the type of object to deserialize
     *
     * @return the object, or the default value if it doesn't exist
     */
    @NotNull
    public <T extends ConfigurationSerializable> T getSerializable(
        @NotNull String path, @NotNull Class<T> clazz, @NotNull T def
    )
    {
        return this.config.getSerializable(path, clazz, def);
    }

    /**
     * Get a string from the config.
     *
     * @param path the path to the string
     *
     * @return the string, or null if it doesn't exist
     *
     * @see #getString(String, String)
     * @see #getStringNonNull(String)
     * @see #getTranslated(String)
     */
    @Nullable
    public String getString(@NotNull String path) {
        return this.config.getString(path);
    }

    /**
     * Get a string from the config, or the default value if it doesn't exist.
     *
     * @param path the path to the string
     * @param def  the default value
     *
     * @return the string, or the default value if it doesn't exist
     *
     * @see #getString(String)
     * @see #getStringNonNull(String)
     * @see #getTranslated(String)
     */
    @Contract("_, null -> null; _, !null -> !null")
    public String getString(@NotNull String path, String def) {
        return this.config.getString(path, def);
    }

    /**
     * Get a string from the config, or an empty string if it doesn't exist.
     *
     * @param path the path to the string
     *
     * @return the string, or an empty string if it doesn't exist
     *
     * @see #getString(String)
     * @see #getString(String, String)
     * @see #getTranslated(String)
     */
    @NotNull
    public String getStringNonNull(@NotNull String path) {
        return this.config.getString(path, "");
    }

    /**
     * Get and translate a string from the config, or an empty string if it doesn't exist.
     *
     * @param path the path to the string
     *
     * @return the translated string, or an empty string if it doesn't exist
     */
    @NotNull
    @Colored
    public String getTranslated(@NotNull String path) {
        return ChatUtil.translate(this.config.getString(path));
    }

    /**
     * Reload the configuration from its file.
     *
     * @throws IOException                   if an I/O error occurs
     * @throws InvalidConfigurationException if the configuration is invalid
     * @throws FileNotFoundException         if the file does not exist
     */
    public void reload() throws IOException, InvalidConfigurationException, FileNotFoundException {
        this.config.load(this.filePath);
    }

    /**
     * Save this configuration to its file synchronously.
     *
     * @throws IOException if an I/O error occurs
     * @see #saveAsync(Plugin)
     * @see #saveAsync(Plugin, Consumer)
     */
    public void save() throws IOException {
        this.config.save(this.filePath);
    }

    /**
     * Save the configuration to its file asynchronously. If an exception is thrown, print the stack trace.
     *
     * @param caller the reference to the plugin scheduling task
     *
     * @see #save()
     * @see #saveAsync(Plugin, Consumer)
     */
    public void saveAsync(@NotNull Plugin caller) {
        this.saveAsync(caller, IOException::printStackTrace);
    }

    /**
     * Save this configuration to its file asynchronously.
     *
     * @param caller           the reference to the plugin scheduling task
     * @param exceptionHandler the exception handler
     *
     * @see #save()
     * @see #saveAsync(Plugin)
     */
    public void saveAsync(@NotNull Plugin caller, @NotNull Consumer<IOException> exceptionHandler) {
        Bukkit.getScheduler().runTaskAsynchronously(caller, () -> {
            try {
                this.save();
            } catch (IOException exception) {
                exceptionHandler.accept(exception);
            }
        });
    }

    /**
     * Load the config from the plugin's data folder and the provided file path. If the configuration file is invalid,
     * the resource will be loaded from the plugin JAR. If the file does not exist, {@link Plugin#saveResource} will be
     * called to copy it into the plugin's data folder. This method's functionality is equivalent to that of
     * {@link Plugin#saveDefaultConfig()},
     *
     * @param plugin   plugin
     * @param filePath the file path
     *
     * @return the new config
     */
    @Contract("_, _ -> new")
    public static PathConfig dumbLoad(@NotNull Plugin plugin, @NotNull String filePath)
    throws IOException, InvalidConfigurationException, FileNotFoundException
    {
        File file = new File(plugin.getDataFolder(), filePath);

        if (file.exists()) {
            return loadConfig(file);
        }
        plugin.saveResource(filePath, false);
        return new PathConfig(file.getCanonicalPath(), YamlConfiguration.loadConfiguration(file));
    }

    /**
     * Load a YAML configuration from the given file.
     *
     * @param file the file
     *
     * @return the new config
     *
     * @throws FileNotFoundException         if the file does not exist
     * @throws IOException                   if an I/O error occurs
     * @throws InvalidConfigurationException if the file is not a valid configuration
     */
    @Contract("_ -> new")
    public static PathConfig loadConfig(@NotNull File file) throws IOException, InvalidConfigurationException,
        FileNotFoundException
    {
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        return new PathConfig(file.getCanonicalPath(), config);
    }
}
