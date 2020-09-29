package com.example.fitup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private ArrayList<Workout> workoutItemArrayList;

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder{
        public ImageView Fragment_IMG_discover;
        public TextView Fragment_LBL_workoutName;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            Fragment_IMG_discover = itemView.findViewById(R.id.Fragment_IMG_discover);
            Fragment_LBL_workoutName = itemView.findViewById(R.id.Fragment_LBL_workoutName);
        }
    }

    public  WorkoutAdapter(ArrayList<Workout> workoutItemArrayList){
        this.workoutItemArrayList = workoutItemArrayList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        WorkoutViewHolder workoutViewHolder = new WorkoutViewHolder(v);
        return workoutViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout currentItem = workoutItemArrayList.get(position);

        if(currentItem.Type.equals("Jumping Rope")){
            holder.Fragment_IMG_discover.setImageResource(R.drawable.ic_jumpingrope);
        }else if(currentItem.Type.equals("HIIT")){
            holder.Fragment_IMG_discover.setImageResource(R.drawable.ic_hiit);
        }else if(currentItem.Type.equals("ABS")){
            holder.Fragment_IMG_discover.setImageResource(R.drawable.ic_abs);
        }
        holder.Fragment_LBL_workoutName.setText(currentItem.Name);
//        holder.Fragment_IMG_discover.setImageResource(currentItem.getmImageResource());
//        holder.Fragment_LBL_workoutName.setText(currentItem.getFragment_LBL_workoutName());
    }

    @Override
    public int getItemCount() {
        return workoutItemArrayList.size();
    }
}
