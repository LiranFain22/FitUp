package com.example.fitup.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitup.JavaClasses.MyUser;
import com.example.fitup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Fragment_UserStatus extends Fragment {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

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
        BottomNavigationView navBar = getActivity().findViewById(R.id.Main_bottom_navigation);
        navBar.setVisibility(View.VISIBLE);

        findViews(view);

        userStatus();
    }

    /**
     * this method fetching from firebase information about user status
     */
    private void userStatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database.collection("users").whereEqualTo("uid", user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot: task.getResult()){
                        MyUser myUser = documentSnapshot.toObject(MyUser.class);
                        Fragment_user_status_LBL_userName.setText("" + myUser.getUserName());
                        Fragment_user_status_LBL_userLevel.setText("" + myUser.getLevel());
                        Fragment_user_status_LBL_userTotalWorkouts.setText("" + myUser.getTotalNumOfWorkouts());
                        Fragment_user_status_LBL_userLastActivated.setText("" + myUser.getLastActivated());
                    }
                }
            }
        });
    }

    private void findViews(View view) {
        Fragment_user_status_LBL_userName = view.findViewById(R.id.Fragment_user_status_LBL_userName);
        Fragment_user_status_LBL_userLevel = view.findViewById(R.id.Fragment_user_status_LBL_userLevel);
        Fragment_user_status_LBL_userTotalWorkouts = view.findViewById(R.id.Fragment_user_status_LBL_userTotalWorkouts);
        Fragment_user_status_LBL_userLastActivated = view.findViewById(R.id.Fragment_user_status_LBL_userLastActivated);
    }
}
