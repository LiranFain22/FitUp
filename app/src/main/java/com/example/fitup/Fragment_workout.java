package com.example.fitup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androchef.happytimer.countdowntimer.CircularCountDownView;
import com.androchef.happytimer.countdowntimer.HappyTimer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;

public class Fragment_workout extends Fragment {

    private FirebaseFirestore database;

    private TextView Fragment_LBL_workout_title;
    private RecyclerView Fragment_RCV_workout;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MaterialButton Fragment_BTN_start_Workout;
    private int currentExercise ;
    private ArrayList<Exercise> exerciseArrayList;
    private Workout workout = null;

    private CircularCountDownView Fragment_TIMER_workout;
    private TextToSpeech textToSpeech;

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
                    Fragment_workout.this.workout = workout;
                    initWorkoutRecyclerView(workout, view);
                    exerciseArrayList = (ArrayList<Exercise>) workout.getExercises();
                    Fragment_BTN_start_Workout.setVisibility(View.VISIBLE);
                    Fragment_BTN_start_Workout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startTimer();
                        }
                    });
                    break;
                }
            }
        });


        return  view;
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
}