package com.example.fitup.Fragments;

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

import com.example.fitup.JavaClasses.MyUser;
import com.example.fitup.R;
import com.example.fitup.JavaClasses.Workout;
import com.example.fitup.Adapters.WorkoutAdapter;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_Discover extends Fragment {

    private NavController navController = null;

    private RecyclerView Fragment_RCV_discover;
    private WorkoutAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    private FirebaseFirestore database;
    private ArrayList<Workout> workoutItemArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__discover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBottomNavigation();
        findViews(view);
        initWorkouts();
        initRecyclerView();
        setWorkoutListener();
    }

    /**
     * this method make move from Discover fragment to workout fragment
     */
    private void setWorkoutListener() {
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

    private void initRecyclerView() {
        Fragment_RCV_discover.setHasFixedSize(true);
        Fragment_RCV_discover.setLayoutManager(mLayoutManager);
        Fragment_RCV_discover.setAdapter(mAdapter);
    }

    /**
     * this method fetching from firebase workouts that matching level's user
     */
    private void initWorkouts() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        MyUser myUser = documentSnapshot.toObject(MyUser.class);
                        database.collection("workouts").whereEqualTo("Level", myUser.getLevel()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (DocumentSnapshot documentReference : task.getResult()) {
                                    Workout workout = documentReference.toObject(Workout.class);
                                    workoutItemArrayList.add(workout);
                                }
                                mAdapter.notifyDataSetChanged();
                            }


                        });
                    }
                }
            }
        });
    }

    private void findViews(@NonNull View view) {
        database = FirebaseFirestore.getInstance();
        navController = Navigation.findNavController(view);
        Fragment_RCV_discover = view.findViewById(R.id.Fragment_RCV_discover);
        mLayoutManager = new LinearLayoutManager(getContext());
        workoutItemArrayList = new ArrayList<>();
        mAdapter = new WorkoutAdapter(workoutItemArrayList);
    }

    private void setBottomNavigation() {
        BottomNavigationView navBar = getActivity().findViewById(R.id.Main_bottom_navigation);
        navBar.setVisibility(View.GONE);
    }
}