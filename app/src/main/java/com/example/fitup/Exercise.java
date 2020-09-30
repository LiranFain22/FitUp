package com.example.fitup;

public class Exercise {

    private String name;
    private int timer;

    public Exercise(){}

    public Exercise(String name, int timer) {
        this.name = name;
        this.timer = timer;
    }

    public String getName() {
        return name;
    }

    public int getTimer() {
        return timer;
    }
}