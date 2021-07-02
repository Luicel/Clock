package com.luicel.clock.models;

import com.luicel.clock.utils.ChatUtils;

public class Timer {
    private final String name;
    private double seconds;
    private boolean isPaused;

    public enum State { ACTIVE, INACTIVE, PAUSED }
    private State state;

    public Timer(String name, long seconds) {
        this.name = name;
        this.seconds = seconds;
        this.isPaused = false;
        this.state = State.INACTIVE;
    }

    public String getName() {
        return name;
    }

    public void setSeconds(double seconds) {
        this.seconds = seconds;
    }

    public double getSeconds() {
        return seconds;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getFormattedDisplay() {
        // TODO grab from config to properly format
        return ChatUtils.format("&f00:00:00");
    }

    public static boolean isNameValid(String name) {
        // TODO
        return true;
    }
}
