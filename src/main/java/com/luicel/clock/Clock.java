package com.luicel.clock;

import com.luicel.clock.commands.Commands;
import com.luicel.clock.files.Files;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

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

    public void registerFiles() {
        new Reflections("com.luicel.clock.files").getSubTypesOf(Files.class).forEach(file -> {
            try {
                Constructor constructor = file.getConstructor(String.class);
                String name = file.getSimpleName().replace("File", "").toLowerCase() + ".yml";
                constructor.setAccessible(true);
                constructor.newInstance(name);
            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static Clock getInstance() {
        return instance;
    }
}
