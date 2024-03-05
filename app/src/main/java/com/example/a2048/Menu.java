package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<MenuItem> mMenuData;
    private MenuAdapter mAdapter;
    private TextView title;
    private String userName;
    private ImageView imgUser;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        userName = sharedPreferences.getString("ActiveUser", "");
        dbHelper = new DBHelper(this);
        initView();

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        MenuAdapter mAdapter = new MenuAdapter(this, MenuItem.getMenu());
        mRecyclerView.setAdapter(mAdapter);

        loadImage();

    }

    private void initView() {
        title = findViewById(R.id.tvWelcome);
        title.setText("Welcome " + userName);
        startAnimation(title);
        mRecyclerView = findViewById(R.id.recyclerView);
        imgUser = findViewById(R.id.imgUser);

    }

    private void loadImage() {
        try {
            Bitmap bitmapImage = dbHelper.getPhoto(userName);
            if (bitmapImage != null && bitmapImage.getByteCount() > 0) {
                imgUser.setImageBitmap(bitmapImage);
            } else {
                imgUser.setImageResource(R.drawable.img_user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void startAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        view.startAnimation(animation);
    }
}
