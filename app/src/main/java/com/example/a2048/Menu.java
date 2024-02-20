package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    Button bt2048;
    Button btSenku;
    Button btScore;
    private TextView title;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userName = intent.getStringExtra("UserName");
        bt2048 = findViewById(R.id.button1);
        bt2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, game2048.class);
                intent.putExtra("UserName", userName);
                startActivity(intent);
            }
        });
        btSenku = findViewById(R.id.button2);
        btSenku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Senku.class);
                intent.putExtra("UserName", userName);
                startActivity(intent);
            }
        });
        btScore = findViewById(R.id.btScore);
        btScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("User: " + userName);
                Intent ss = new Intent(Menu.this, ScoreActivity.class);
                ss.putExtra("UserName", userName);
                startActivity(ss);
            }
        });

        title = findViewById(R.id.tvTitle);
        Animation slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);

        // Asigna la animación al TextView
        title.startAnimation(slideInLeft);

    }
}
