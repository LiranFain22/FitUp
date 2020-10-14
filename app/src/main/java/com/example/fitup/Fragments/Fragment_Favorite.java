package com.example.fitup.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitup.Adapters.WorkoutAdapter;
import com.example.fitup.JavaClasses.MyUser;
import com.example.fitup.JavaClasses.Workout;
import com.example.fitup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Fragment_Favorite extends Fragment {

    private NavController navController = null;

    private RecyclerView Fragment_RCV_favorite;
    private WorkoutAdapter mAdapter;
    private  RecyclerView.LayoutManager mLayoutManager;
    final ArrayList<Workout> workoutItemArrayList = new ArrayList<>();
    private FirebaseFirestore database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BottomNavigationView navBar = getActivity().findViewById(R.id.Main_bottom_navigation);
        navBar.setVisibility(View.VISIBLE);
        findViews(view);

        initFavoritesWorkouts();

        adapterClickListener();
    }

    /**
     * this method fetching from firebase user's favorite workouts
     */
    private void initFavoritesWorkouts() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(final DocumentSnapshot documentSnapshot : task.getResult()){
                        database.collection("users").document(documentSnapshot.getId()).collection("favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(DocumentSnapshot documentSnapshot1 : task.getResult()){
                                        Workout workout = documentSnapshot1.toObject(Workout.class);
                                        workoutItemArrayList.add(workout);
                                    }
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * this method moving from favorite fragment to workout fragment
     */
    private void adapterClickListener() {
        mAdapter.setOnClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                String workoutName = workoutItemArrayList.get(position).Name;
                bundle.putString("workout_name" , workoutName);
                navController.navigate(R.id.action_fragment_Favorite_to_fragment_workout, bundle);
            }
        });
    }

    private void findViews(View view) {
        navController = Navigation.findNavController(view);
        database = FirebaseFirestore.getInstance();
        Fragment_RCV_favorite = view.findViewById(R.id.Fragment_RCV_favorite);
        Fragment_RCV_favorite.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new WorkoutAdapter(workoutItemArrayList);

        Fragment_RCV_favorite.setLayoutManager(mLayoutManager);
        Fragment_RCV_favorite.setAdapter(mAdapter);
    }
}
