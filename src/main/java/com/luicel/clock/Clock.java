package com.luicel.clock;

import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.commands.Commands;
import com.luicel.clock.files.Files;
import com.luicel.clock.files.TimersFile;
import com.luicel.clock.models.Timer;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class Clock extends JavaPlugin {
    private static Clock instance;

    @Override
    public void onEnable() {
        instance = this;

        registerCommands();
        registerFiles();
    }

    @Override
    public void onDisable() {
        updateFileData();
    }

    private void registerCommands() {
        new Reflections("com.luicel.clock.commands").getSubTypesOf(Commands.class).forEach(command -> {
            try {
                Commands customCommand = command.newInstance();
                getCommand(customCommand.commandName).setExecutor(customCommand);
            } catch (IllegalAccessError | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerFiles() {
        new Reflections("com.luicel.clock.files").getSubTypesOf(Files.class).forEach(file -> {
            try {
                Constructor<?> constructor = file.getConstructor(String.class, String.class);
                String fileName = file.getSimpleName().replace("File", "").toLowerCase() + ".yml";
                String directory = file.getAnnotation(FileDirectory.class).value();
                constructor.setAccessible(true);
                constructor.newInstance(fileName, directory);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateFileData() {
        for (Timer timer : TimersFile.getTimers())
            TimersFile.updateData(timer);
    }

    public static Clock getInstance() {
        return instance;
    }
}
