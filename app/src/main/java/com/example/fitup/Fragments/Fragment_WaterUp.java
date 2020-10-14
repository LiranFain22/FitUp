package com.example.fitup.Fragments;

import android.app.Notification;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fitup.JavaClasses.FitUp;
import com.example.fitup.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;


public class Fragment_WaterUp extends Fragment {

    private final long START_TIME_IN_MILLIS = 600000;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private NotificationManagerCompat notificationManager;
    private TextView Fragment_WaterUp_LBL_countdown;
    private MaterialButton Fragment_WaterUp_BTN_setButton;
    private MaterialButton Fragment_WaterUp_BTN_resetButton;

    private MaterialButton Fragment_WaterUp_BTN_set20MinButton;
    private MaterialButton Fragment_WaterUp_BTN_set30MinButton;
    private MaterialButton Fragment_WaterUp_BTN_set60MinButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__water_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView navBar = getActivity().findViewById(R.id.Main_bottom_navigation);
        navBar.setVisibility(View.GONE);

        findViews(view);

        setClickListenerButtonSet();

        setClickListenerButtonReset();

        setClickListenerButton_20_30_60();

        updateCountDownText();

    }

    /**
     * this method setting time interval by clicking on '20sec','30sec' and '60sec'
     */
    private void setClickListenerButton_20_30_60() {
        Fragment_WaterUp_BTN_set20MinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                mTimeLeftInMillis *= 2;
                updateCountDownText();
            }
        });
        Fragment_WaterUp_BTN_set30MinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                mTimeLeftInMillis *= 3;
                updateCountDownText();
            }
        });
        Fragment_WaterUp_BTN_set60MinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
                mTimeLeftInMillis *= 6;
                updateCountDownText();
            }
        });
    }

    /**
     * this method start time interval reminder by clicking on 'set' button
     */
    private void setClickListenerButtonSet() {
        Fragment_WaterUp_BTN_setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mTimerRunning) {
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });
    }

    /**
     * this method reset time interval reminder by clicking on 'reset' button
     */
    private void setClickListenerButtonReset() {
        Fragment_WaterUp_BTN_resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
    }

    private void startTimer(){
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                sendOnChannel1(getView());
                start();
//                mTimerRunning = false;
//                Fragment_WaterUp_BTN_setButton.setText("Set");
//                Fragment_WaterUp_BTN_setButton.setVisibility(View.INVISIBLE);
//                Fragment_WaterUp_BTN_resetButton.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        Fragment_WaterUp_BTN_setButton.setText("Pause");
        Fragment_WaterUp_BTN_resetButton.setVisibility(View.INVISIBLE);
        Fragment_WaterUp_BTN_set20MinButton.setVisibility(View.INVISIBLE);
        Fragment_WaterUp_BTN_set30MinButton.setVisibility(View.INVISIBLE);
        Fragment_WaterUp_BTN_set60MinButton.setVisibility(View.INVISIBLE);
    }

    private void pauseTimer(){
        mCountDownTimer.cancel();
        mTimerRunning = false;
        Fragment_WaterUp_BTN_setButton.setText("Set");
        Fragment_WaterUp_BTN_resetButton.setVisibility(View.VISIBLE);
    }

    private void resetTimer(){
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        Fragment_WaterUp_BTN_resetButton.setVisibility(View.INVISIBLE);
        Fragment_WaterUp_BTN_setButton.setVisibility(View.VISIBLE);
        Fragment_WaterUp_BTN_set20MinButton.setVisibility(View.VISIBLE);
        Fragment_WaterUp_BTN_set30MinButton.setVisibility(View.VISIBLE);
        Fragment_WaterUp_BTN_set60MinButton.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        Fragment_WaterUp_LBL_countdown.setText(timeLeftFormatted);
    }


    /*-----------------------------Notification Method--------------------------------------------*/
    public void sendOnChannel1(View view){
        Notification notification = new NotificationCompat.Builder(getContext(), FitUp.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_fitup)
                .setContentTitle("FitUp")
                .setContentText("Remember to DRINK water!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    private void findViews(View view) {
        notificationManager = NotificationManagerCompat.from(getContext());

        Fragment_WaterUp_BTN_setButton = view.findViewById(R.id.Fragment_WaterUp_BTN_setButton);
        Fragment_WaterUp_BTN_resetButton = view.findViewById(R.id.Fragment_WaterUp_BTN_resetButton);
        Fragment_WaterUp_BTN_set20MinButton = view.findViewById(R.id.Fragment_WaterUp_BTN_set20MinButton);
        Fragment_WaterUp_BTN_set30MinButton = view.findViewById(R.id.Fragment_WaterUp_BTN_set30MinButton);
        Fragment_WaterUp_BTN_set60MinButton = view.findViewById(R.id.Fragment_WaterUp_BTN_set60MinButton);

        Fragment_WaterUp_LBL_countdown = view.findViewById(R.id.Fragment_WaterUp_LBL_countdown);
    }
}