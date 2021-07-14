package com.luicel.clock.files;

import com.luicel.clock.annotations.FileDirectory;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@FileDirectory("")
public class ConfigFile extends Files {
    public static YamlConfiguration ymlConfig;
    public static File file;

    public ConfigFile(String fileName, String directory) {
        ymlConfig = createConfig(fileName, directory);
        file = getFile(fileName);
    }

    public static String getString(String path) {
        return ymlConfig.getString(path);
    }

    public static boolean getBoolean(String path) {
        return ymlConfig.getBoolean(path);
    }

    public static void reload() {
        try {
            ymlConfig.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }
}
