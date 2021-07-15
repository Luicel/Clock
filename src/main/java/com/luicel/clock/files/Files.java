package com.luicel.clock.files;

import com.luicel.clock.Clock;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Files {
    private String FOLDER = Clock.getInstance().getDataFolder() + "\\";

    protected YamlConfiguration createConfig(String fileName, String directory) {
        if (!Clock.getInstance().getDataFolder().exists())
            Clock.getInstance().getDataFolder().mkdir();

        if (directory.length() > 0) {
            FOLDER += directory + "\\";

            File dataDirectory = new File(Clock.getInstance().getDataFolder(), directory);
            if (!dataDirectory.exists())
                dataDirectory.mkdirs();
        }

        File file = getFile(fileName);
        if (!file.exists())
            file = copyDefault(fileName);

        return YamlConfiguration.loadConfiguration(file);
    }

    protected File getFile(String path) {
        return new File(FOLDER + path);
    }

    protected File copyDefault(String fileName) {
        try {
            String resourcePath = FOLDER.replace(Clock.getInstance().getDataFolder().toString(), "");
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resourcePath + fileName)));
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
