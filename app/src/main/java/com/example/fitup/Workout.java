package com.example.fitup;

import java.util.ArrayList;
import java.util.List;

public class Workout {
    private List<Exercise> exercises;

    public Workout() {
        this.exercises = new ArrayList<>();
    }

    public List<Exercise> getExercises(){
        return this.exercises;
    }

    public void addExercise(String name, int timer){
        this.exercises.add(new Exercise(name,timer));
    }

    private class Exercise {
        private String name;
        private int timer;

        public Exercise(String name, int timer) {
            this.name = name;
            this.timer = timer;
        }

        private String getName(){
            return this.name;
        }
        private int getTimer(){
            return this.timer;
        }
    }
}
