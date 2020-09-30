package com.example.fitup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_workout extends Fragment {

    private FirebaseFirestore database;

    private TextView Fragment_LBL_workout_title;
    private RecyclerView Fragment_RCV_workout;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public Fragment_workout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_workout, container, false);

        String workout_name = getArguments().getString("workout_name");

        findViews(view);

        database.collection("workouts").whereEqualTo("Name" , workout_name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Workout workout = documentSnapshot.toObject(Workout.class);
                    initWorkoutRecyclerView(workout, view);

                    break;
                }
            }
        });


        return  view;
    }

    private void initWorkoutRecyclerView(Workout workout, View view) {
        ArrayList<Exercise> exerciseArrayList = (ArrayList<Exercise>) workout.getExercises();
        Fragment_RCV_workout = view.findViewById(R.id.Fragment_RCV_workout);
        Fragment_RCV_workout.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExerciseAdapter(exerciseArrayList);

        Fragment_RCV_workout.setLayoutManager(mLayoutManager);
        Fragment_RCV_workout.setAdapter(mAdapter);
    }

    private void findViews(View view) {
        database = FirebaseFirestore.getInstance();

        Fragment_LBL_workout_title = view.findViewById(R.id.Fragment_LBL_workout_title);
    }
}