package com.github.imajindevon.bluelibtest;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfig;
import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfigInjector;
import com.github.imajindevon.bluelib.config.reflection.ReflectiveConfigWriter;
import com.github.imajindevon.bluelib.config.reflection.annotation.Optional;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
final class Test {
    private Test() {
    }

    @Data
    private static final class ExampleReflectiveConfig implements ReflectiveConfig {
        private String prefix = "&aTest: ";

        // Assure that Boolean will be converted to boolean
        private boolean testPrimitive = true;

        @Optional
        private String helloWorld = "&eHello, world!";

        private void reloadFrom(@NotNull ConfigurationSection section) {
            try {
                ReflectiveConfigInjector.getAndInject(this, section, true, true);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        }
    }

    private MockPlugin plugin = null;

    @BeforeAll
    void setup() {
        MockBukkit.mock();
        this.plugin = MockBukkit.createMockPlugin("BlueLibMockTests");
    }

    @org.junit.jupiter.api.Test
    @Order(0)
    void onEnable() {
        Assertions.assertDoesNotThrow(this.plugin::saveDefaultConfig);
    }

    @org.junit.jupiter.api.Test
    void configTest() {
        FileConfiguration fileConfig = this.plugin.getConfig();

        // Create a new reflective config, using the default values.
        ExampleReflectiveConfig config = new ExampleReflectiveConfig();

        // Our initial value has not changed.
        Assertions.assertEquals("&aTest: ", config.getPrefix());

        // Now we will change the value...
        fileConfig.set("prefix", "&cTest: ");

        // Then reload the config instance dynamically...
        config.reloadFrom(fileConfig);

        // Make sure the primitive works.
        Assertions.assertTrue(config.isTestPrimitive());

        // Now assert that the value has changed, and was translated.
        Assertions.assertEquals(ChatColor.RED + "Test: ", config.getPrefix());

        // Now we change the values in the instance...
        config.setPrefix(ChatColor.YELLOW + "Test: ");

        // Then write the values to the FileConfiguration...
        Assertions.assertDoesNotThrow(() -> ReflectiveConfigWriter.copyFields(config, fileConfig));

        // Then assert that the expected values have been copied to the FileConfiguration.
        Assertions.assertEquals(ChatColor.YELLOW + "Test: ", fileConfig.getString("prefix"));
    }

    @AfterAll
    void tearDown() {
        MockBukkit.unmock();
    }
}
