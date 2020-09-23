package com.example.fitup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView Main_bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.NAV_BTN_home:
                    selectedFragment = new Fragment_Home();
                    break;
                case R.id.NAV_BTN_userStatus:
                    selectedFragment = new Fragment_UserStatus();
                    break;
                case R.id.NAV_BTN_favorite:
                    selectedFragment = new Fragment_Favorite();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, selectedFragment).commit();

            return true;
        }
    };
    private void findView() {
        Main_bottom_navigation = findViewById(R.id.Main_bottom_navigation);
        Main_bottom_navigation.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new Fragment_Home()).commit();
    }
}