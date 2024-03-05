package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userName = sharedPreferences.getString("ActiveUser", "");

        title = findViewById(R.id.tvWelcome);
        title.setText("Welcome " + userName);
        startAnimation(title);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        MenuAdapter mAdapter = new MenuAdapter(this, MenuItem.getMenu());
        mRecyclerView.setAdapter(mAdapter);

        imgUser = findViewById(R.id.imgUser);
        loadImage();

    }

    private void loadImage() {
        try {
            Bitmap bitmapImage = dbHelper.getPhoto(userName);
            imgUser.setImageBitmap(bitmapImage);

        } catch (Exception e) {
            imgUser.setImageResource(R.drawable.img_user);
        }


    }

    private void startAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        view.startAnimation(animation);
    }
}
