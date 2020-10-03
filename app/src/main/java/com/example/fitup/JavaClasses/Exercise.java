package com.example.fitup.JavaClasses;

public class Exercise {

    private String name;
    private int timer;
    private boolean isFocused;

    public Exercise(){}

    public Exercise(String name, int timer) {
        this.name = name;
        this.timer = timer;
        isFocused = false;
    }

    public String getName() {
        return name;
    }

    public int getTimer() {
        return timer;
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setFocused(boolean focused) {
        isFocused = focused;
    }
}