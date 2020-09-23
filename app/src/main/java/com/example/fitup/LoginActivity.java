package com.example.fitup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button LOGIN_BTN_loginButton;
    private EditText LOGIN_EDT_emailText;
    private EditText LOGIN_EDT_passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        findView();

        LOGIN_BTN_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = LOGIN_EDT_emailText.getText().toString();
                String password = LOGIN_EDT_passwordText.getText().toString();
                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter Email and Password", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("pttt", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("pttt", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            updateUI(currentUser);
        }
    }

    public void updateUI(FirebaseUser currentUser){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("email", currentUser.getEmail());
        startActivity(i);
    }

    private void findView() {
        LOGIN_BTN_loginButton = findViewById(R.id.LOGIN_BTN_loginButton);
        LOGIN_EDT_emailText = findViewById(R.id.LOGIN_EDT_emailText);
        LOGIN_EDT_passwordText = findViewById(R.id.LOGIN_EDT_passwordText);
    }

}