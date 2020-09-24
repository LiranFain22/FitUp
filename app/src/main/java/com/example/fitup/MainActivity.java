package com.example.fitup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView Main_bottom_navigation;
    private Toolbar Main_toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();

        setSupportActionBar(Main_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_icon_logout:
                Toast.makeText(this, "Bye 😊", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        Main_toolbar = findViewById(R.id.Main_toolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_container, new Fragment_Home()).commit();
    }
}