package com.github.imajindevon.bluelib.config.reflection;

import com.github.imajindevon.bluelib.config.PathConfig;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;

public class ReflectivePluginConfig<T extends ReflectiveConfig> {
    private final PathConfig configFile;
    private final Plugin plugin;
    private final T reflectiveConfig;

    public ReflectivePluginConfig(@NotNull Plugin plugin, @NotNull PathConfig configFile, @NotNull T reflectiveConfig) {
        this.plugin = plugin;
        this.configFile = configFile;
        this.reflectiveConfig = reflectiveConfig;
        this.updateFields();
    }

    /**
     * Get the inner reflective config.
     *
     * @return the config
     */
    @NotNull
    public T config() {
        return this.reflectiveConfig;
    }

    /**
     * Get the inner path config.
     *
     * @return the path config
     */
    @NotNull
    public PathConfig configFile() {
        return this.configFile;
    }

    /**
     * Copy the reflective config instance's fields into the configuration.
     */
    public void copyFields() {
        try {
            ReflectiveConfigWriter.copyFields(this.reflectiveConfig, this.configFile.config());
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Copy the reflective config instance's fields into the configuration, then save to the file asynchronously. If an
     * {@link IOException} is thrown, print the stack trace.
     *
     * @see #copySaveAsync(Consumer)
     */
    public void copySaveAsync() {
        this.copySaveAsync(IOException::printStackTrace);
    }

    /**
     * Copy the reflective config instance's fields into the configuration, then save to the file asynchronously.
     *
     * @see #copyFields()
     * @see #saveAsync(Consumer)
     */
    public void copySaveAsync(@NotNull Consumer<IOException> exceptionHandler) {
        this.copyFields();
        this.saveAsync(exceptionHandler);
    }

    /**
     * Reload this configuration from the file, and update the reflective config instance's fields with the new
     * entries.
     *
     * @throws IOException                   if an I/O error occurs
     * @throws InvalidConfigurationException if the configuration in the file is invalid
     * @throws FileNotFoundException         if the configuration file does not exist
     * @see #updateFields()
     * @see #tryReloadFromFile()
     */
    public void reloadFromFile() throws IOException, InvalidConfigurationException, FileNotFoundException {
        this.configFile.reload();
        this.updateFields();
    }

    /**
     * Save this configuration to its file.
     *
     * @throws IOException if an I/O error occurs
     * @see #saveAsync()
     * @see #saveAsync(Consumer)
     */
    public void save() throws IOException {
        this.configFile.save();
    }

    /**
     * Save this configuration to its file asynchronously.
     *
     * @param exceptionHandler the exception handler
     *
     * @see #saveAsync()
     */
    public void saveAsync(@NotNull Consumer<IOException> exceptionHandler) {
        this.configFile.saveAsync(this.plugin, exceptionHandler);
    }

    /**
     * Save this configuration to its file asynchronously.
     *
     * @see #saveAsync(Consumer)
     */
    public void saveAsync() {
        this.configFile.saveAsync(this.plugin);
    }

    /**
     * Call {@link #reloadFromFile()}, and if an exception is thrown, print the stack trace.
     *
     * @see #reloadFromFile()
     */
    public void tryReloadFromFile() {
        try {
            this.reloadFromFile();
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Copy values from the configuration in memory to the reflective config instance.
     */
    public void updateFields() {
        try {
            ReflectiveConfigInjector.getAndInject(this.reflectiveConfig, this.configFile.config(), true, true);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Load a configuration from the given path and copy the values into the reflective config instance.
     * <br>
     * If the file at the given path does not exist it will be created, and the values from the reflective config
     * instance will be copied into the configuration and saved asynchronously.
     *
     * @param plugin           the plugin
     * @param dataPath         the path to the file from the plugin's data folder
     * @param reflectiveConfig the reflective config instance
     * @param <T>              the type of the reflective config
     *
     * @return the new ReflectivePluginConfig
     *
     * @throws IOException                   if an I/O error occurs
     * @throws InvalidConfigurationException if the file exists and the stored configuration is invalid
     */
    @Contract("_, _, _ -> new")
    public static <T extends ReflectiveConfig> ReflectivePluginConfig<T> loadOrCopyDefaultsAsync(
        @NotNull Plugin plugin,
        @NotNull String dataPath,
        @NotNull T reflectiveConfig
    ) throws IOException, InvalidConfigurationException, FileNotFoundException
    {
        File file = new File(plugin.getDataFolder(), dataPath);
        PathConfig configFile = new PathConfig(file.getCanonicalPath(), new YamlConfiguration());
        ReflectivePluginConfig<T> self = new ReflectivePluginConfig<>(plugin, configFile, reflectiveConfig);

        if (file.createNewFile()) {
            self.copySaveAsync();
        } else {
            self.reloadFromFile();
        }
        return self;
    }
}