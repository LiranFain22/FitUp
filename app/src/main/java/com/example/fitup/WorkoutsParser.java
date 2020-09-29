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

    public static void parse(AssetManager assets){
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
                    workout = new Workout();
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
//        try {
//            InputStream inputStream = assets.open("workouts.txt");
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            String data = new String(buffer);
//            String[] splitted = data.split("\"|:|,|\n");
//            Workout workout = null;
//            for(int i = 0; i < splitted.length-1; i = i + 2){
//                String splittedStr = splitted[i].replaceAll("”|“","");
//                if(splittedStr.equals("start")){
//                    i--;
//                    if(workout != null){
//                        workoutArrayList.add(workout);
//                    }
//                    workout = new Workout();
//                } else {
//                    workout.addExercise(splittedStr,Integer.parseInt(splitted[i+1].replaceAll("\\s+|”|“","")));
//                    Log.d("pttt",splitted[i]);
//                }
//            }
//            workoutArrayList.add(workout);
//
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
