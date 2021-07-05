package com.luicel.clock.files;

import com.luicel.clock.Clock;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Files {
    public static YamlConfiguration ymlConfig;
    public static File file;

    public static String FOLDER = Clock.getInstance().getDataFolder() + "\\";

    public Files(String fileName, String directory) {
        if (directory.length() > 0)
            FOLDER += directory + "\\";
        ymlConfig = createConfig(fileName);
        file = getFile(fileName);
    }

    public YamlConfiguration createConfig(String fileName) {
        if (!Clock.getInstance().getDataFolder().exists())
            Clock.getInstance().getDataFolder().mkdir();

        File dataDirectory = new File(Clock.getInstance().getDataFolder(), "data");
        if (!dataDirectory.exists())
            dataDirectory.mkdirs();

        File file = Files.getFile(fileName);
        if (!file.exists())
            file = copyDefault(fileName);

        return YamlConfiguration.loadConfiguration(file);
    }

    public static File getFile(String path) {
        return new File(FOLDER + path);
    }

    private File copyDefault(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + fileName)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(FOLDER + fileName));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            writer.close();
            return getFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
