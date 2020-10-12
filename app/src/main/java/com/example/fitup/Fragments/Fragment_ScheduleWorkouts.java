package com.example.fitup.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitup.Adapters.WorkoutAdapter;
import com.example.fitup.Adapters.WorkoutSpinnerAdapter;
import com.example.fitup.JavaClasses.MyUser;
import com.example.fitup.JavaClasses.Workout;
import com.example.fitup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Fragment_ScheduleWorkouts extends Fragment implements DatePickerDialog.OnDateSetListener {


    private TextView Fragment_scheduleWorkouts_LBL_chosenDate;
    private MaterialButton Fragment_scheduleWorkouts_BTN_chooseDate;
    private MaterialButton Fragment_ScheduleWorkouts_BTN_createEvent;
    private Spinner fragment_ScheduleWorkouts_Spinner_chooseWorkout;

    private ArrayList<Workout> workoutArrayList;
    private WorkoutSpinnerAdapter workoutSpinnerAdapter;

    private FirebaseFirestore database;
    private WorkoutAdapter mAdapter;

    private String clickedWorkoutName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__schedule_workouts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initWorkoutList();
        findViews(view);

        setOnClickListenerDateButton();

        setOnItemSelectedListenerSpinner();

        setOnClickListenerCreateEventButton();
    }

    private void setOnClickListenerCreateEventButton() {
        Fragment_ScheduleWorkouts_BTN_createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fragment_ScheduleWorkouts_Spinner_chooseWorkout.getSelectedItem() != null &&
                   !Fragment_scheduleWorkouts_LBL_chosenDate.getText().toString().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_INSERT).setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, "FitUp - Workout day");
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, clickedWorkoutName);
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Please Select Workout and Choose Date", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setOnItemSelectedListenerSpinner() {
        fragment_ScheduleWorkouts_Spinner_chooseWorkout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Workout clickedWorkout = (Workout) parent.getItemAtPosition(position);
                clickedWorkoutName = clickedWorkout.Name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initWorkoutList() {
        workoutArrayList = new ArrayList<>();
        database = FirebaseFirestore.getInstance();
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
                                    workoutArrayList.add(workout);
                                }
                                mAdapter.notifyDataSetChanged();
                                workoutSpinnerAdapter.notifyDataSetChanged();
                            }


                        });
                    }
                }
            }
        });
        mAdapter = new WorkoutAdapter(workoutArrayList);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        Fragment_scheduleWorkouts_LBL_chosenDate.setText(currentDateString);
    }

    private void setOnClickListenerDateButton() {
        Fragment_scheduleWorkouts_BTN_chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getChildFragmentManager() , "date picker");
            }
        });
    }

    private void findViews(View view) {
        Fragment_scheduleWorkouts_LBL_chosenDate = view.findViewById(R.id.Fragment_scheduleWorkouts_LBL_chosenDate);
        Fragment_scheduleWorkouts_BTN_chooseDate = view.findViewById(R.id.Fragment_scheduleWorkouts_BTN_chooseDate);
        Fragment_ScheduleWorkouts_BTN_createEvent = view.findViewById(R.id.Fragment_ScheduleWorkouts_BTN_createEvent);

        fragment_ScheduleWorkouts_Spinner_chooseWorkout = view.findViewById(R.id.fragment_ScheduleWorkouts_Spinner_chooseWorkout);
        workoutSpinnerAdapter = new WorkoutSpinnerAdapter(getContext(), workoutArrayList);
        fragment_ScheduleWorkouts_Spinner_chooseWorkout.setAdapter(workoutSpinnerAdapter);
    }
}