package com.example.fitup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fitup.JavaClasses.Workout;
import com.example.fitup.R;

import java.util.ArrayList;

public class WorkoutSpinnerAdapter extends ArrayAdapter<Workout> {

    public WorkoutSpinnerAdapter(Context context, ArrayList<Workout> workoutArrayList){
        super(context, 0, workoutArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.workout_spinner_row, parent, false
            );
        }

        TextView workout_spinner_row_LBL_workoutName = convertView.findViewById(R.id.workout_spinner_row_LBL_workoutName);

        Workout currentWorkout = getItem(position);

        if(currentWorkout != null) {
            workout_spinner_row_LBL_workoutName.setText(currentWorkout.Name);
        }
        return convertView;
    }
}
