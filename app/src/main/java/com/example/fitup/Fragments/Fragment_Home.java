package com.example.fitup.Fragments;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.fitup.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Fragment_Home extends Fragment implements View.OnClickListener {

    private NavController navController = null;

    private TextView Fragment_home_userTitle;

    private RelativeLayout Fragment_home_LAY__scheduleWorkouts;
    private RelativeLayout Fragment_home_LAY__chooseRandom;
    private RelativeLayout Fragment_home_LAY__discover;
    private RelativeLayout Fragment_home_LAY__waterUp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        findViews(view);

        changeTitleToUserName(Fragment_home_userTitle);

        clickListener();

    }

    private void changeTitleToUserName(TextView fragment_home_userTitle) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        fragment_home_userTitle.setText("Hi " + user.getDisplayName());
    }

    private void clickListener() {
        Fragment_home_LAY__scheduleWorkouts.setOnClickListener(this);
        Fragment_home_LAY__chooseRandom.setOnClickListener(this);
        Fragment_home_LAY__discover.setOnClickListener(this);
        Fragment_home_LAY__waterUp.setOnClickListener(this);
    }

    private void findViews(View view) {
        navController = Navigation.findNavController(view);

        Fragment_home_userTitle = view.findViewById(R.id.Fragment_home_userTitle);

        Fragment_home_LAY__scheduleWorkouts = view.findViewById(R.id.Fragment_home_LAY__scheduleWorkouts);
        Fragment_home_LAY__chooseRandom = view.findViewById(R.id.Fragment_home_LAY__chooseRandom);
        Fragment_home_LAY__discover = view.findViewById(R.id.Fragment_home_LAY__discover);
        Fragment_home_LAY__waterUp = view.findViewById(R.id.Fragment_home_LAY__waterUp);
    }

    @Override
    public void onClick(View view) {
        if(view != null){
            switch (view.getId()){
                case R.id.Fragment_home_LAY__scheduleWorkouts:
                    navController.navigate(R.id.action_fragment_Home_to_fragment_ScheduleWorkouts);
                    break;
                case R.id.Fragment_home_LAY__chooseRandom:
                    navController.navigate(R.id.action_fragment_Home_to_fragment_Choose_Random);
                    break;
                case R.id.Fragment_home_LAY__discover:
                    navController.navigate(R.id.action_fragment_Home_to_fragment_Discover);
                    break;
                case R.id.Fragment_home_LAY__waterUp:
                    navController.navigate(R.id.action_fragment_Home_to_fragment_WaterUp);
                    break;
            }
        }
    }
}
