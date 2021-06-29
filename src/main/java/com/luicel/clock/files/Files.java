package com.luicel.clock.files;

import com.luicel.clock.Clock;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class Files {
    public static YamlConfiguration ymlConfig;
    public static File file;

    public static String DATA_FOLDER = Clock.getInstance().getDataFolder() + "\\";

    public Files(String fileName, String directory) {
        DATA_FOLDER += directory + "\\";
        ymlConfig = createConfig(fileName);
        file = getFile(fileName);
    }

    public YamlConfiguration createConfig(String fileName) {
        if (!Clock.getInstance().getDataFolder().exists())
            Clock.getInstance().getDataFolder().mkdir();

        File dataDirectory = new File(Clock.getInstance().getDataFolder(), "data");
        if (!dataDirectory.exists())
            dataDirectory.mkdirs();
        // TODO move data files to data directory, but currently I have no idea how to do that

        File file = Files.getFile(fileName);
        if (!file.exists())
            file = copyDefault(fileName);

        return YamlConfiguration.loadConfiguration(file);
    }

    public static File getFile(String path) {
        return new File(DATA_FOLDER + path);
    }

    private File copyDefault(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/" + fileName)));
            BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FOLDER + fileName));

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
