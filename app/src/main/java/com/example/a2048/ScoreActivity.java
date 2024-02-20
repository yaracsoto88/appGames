package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView2048;
    private RecyclerView recyclerViewSenku;
    private ArrayList<Score> dataScores2048;
    private ArrayList<Score> dataScoresSenku;
    private ScoresAdapter mAdapter2048;
    private ScoresAdapter mAdapterSenku;
    private DBHelper dbHelper;
    String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userName = intent.getStringExtra("UserName");
        setContentView(R.layout.activity_scores);
        dataScores2048 = new ArrayList<>();
        dataScoresSenku = new ArrayList<>();
        dbHelper = new DBHelper(this);
        addData2048();
        addDataSenku();
        // Initialize the RecyclerView.

        recyclerView2048 = findViewById(R.id.rv2048);
        recyclerViewSenku = findViewById(R.id.rvSenku);


        // Set the Layout Manager.
        recyclerView2048.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerViewSenku.setLayoutManager(new GridLayoutManager(this, 1));


        // Initialize the adapter and set it to the RecyclerView.
        mAdapter2048 = new ScoresAdapter(this, dataScores2048);
        recyclerView2048.setAdapter(mAdapter2048);
        mAdapterSenku = new ScoresAdapter(this, dataScoresSenku);
        recyclerViewSenku.setAdapter(mAdapterSenku);

    }

    private void addData2048() {
        Cursor cursor = dbHelper.getScoreData("ScoreData2048", userName);
        while (cursor.moveToNext()) {
            dataScores2048.add(new Score(cursor.getString(0), cursor.getString(1)));
        }
    }

    private void addDataSenku() {
        Cursor cursor = dbHelper.getScoreData("ScoreDataSenku", userName);
        while (cursor.moveToNext()) {
            Score score = new Score(cursor.getString(0), cursor.getString(1));
            score.formatScore();
            dataScoresSenku.add(score);
        }

    }

}