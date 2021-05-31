package com.example.chattingapp2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText username_login, password_login;
    Button login_button;
    TextView gotoregister;


    //Firebase
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Cek the user existance then make model to save the current user
        if (firebaseUser != null) {
            Intent intent = new Intent(LoginActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username_login = findViewById(R.id.username_login);
        password_login = findViewById(R.id.password_login);
        login_button = findViewById(R.id.login_button);
        gotoregister = findViewById(R.id.gotoregister);


        //Firebase Authentication
        auth = FirebaseAuth.getInstance();
        //Get current user


        gotoregister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        //Login Button
        login_button.setOnClickListener(v -> {
            String email = username_login.getText().toString();
            String password = password_login.getText().toString();


            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please Fill in Every Field", Toast.LENGTH_SHORT).show();

            } else {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                                //Clear data sebelum memasuki class StartActivity
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login has failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}