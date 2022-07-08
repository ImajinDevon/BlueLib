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

public class ConfigFile {
    private final FileConfiguration config;
    private final File configFile;

    /**
     * @param configFile the file that the config originates from
     * @param config     the yaml configuration
     */
    public ConfigFile(@NotNull File configFile, @NotNull YamlConfiguration config) {
        this.configFile = configFile;
        this.config = config;
    }

    /**
     * Load the config from the plugin's data folder, and the provided file path.
     * If the configuration file is invalid, it will use the default configuration file.
     * If the file does not exist, {@link Plugin#saveResource} will be called to create it.
     * This method's functionality is equivalent to that of {@link Plugin#saveDefaultConfig()}.
     * If an error occurs, the stack trace will be printed.
     *
     * @param plugin   plugin
     * @param filePath the file path
     * @return the new Config
     * @throws IllegalArgumentException if the file path is empty or points to a nonexistent resource
     */
    @Contract("_, _ -> new")
    public static ConfigFile dumbLoad(@NotNull Plugin plugin, @NotNull String filePath) {
        return dumbLoad(plugin, filePath, Throwable::printStackTrace);
    }

    /**
     * Load the config from the plugin's data folder, and the provided file path.
     * If the configuration file is invalid, it will use the default configuration file.
     * If the file does not exist, {@link Plugin#saveResource} will be called to create it.
     * This method's functionality is equivalent to that of {@link Plugin#saveDefaultConfig()},
     *
     * @param plugin           plugin
     * @param filePath         the file path
     * @param exceptionHandler the exception handler
     * @return the new Config
     * @throws IllegalArgumentException if the file path is empty or points to a nonexistent resource
     */
    @Contract("_, _, _ -> new")
    public static ConfigFile dumbLoad(@NotNull Plugin plugin, @NotNull String filePath,
                                      Consumer<Throwable> exceptionHandler) {
        File file = new File(plugin.getDataFolder(), filePath);

        if (file.exists()) {
            try {
                return loadConfig(file);
            } catch (IOException | InvalidConfigurationException exception) {
                exceptionHandler.accept(exception);
            }
        } else {
            plugin.saveResource(filePath, false);
        }
        return new ConfigFile(file, YamlConfiguration.loadConfiguration(file));
    }

    /**
     * Load a Config from the given file.
     * Calls {@link YamlConfiguration#load(File)} to load the {@link YamlConfiguration}.
     *
     * @param file the file
     * @return the new Config
     * @throws FileNotFoundException         if the file does not exist
     * @throws IOException                   if an I/O error occurs
     * @throws InvalidConfigurationException if the file is not a valid configuration
     */
    @Contract("_ -> new")
    public static ConfigFile loadConfig(@NotNull File file) throws IOException, InvalidConfigurationException,
                                                                       FileNotFoundException {
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        return new ConfigFile(file, config);
    }

    /**
     * Get the inner {@link FileConfiguration}.
     *
     * @return the inner {@link FileConfiguration}
     */
    @NotNull
    public FileConfiguration getConfig() {
        return this.config;
    }

    /**
     * Get a string from the config.
     *
     * @param path the path to the string
     * @return the string, or null if it doesn't exist
     */
    @Nullable
    public String getString(@NotNull String path) {
        return this.config.getString(path);
    }

    /**
     * Get an int from the config, or -1 if it doesnt exist.
     *
     * @param path the path to the int
     * @return the int, or -1 if it doesn't exist
     */
    public int getInt(@NotNull String path) {
        return this.config.getInt(path, -1);
    }

    /**
     * Get an int from the config, or the default value if it doesn't exist.
     *
     * @param path         the path to the int
     * @param defaultValue the default value
     * @return the int, or the default value if it doesn't exist
     */
    public int getInt(@NotNull String path, int defaultValue) {
        return this.config.getInt(path, defaultValue);
    }

    /**
     * Get a double from the config, or -1.0 if it doesn't exist.
     *
     * @param path the path to the double
     * @return the double, or -1.0 if it doesn't exist
     */
    public double getDouble(@NotNull String path) {
        return this.config.getDouble(path, -1.0);
    }

    /**
     * Get a double from the config, or the default value if it doesn't exist.
     *
     * @param path         the path to the double
     * @param defaultValue the default value
     * @return the double, or the default value if it doesn't exist
     */
    public double getDouble(@NotNull String path, double defaultValue) {
        return this.config.getDouble(path, defaultValue);
    }

    /**
     * Get a boolean from the config, or false if it doesn't exist.
     *
     * @param path the path to the boolean
     * @return the boolean, or false if it doesn't exist
     */
    public boolean getBoolean(@NotNull String path) {
        return this.config.getBoolean(path);
    }

    /**
     * Get a boolean from the config, or the default value if it doesn't exist.
     *
     * @param path         the path to the boolean
     * @param defaultValue the default value
     * @return the boolean, or the default value if it doesn't exist
     */
    public boolean getBoolean(@NotNull String path, boolean defaultValue) {
        return this.config.getBoolean(path, defaultValue);
    }

    /**
     * Get a serializable object from the given path.
     *
     * @param path  the path
     * @param clazz the class of the type
     * @param <T>   the type of object to deserialize
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
     * @return the object, or the default value if it doesn't exist
     */
    @Nullable
    public <T extends ConfigurationSerializable> T getSerializable(
        @NotNull String path, @NotNull Class<T> clazz, @NotNull T def
    ) {
        return this.config.getSerializable(path, clazz, def);
    }

    /**
     * Get a string from the config, or an empty string if it doesn't exist.
     *
     * @param path the path to the string
     * @return the string, or an empty string if it doesn't exist
     */
    @NotNull
    public String getStringNonNull(@NotNull String path) {
        return this.config.getString(path, "");
    }

    /**
     * Get a string from the config, or the default value if it doesn't exist.
     *
     * @param path         the path to the string
     * @param defaultValue the default value
     * @return the string, or the default value if it doesn't exist
     */
    public String getString(@NotNull String path, @NotNull String defaultValue) {
        return this.config.getString(path, defaultValue);
    }

    /**
     * Get and translate a string from the config, or an empty string if it doesn't exist.
     *
     * @param path the path to the string
     * @return the translated string, or an empty string if it doesn't exist
     */
    @NotNull
    @Colored
    public String getTranslated(@NotNull String path) {
        return ChatUtil.translate(this.config.getString(path));
    }

    /**
     * Save the configuration to the file asynchronously.
     * In the case of an exception, the stack trace will be printed to the console.
     *
     * @param caller the plugin that is calling this method
     */
    public void saveAsync(@NotNull Plugin caller) {
        this.saveAsync(caller, IOException::printStackTrace);
    }

    /**
     * Save this configuration to the file asynchronously.
     *
     * @param caller           the plugin that is calling this method
     * @param exceptionHandler the exception handler to use when saving the configuration
     */
    public void saveAsync(@NotNull Plugin caller, @NotNull Consumer<IOException> exceptionHandler) {
        Bukkit.getScheduler().runTaskAsynchronously(caller, () -> {
            try {
                this.config.save(this.configFile);
            } catch (IOException exception) {
                exceptionHandler.accept(exception);
            }
        });
    }

    /**
     * Reload the configuration from the file.
     *
     * @throws IOException                   if an I/O error occurs
     * @throws InvalidConfigurationException if the configuration is invalid
     * @throws FileNotFoundException         if the file does not exist
     */
    public void reload() throws IOException, InvalidConfigurationException, FileNotFoundException {
        this.config.load(this.configFile);
    }
}
