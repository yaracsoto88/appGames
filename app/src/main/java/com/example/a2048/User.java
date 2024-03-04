package com.example.a2048;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
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
    Setting setting;
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
                    mensaje("Please fill all the fields");
                } else {
                    if (dbHelper.checkUserData(user, password)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("ActiveUser", user);
                        editor.apply();
                        Intent intent = new Intent(User.this, Menu.class);
                        startActivity(intent);
                    } else {
                        mensaje("User does not exist. Please register first.");
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
                   mensaje("Please fill all the fields");
                } else {
                    if (dbHelper.checkUserData(user,password)) {
                        mensaje("User already exists");
                    } else {

                        dbHelper.insertUserData(user, password,null);
                       mensaje("User registered successfully");
                    }
                }
            }
        });



    }    public void mensaje(String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(User.this);
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK", null);
        builder.create();
        builder.show();
    }
}