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

import com.example.fitup.FitUp;
import com.example.fitup.R;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;


public class Fragment_WaterUp extends Fragment {

    private static  final long START_TIME_IN_MILLIS = 600000;

    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private NotificationManagerCompat notificationManager;
    private TextView Fragment_WaterUp_LBL_countdown;
    private MaterialButton Fragment_WaterUp_BTN_setButton;
    private MaterialButton Fragment_WaterUp_BTN_resetButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__water_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);

        setClickListenerButtonSet(view);
        setClickListenerButtonReset(view);

        updateCountDownText();

    }

    private void setClickListenerButtonSet(View view) {
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

    private void setClickListenerButtonReset(View view) {
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
    }

    private void updateCountDownText(){
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        Fragment_WaterUp_LBL_countdown.setText(timeLeftFormatted);
    }


    /*-----------------------------Notification Methods - START--------------------------------------------*/

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


    public void sendOnChannel2(View view){
        Notification notification = new NotificationCompat.Builder(getContext(), FitUp.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_fitup)
                .setContentTitle("FitUp")
                .setContentText("Remember to DRINK water!")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(2, notification);
    }

    /*-----------------------------Notification Methods - END--------------------------------------------*/

    private void findViews(View view) {
        notificationManager = NotificationManagerCompat.from(getContext());
        Fragment_WaterUp_BTN_setButton = view.findViewById(R.id.Fragment_WaterUp_BTN_setButton);
        Fragment_WaterUp_BTN_resetButton = view.findViewById(R.id.Fragment_WaterUp_BTN_resetButton);
        Fragment_WaterUp_LBL_countdown = view.findViewById(R.id.Fragment_WaterUp_LBL_countdown);
    }
}