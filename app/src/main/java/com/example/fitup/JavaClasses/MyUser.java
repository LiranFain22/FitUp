package com.example.fitup.JavaClasses;

import java.util.Date;

public class MyUser {
    private String userName;
    private String uid;
    private String level;
    private int totalNumOfWorkouts;
    private String lastActivated;

    public MyUser(){}

    public MyUser(String userName, String uid, String level, int totalNumOfWorkouts, String lastActivated) {
        this.userName = userName;
        this.uid = uid;
        this.level = level;
        this.totalNumOfWorkouts = totalNumOfWorkouts;
        this.lastActivated = java.text.DateFormat.getDateTimeInstance().format(new Date());
    }

    public String getUserName() {
        return userName;
    }

    public String getUid() {
        return uid;
    }

    public String getLevel() {
        return level;
    }

    public int getTotalNumOfWorkouts() {
        return totalNumOfWorkouts;
    }

    public String getLastActivated() {
        return lastActivated;
    }

    public void setTotalNumOfWorkouts(int totalNumOfWorkouts) {
        this.totalNumOfWorkouts = totalNumOfWorkouts;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
