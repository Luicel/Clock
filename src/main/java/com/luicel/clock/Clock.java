package com.luicel.clock;

import com.luicel.clock.annotations.FileDirectory;
import com.luicel.clock.commands.Commands;
import com.luicel.clock.files.Files;
import com.luicel.clock.files.data.StopwatchesFile;
import com.luicel.clock.files.data.TimersFile;
import com.luicel.clock.models.ClockObject;
import com.luicel.clock.models.Stopwatch;
import com.luicel.clock.models.Timer;
import com.luicel.clock.runnables.DisplayRunnable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Set;

public final class Clock extends JavaPlugin {
    private static Clock instance;

    @Override
    public void onEnable() {
        instance = this;

        Reflections reflections = new Reflections("com.luicel.clock");
        registerSerializables(reflections);
        registerCommands(reflections);
        registerFiles(reflections);

        new DisplayRunnable().runTaskTimer(getInstance(), 0, 1);
    }

    @Override
    public void onDisable() {
        updateFileData();
    }

    private void registerSerializables(Reflections reflections) {
        reflections.getSubTypesOf(ClockObject.class).forEach(model -> {
            String alias = model.getAnnotation(SerializableAs.class).value();
            ConfigurationSerialization.registerClass(model, alias);
        });
    }

    private void registerCommands(Reflections reflections) {
        reflections.getSubTypesOf(Commands.class).forEach(command -> {
            try {
                Commands customCommand = command.newInstance();
                getCommand(customCommand.commandName).setExecutor(customCommand);
            } catch (IllegalAccessError | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    private void registerFiles(Reflections reflections) {
        Set<Class<? extends Files>> classes = reflections.getSubTypesOf(Files.class);
        classes.stream().sorted(Comparator.comparing(c -> c.getAnnotation(FileDirectory.class).value())).forEach(file -> {
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
        TimersFile.getTimers().forEach(Timer::save);
        StopwatchesFile.getStopwatches().forEach(Stopwatch::save);
    }

    public static Clock getInstance() {
        return instance;
    }
}
