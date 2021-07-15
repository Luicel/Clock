package com.luicel.clock.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public abstract class ClockObject implements ConfigurationSerializable {
    public enum State { ACTIVE, INACTIVE }
    public enum Display { NONE, ACTIONBAR }

    public abstract Map<String, Object> serialize();

    public abstract void save();

    public abstract void delete();
}
