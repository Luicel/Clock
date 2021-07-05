package com.luicel.clock.models;

import com.luicel.clock.utils.ChatUtils;

public class Timer {
    private final String name;
    private int seconds;

    public enum State { ACTIVE, INACTIVE, PAUSED }
    private State state;

    public Timer(String name, int seconds) {
        this.name = name;
        this.seconds = seconds;
        this.state = State.INACTIVE;
    }

    public String getName() {
        return name;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getFormattedDisplay() {
        // TODO grab from config to properly format
        return ChatUtils.format("&f00:00:" + seconds);
    }

    public static String getPrefix() {
        return ChatUtils.format("&b&lTIMER! &7");
    }
    
    public static boolean isNameValid(String name) {
        // TODO
        return true;
    }
}
