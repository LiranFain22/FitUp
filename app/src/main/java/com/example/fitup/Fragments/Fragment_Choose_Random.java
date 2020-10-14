package com.example.fitup.Fragments;

import android.animation.Animator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.fitup.JavaClasses.MyUser;
import com.example.fitup.JavaClasses.Workout;
import com.example.fitup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class Fragment_Choose_Random extends Fragment {

    private MaterialButton Fragment_Choose_Random_BTN_random;
    private LottieAnimationView Fragment_Choose_Random_ANIM;

    private Random random = new Random();
    private FirebaseFirestore database;
    private NavController navController = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__choose__random, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        BottomNavigationView navBar = getActivity().findViewById(R.id.Main_bottom_navigation);
        navBar.setVisibility(View.GONE);
        Fragment_Choose_Random_ANIM.setVisibility(View.INVISIBLE);
        Fragment_Choose_Random_BTN_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_Choose_Random_BTN_random.setVisibility(View.INVISIBLE);
                Fragment_Choose_Random_ANIM.setVisibility(View.VISIBLE);
                Fragment_Choose_Random_ANIM.playAnimation();
                Fragment_Choose_Random_ANIM.addAnimatorListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Fragment_Choose_Random_ANIM.setVisibility(View.GONE);
                        chooseWorkout();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        });
    }

    /**
     * This method fetching randomly from firebase workout that match user's level
     */
    private void chooseWorkout() {
        database = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayList<Workout> workoutArrayList = new ArrayList<>();
        database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        MyUser myUser = documentSnapshot.toObject(MyUser.class);
                        database.collection("workouts").whereEqualTo("Level", myUser.getLevel()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(DocumentSnapshot documentReference : task.getResult()){
                                    Workout workout = documentReference.toObject(Workout.class);
                                    workoutArrayList.add(workout);
                                }
                                int numberWorkout = random.nextInt((workoutArrayList.size()));
                                Bundle bundle = new Bundle();
                                String workoutName = workoutArrayList.get(numberWorkout).Name;
                                bundle.putString("workout_name" , workoutName);
                                navController.navigate(R.id.action_fragment_Choose_Random_to_fragment_workout, bundle);
                            }
                        });
                    }
                }
            }
        });
    }

    private void findViews(View view) {
        Fragment_Choose_Random_BTN_random = view.findViewById(R.id.Fragment_Choose_Random_BTN_random);
        Fragment_Choose_Random_ANIM = view.findViewById(R.id.Fragment_Choose_Random_ANIM);

        navController = Navigation.findNavController(view);
    }
}