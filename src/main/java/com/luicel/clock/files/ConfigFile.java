package com.luicel.clock.files;

import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.models.Timer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

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
}
