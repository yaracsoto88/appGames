package com.example.a2048;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class User extends AppCompatActivity {
    private EditText etUser;
    private EditText etPassword;
    private Button btLogin;
    private Button btRegister;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        dbHelper = new DBHelper(this);
        initView();
        putListener();

    }

    private void initView() {
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btSignIn);

    }

    private void putListener() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String password = etPassword.getText().toString();
                if (user.isEmpty() || password.isEmpty()) {
                    message("Please fill all the fields");
                } else {
                    if (dbHelper.checkUserData(user, password)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ActiveUser", user);
                        editor.apply();
                        Intent intent = new Intent(User.this, Menu.class);
                        startActivity(intent);
                    } else {
                        message("User does not exist. Please register first.");
                    }
                }
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString();
                String password = etPassword.getText().toString();
                if (user.isEmpty() || password.isEmpty()) {
                    message("Please fill all the fields");
                } else {
                    if (dbHelper.checkUserData(user, password)) {
                        message("User already exists");
                    } else {

                        dbHelper.insertUserData(user, password, null);
                        message("User registered successfully");
                    }
                }
            }
        });

    }

    private void message(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", null);
        builder.create();
        builder.show();
    }
}