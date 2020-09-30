package com.example.fitup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Fragment_workout extends Fragment {

    private TextView Fragment_LBL_workout_title;

    private FirebaseFirestore database;

    public Fragment_workout() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_workout, container, false);

        String workout_name = getArguments().getString("workout_name");

        findViews(view);

        database.collection("workouts").whereEqualTo("Name" , workout_name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Workout workout = documentSnapshot.toObject(Workout.class);
                    initWorkout(workout);

                    break;
                }
            }
        });


        return  view;
    }

    private void initWorkout(Workout workout) {

    }

    private void findViews(View view) {
        database = FirebaseFirestore.getInstance();

        Fragment_LBL_workout_title = view.findViewById(R.id.Fragment_LBL_workout_title);
    }
}