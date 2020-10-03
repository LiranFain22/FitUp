package com.example.fitup.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitup.JavaClasses.Workout;
import com.example.fitup.R;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private ArrayList<Workout> workoutItemArrayList;
    private  OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder{
        public ImageView Fragment_IMG_discover;
        public TextView Fragment_LBL_workoutName;

        public WorkoutViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            Fragment_IMG_discover = itemView.findViewById(R.id.Fragment_IMG_discover);
            Fragment_LBL_workoutName = itemView.findViewById(R.id.Fragment_LBL_workoutName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public  WorkoutAdapter(ArrayList<Workout> workoutItemArrayList){
        this.workoutItemArrayList = workoutItemArrayList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_item, parent, false);
        WorkoutViewHolder workoutViewHolder = new WorkoutViewHolder(v, mListener);
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
