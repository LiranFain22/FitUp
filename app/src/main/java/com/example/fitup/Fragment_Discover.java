package com.example.fitup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

    private NavController navController = null;

    private RecyclerView Fragment_RCV_discover;
    private  WorkoutAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    private FirebaseFirestore database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseFirestore.getInstance();

        navController = Navigation.findNavController(view);

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

        mAdapter.setOnClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                String workoutName = workoutItemArrayList.get(position).Name;
                bundle.putString("workout_name", workoutName);
                navController.navigate(R.id.action_fragment_Discover_to_fragment_workout, bundle);
            }
        });
    }
}