package com.example.fitup.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androchef.happytimer.countdowntimer.CircularCountDownView;
import com.androchef.happytimer.countdowntimer.HappyTimer;
import com.example.fitup.JavaClasses.Exercise;
import com.example.fitup.Adapters.ExerciseAdapter;
import com.example.fitup.JavaClasses.MyUser;
import com.example.fitup.R;
import com.example.fitup.JavaClasses.Workout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class Fragment_workout extends Fragment {

    private FirebaseFirestore database;

    private TextView Fragment_LBL_workout_title;
    private ImageView Fragment_IMG_iconFavorite;
    private RecyclerView Fragment_RCV_workout;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MaterialButton Fragment_BTN_start_Workout;
    private int currentExercise ;
    private ArrayList<Exercise> exerciseArrayList;
    private Workout workout = null;

    private CircularCountDownView Fragment_TIMER_workout;
    private TextToSpeech textToSpeech;

    private boolean isFavoriteClicked;

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

        BottomNavigationView navBar = getActivity().findViewById(R.id.Main_bottom_navigation);
        navBar.setVisibility(View.GONE);

        findViews(view);

        initWorkout(view, workout_name);

        return  view;
    }

    /**
     * this method fetching from firebase list of workout
     */
    private void initWorkout(final View view, String workout_name) {
        database.collection("workouts").whereEqualTo("Name" , workout_name).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    Workout workout = documentSnapshot.toObject(Workout.class);
                    Fragment_workout.this.workout = workout;
                    initIconFavorites();
                    initWorkoutRecyclerView(workout, view);
                    exerciseArrayList = (ArrayList<Exercise>) workout.getExercises();
                    Fragment_BTN_start_Workout.setVisibility(View.VISIBLE);
                    Fragment_BTN_start_Workout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateTotalWorkouts();
                            startTimer();
                        }
                    });
                    break;
                }
            }
        });
    }

    private void initIconFavorites() {
        setFavoritesListener();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(final DocumentSnapshot documentSnapshot: task.getResult()) {
                        database.collection("users")
                                .document(documentSnapshot.getId())
                                .collection("favorites").whereEqualTo("Name", workout.Name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    if(task.getResult().isEmpty()){
                                        Fragment_IMG_iconFavorite.setImageResource(R.drawable.ic_favorite_notclicked);
                                        isFavoriteClicked = false;
                                    }else{
                                        Fragment_IMG_iconFavorite.setImageResource(R.drawable.ic_favorite_clicked);
                                        isFavoriteClicked = true;
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * this method add workout to favorite's workouts of user by clicking 'heart' icon
     */
    private void setFavoritesListener() {
        Fragment_IMG_iconFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (isFavoriteClicked) {
                    Fragment_IMG_iconFavorite.setImageResource(R.drawable.ic_favorite_notclicked);
                    database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for(final DocumentSnapshot documentSnapshot : task.getResult()){
                                    database.collection("users")
                                            .document(documentSnapshot.getId())
                                            .collection("favorites")
                                            .whereEqualTo("Name", workout.Name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if(task.isSuccessful()){
                                                for(final DocumentSnapshot documentSnapshot2 : task.getResult()){
                                                    database.collection("users")
                                                            .document(documentSnapshot.getId())
                                                            .collection("favorites")
                                                            .document(documentSnapshot2.getId()).delete();
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    Fragment_IMG_iconFavorite.setImageResource(R.drawable.ic_favorite_clicked);
                    database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                    database.collection("users")
                                            .document(documentSnapshot.getId())
                                            .collection("favorites")
                                            .add(workout).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                                                isFavoriteClicked = true;
                                            } else {
                                                Toast.makeText(getContext(), "Failed to add to Favorites", Toast.LENGTH_SHORT).show();
                                                Fragment_IMG_iconFavorite.setImageResource(R.drawable.ic_favorite_notclicked);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        });

    }

    /**
     * this method updating level's user
     * default level - 'Beginner'
     * after 10 workouts - level's user change to 'Advance'
     * after 20 workouts - level's user change to 'Expert'
     */
    private void updateLevelOfUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot: task.getResult()){
                        MyUser myUser = documentSnapshot.toObject(MyUser.class);

                        int totalWorkouts = myUser.getTotalNumOfWorkouts();

                        if(totalWorkouts >= 20)
                            myUser.setLevel("Expert");
                        else if(totalWorkouts >= 10)
                            myUser.setLevel("Advanced");

                        database.collection("users").document(documentSnapshot.getId()).set(myUser);
                    }
                }
            }
        });
    }

    /**
     * this method updating total workout's user
     * default level - 'Beginner'
     * after 10 workouts - level's user change to 'Advance'
     * after 20 workouts - level's user change to 'Expert'
     */
    private void updateTotalWorkouts() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot: task.getResult()){
                        MyUser myUser = documentSnapshot.toObject(MyUser.class);

                        int totalWorkouts = myUser.getTotalNumOfWorkouts() + 1;
                        myUser.setTotalNumOfWorkouts(totalWorkouts);
                        database.collection("users").document(documentSnapshot.getId()).set(myUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                updateLevelOfUser();
                            }
                        });

                    }
                }
            }
        });
    }

    private void startTimer() {
        Fragment_BTN_start_Workout.setVisibility(View.INVISIBLE);
        Fragment_TIMER_workout.initTimer(10);
        String excerciseName = "next up, " + exerciseArrayList.get(0).getName();
        speak("get ready, " + excerciseName);
        Fragment_TIMER_workout.setOnTickListener(new HappyTimer.OnTickListener() {
            @Override
            public void onTick(int completed, int remaining) {
                if(remaining == 3){
                    speak("Three");
                }else if(remaining == 2){
                    speak("Two");
                }else if(remaining == 1){
                    speak("One");
                }
            }

            @Override
            public void onTimeUp() {
                exerciseArrayList.get(0).setFocused(true);
                mAdapter.notifyDataSetChanged();
                Fragment_BTN_start_Workout.setVisibility(View.INVISIBLE);
                Fragment_TIMER_workout.initTimer(exerciseArrayList.get(0).getTimer());
                Fragment_TIMER_workout.setOnTickListener(new HappyTimer.OnTickListener() {
                    @Override
                    public void onTick(int completed, int remaining) {
                        if(remaining == 7) {
                            if(currentExercise + 1 < exerciseArrayList.size()) {
                                String excerciseName = "next up, " + exerciseArrayList.get(currentExercise + 1).getName();
                                speak(excerciseName);
                            }
                        }else if(remaining == 3){
                            speak("Three");
                        }else if(remaining == 2){
                            speak("Two");
                        }else if(remaining == 1){
                            speak("One");
                        }
                    }

                    @Override
                    public void onTimeUp() {
                        exerciseArrayList.get(currentExercise).setFocused(false);
                        currentExercise++;
                        if(currentExercise<exerciseArrayList.size()) {
                            Fragment_TIMER_workout.initTimer(exerciseArrayList.get(currentExercise).getTimer());
                            Fragment_TIMER_workout.startTimer();
                            Fragment_RCV_workout.scrollToPosition(currentExercise);
                            exerciseArrayList.get(currentExercise).setFocused(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                Fragment_TIMER_workout.startTimer();
            }
        });
        Fragment_TIMER_workout.startTimer();
    }

    private void speak(String text) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDestroy() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void initWorkoutRecyclerView(Workout workout, View view) {
        ArrayList<Exercise> exerciseArrayList = (ArrayList<Exercise>) workout.getExercises();
        Fragment_LBL_workout_title.setText(workout.Name);
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
        Fragment_IMG_iconFavorite = view.findViewById(R.id.Fragment_IMG_iconFavorite);
        Fragment_TIMER_workout = view.findViewById(R.id.Fragment_TIMER_workout);
        Fragment_BTN_start_Workout = view.findViewById(R.id.Fragment_BTN_start_Workout);
        currentExercise = 0;
        exerciseArrayList = new ArrayList<>();


        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);

                    if(result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.d("pttt", "Language not supported: ");
                    }else {

                    }
                }else{
                    Log.d("pttt", "Initialization failed");
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onSaveInstanceState(outState);

    }
}