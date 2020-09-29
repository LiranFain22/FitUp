package com.example.fitup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView Main_bottom_navigation;
    private Toolbar Main_toolbar;
    private NavController navController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WorkoutsParser.parse(getAssets());

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

            switch (item.getItemId()){
                case R.id.NAV_BTN_home:
                    navController.navigate(R.id.fragment_Home);
                    break;
                case R.id.NAV_BTN_userStatus:
                    navController.navigate(R.id.fragment_UserStatus);
                    break;
                case R.id.NAV_BTN_favorite:
                    navController.navigate(R.id.fragment_Favorite);
                    break;
            }
            return true;
        }
    };

    private void findView() {
        navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        Main_bottom_navigation = findViewById(R.id.Main_bottom_navigation);
        Main_bottom_navigation.setOnNavigationItemSelectedListener(navListener);
        Main_toolbar = findViewById(R.id.Main_toolbar);
    }
}