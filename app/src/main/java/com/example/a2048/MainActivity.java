package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button bt2048;
    Button btSenku;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt2048 = findViewById(R.id.button1);
        bt2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, game2048.class);
                startActivity(intent);
            }
        });
        btSenku = findViewById(R.id.button2);
        btSenku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Senku.class);
                startActivity(intent);
            }
        });
        title = findViewById(R.id.tvTitle);
        Animation slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);

        // Asigna la animación al TextView
        title.startAnimation(slideInLeft);

    }}
