package com.example.fitup;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkoutsParser {

    public WorkoutsParser(Activity activity) { }

    public static ArrayList<Workout> parse(AssetManager assets){
        ArrayList<Workout> workoutArrayList = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(assets.open("workouts.txt")));
            String mline;
            Workout workout = null;
            while((mline = reader.readLine()) != null){
                if(mline.equals("start")) {
                    if(workout != null)
                        workoutArrayList.add(workout);
                    //workout = new Workout(new ArrayList<Workout.Exercise>(),"");
                }
                else{
                    String name = mline;
                    if((mline=reader.readLine())!=null){
                        workout.addExercise(name, Integer.parseInt(mline));
                        Log.d("pttt" , mline);
                    }else{
                        Log.d("pttt", "why????????");
                    }
                }
            }
            workoutArrayList.add(workout);
            reader.close();
            return workoutArrayList;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }
}
