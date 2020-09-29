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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static androidx.navigation.ui.NavigationUI.onNavDestinationSelected;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    private BottomNavigationView Main_bottom_navigation;
    private Toolbar Main_toolbar;
    private NavController navController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setSupportActionBar(Main_toolbar);
    }

//    /**
//     * This method is to SAVING Workouts in Firebase🔥🔥🔥
//     */
//    public void saveWorkout(final ArrayList<Workout> workoutArrayList){
//        //DocumentReference workoutRef = database.collection("Workouts").document("workout" + i);
//        CollectionReference workoutRef = database.collection("workouts");
//        final ArrayList<String> levels = new ArrayList<>();
//        levels.add("Beginner");
//        levels.add("Beginner");
//        levels.add("Advanced");
//        levels.add("Advanced");
//        levels.add("Expert");
//        levels.add("Expert");
//        levels.add("Beginner");
//        levels.add("Beginner");
//        levels.add("Advanced");
//        levels.add("Advanced");
//        levels.add("Expert");
//        levels.add("Expert");
//        levels.add("Beginner");
//        levels.add("Beginner");
//        levels.add("Advanced");
//        levels.add("Advanced");
//        levels.add("Expert");
//        levels.add("Expert");
//
//        final ArrayList<String> types = new ArrayList<>();
//        types.add("Jumping Rope");
//        types.add("Jumping Rope");
//        types.add("Jumping Rope");
//        types.add("Jumping Rope");
//        types.add("Jumping Rope");
//        types.add("Jumping Rope");
//        types.add("HIIT");
//        types.add("HIIT");
//        types.add("HIIT");
//        types.add("HIIT");
//        types.add("HIIT");
//        types.add("HIIT");
//        types.add("ABS");
//        types.add("ABS");
//        types.add("ABS");
//        types.add("ABS");
//        types.add("ABS");
//        types.add("ABS");
//        for(int i = 0; i < workoutArrayList.size(); i++) {
//            final int COUNTER = i;
//            workoutRef.add(workoutArrayList.get(i)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                @Override
//                public void onSuccess(DocumentReference documentReference) {
//                    documentReference.update("Level", levels.get(COUNTER), "Type" , types.get(COUNTER));
//                }
//            });
//        }
//    }

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