package com.example.fitup.JavaClasses;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class FitUp extends Application {
    public static final String CHANNEL_1_ID = "title - FitUp";
    public static final String CHANNEL_2_ID = "text - FitUp";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    /**
     * this method create notification in phone's user
     */
    private void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("title - FitUp");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
