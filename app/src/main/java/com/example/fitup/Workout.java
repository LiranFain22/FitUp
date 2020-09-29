package com.example.fitup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Workout {
    public List<Exercise> exercises ;
    public String Type;
    public String Name;
    public String Level;

    public Workout(){}

    public Workout(List<Exercise> exercises, String Type, String Name, String Level) {
        this.exercises = exercises;
        this.Type = Type;
        this.Name = Name;
        this.Level = Level;
    }

    public List<Exercise> getExercises(){
        return this.exercises;
    }

    public void addExercise(String name, int timer){
        this.exercises.add(new Exercise(name,timer));
    }

//    public String getType(){
//        return Type;
//    }



}
