package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<MenuItem> mMenuData;
    private MenuAdapter mAdapter;
    private TextView title;
    String userName;
    private ImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        userName = intent.getStringExtra("UserName");

        title = findViewById(R.id.tvTitle);
        Animation slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        title.startAnimation(slideInLeft);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        MenuAdapter mAdapter = new MenuAdapter(this, MenuItem.getMenu());
        mRecyclerView.setAdapter(mAdapter);

        imgUser = findViewById(R.id.imgUser);
        imgUser.setImageResource(R.drawable.img_user);



    }
}
