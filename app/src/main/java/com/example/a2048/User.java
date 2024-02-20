package com.example.a2048;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class User extends AppCompatActivity {
    EditText etUser;
    EditText etPassword;
    Button btLogin;
    Button btRegister;
    DBHelper dbHelper;
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        dbHelper = new DBHelper(this);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String password = etPassword.getText().toString();
                if (user.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v, "Please fill all the fields", Snackbar.LENGTH_LONG).show();
                } else {
                    if (dbHelper.checkUserData(user, password)) {
                        Intent intent = new Intent(User.this, Menu.class);
                        intent.putExtra("UserName", user);
                        startActivity(intent);
                    } else {
                        Snackbar.make(v, "Invalid user or password", Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });

        btRegister = findViewById(R.id.btSignIn);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String password = etPassword.getText().toString();
                if (user.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v, "Please fill all the fields", Snackbar.LENGTH_LONG).show();
                } else {
                    if (dbHelper.checkUserData(user,password)) {
                        Snackbar.make(v, "User already exists", Snackbar.LENGTH_LONG).show();
                    } else {
                        dbHelper.insertUserData(user, password);
                        Snackbar.make(v, "User registered", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });


    }
}