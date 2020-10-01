package com.example.fitup;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private ArrayList<Exercise> exerciseArrayList;

    public static  class ExerciseViewHolder extends RecyclerView.ViewHolder{
        public TextView Fragment_LBL_excersice_excersiceName;
        public TextView Fragment_LBL_excersice_excersiceTimer;
        private View itemView;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            Fragment_LBL_excersice_excersiceName = itemView.findViewById(R.id.Fragment_LBL_excersice_excersiceName);
            Fragment_LBL_excersice_excersiceTimer = itemView.findViewById(R.id.Fragment_LBL_excersice_excersiceTimer);
        }

        public void setBackground(boolean isFocused){
            if(isFocused){
                itemView.setBackgroundColor(Color.YELLOW);
            } else {
                itemView.setBackgroundColor(Color.GRAY);
            }
        }
    }

    public ExerciseAdapter(ArrayList<Exercise> exerciseArrayList){
        this.exerciseArrayList = exerciseArrayList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.excersice_item, parent, false);
        ExerciseViewHolder exerciseViewHolder = new ExerciseViewHolder(v);
        return  exerciseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise currentItem = exerciseArrayList.get(position);
        holder.setBackground(currentItem.isFocused());
        holder.Fragment_LBL_excersice_excersiceName.setText(currentItem.getName());
        holder.Fragment_LBL_excersice_excersiceTimer.setText(String.valueOf(currentItem.getTimer()) + " sec");
    }

    @Override
    public int getItemCount() {
        return exerciseArrayList.size();
    }
}
