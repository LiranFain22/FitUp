package com.example.fitup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_Discover extends Fragment {

    private RecyclerView Fragment_RCV_discover;
    private  RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    private FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseFirestore.getInstance();

        final ArrayList<Workout> workoutItemArrayList = new ArrayList<>();
        database.collection("workouts").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentReference:queryDocumentSnapshots){
                    Workout workout = documentReference.toObject(Workout.class);
                    workoutItemArrayList.add(workout);
                }
                mAdapter.notifyDataSetChanged();
            }
        });

        Fragment_RCV_discover = view.findViewById(R.id.Fragment_RCV_discover);
        Fragment_RCV_discover.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new WorkoutAdapter(workoutItemArrayList);

        Fragment_RCV_discover.setLayoutManager(mLayoutManager);
        Fragment_RCV_discover.setAdapter(mAdapter);
    }
}