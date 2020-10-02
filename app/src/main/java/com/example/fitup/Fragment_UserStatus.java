package com.example.fitup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_UserStatus extends Fragment {

    private TextView Fragment_user_status_LBL_userName;
    private TextView Fragment_user_status_LBL_userLevel;
    private TextView Fragment_user_status_LBL_userTotalWorkouts;
    private TextView Fragment_user_status_LBL_userLastActivated;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userstatus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
    }

    private void findViews(View view) {
        Fragment_user_status_LBL_userName = view.findViewById(R.id.Fragment_user_status_LBL_userName);
        Fragment_user_status_LBL_userLevel = view.findViewById(R.id.Fragment_user_status_LBL_userLevel);
        Fragment_user_status_LBL_userTotalWorkouts = view.findViewById(R.id.Fragment_user_status_LBL_userTotalWorkouts);
        Fragment_user_status_LBL_userLastActivated = view.findViewById(R.id.Fragment_user_status_LBL_userLastActivated);
    }
}
