package com.example.chattingapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {


        EditText username_register, email_register, password_register;
        TextView gotologin;
        Button register_button;

        //Firebase
        FirebaseAuth auth;
        DatabaseReference myRef;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);


            //Inisialisasi widget-widget
            username_register = findViewById(R.id.username_register);
            email_register = findViewById(R.id.email_register);
            password_register = findViewById(R.id.password_register);
            gotologin = findViewById(R.id.gotologin);
            register_button = findViewById(R.id.register_button);


            auth = FirebaseAuth.getInstance();

            register_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username_text = username_register.getText().toString();
                    String email_text = email_register.getText().toString();
                    String pass_text = password_register.getText().toString();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    if(TextUtils.isEmpty(username_text) || TextUtils.isEmpty(email_text) || TextUtils.isEmpty(pass_text)){
                        Toast.makeText(RegisterActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                    } else if (!email_text.matches(emailPattern)) {
                        Toast.makeText(RegisterActivity.this, "Email format invalid!", Toast.LENGTH_SHORT).show();
                    } else {
                        RegisterNow(username_text,email_text,pass_text);
                    }
                }
            });

        }

        private void RegisterNow(final String username, String email, String password){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){
                              DatabaseReference mDatabase;

                              mDatabase = FirebaseDatabase.getInstance("https://hello-2d0a6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                              Log.i(TAG," Reference is: "+mDatabase.toString());
                              FirebaseUser firebaseUser = auth.getCurrentUser();
                              assert firebaseUser != null;
                              String userid = firebaseUser.getUid();

                              myRef = FirebaseDatabase.getInstance("https://hello-2d0a6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("MyUsers").child(userid);

                              DatabaseReference sync = FirebaseDatabase.getInstance("https://hello-2d0a6-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("MyUsers");
                              sync.keepSynced(true);
                              HashMap<String,String> hashMap = new HashMap<>();
                              hashMap.put("id",userid);
                              hashMap.put("username",username);
                              hashMap.put("imageURL","default");

                              myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful()){
                                          Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                          startActivity(intent);
                                          Toast.makeText(RegisterActivity.this,"User has been created successfully",Toast.LENGTH_SHORT).show();
                                          finish();
                                      }
                                  }
                              });
                          }if(!task.isSuccessful()){
                              Toast.makeText(RegisterActivity.this,"Invalid Email or Password",Toast.LENGTH_SHORT).show();
                          }
                        }
                    });
        }
    }