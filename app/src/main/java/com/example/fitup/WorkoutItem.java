package com.example.fitup;

public class WorkoutItem {
    private int mImageResource;
    private String Fragment_LBL_workoutName;

    public WorkoutItem(int mImageResource, String fragment_LBL_workoutName){
        this.mImageResource = mImageResource;
        this.Fragment_LBL_workoutName = fragment_LBL_workoutName;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getFragment_LBL_workoutName() {
        return Fragment_LBL_workoutName;
    }
}
