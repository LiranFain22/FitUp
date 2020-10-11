package com.example.fitup.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.fitup.R;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.util.Calendar;


public class Fragment_ScheduleWorkouts extends Fragment implements DatePickerDialog.OnDateSetListener {

    private FragmentActivity myContext;

    private TextView Fragment_scheduleWorkouts_LBL_chosenDate;
    private MaterialButton Fragment_scheduleWorkouts_BTN_chooseDate;

    @Override
    public void onAttach(@NonNull Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__schedule_workouts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        setOnClickListenerDateButton();
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
    }
}