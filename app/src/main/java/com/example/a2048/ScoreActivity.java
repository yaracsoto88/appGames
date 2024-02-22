package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
   Button btOrderScore2048;
   Button btOrderScoreSenku;
   Button btDeleteScore;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("ActiveUser", "");
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

        btOrderScore2048 = findViewById(R.id.btOrder2048);
        btOrderScore2048.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderAndRefresh2048("ScoreData2048");
            }
        });
        btOrderScoreSenku = findViewById(R.id.btOrderSenku);
        btOrderScoreSenku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderAndRefreshSenku("ScoreDataSenku");

            }
        });
        btDeleteScore = findViewById(R.id.btClear);
        btDeleteScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteAll("ScoreData2048");
                dbHelper.deleteAll("ScoreDataSenku");
                dataScores2048.clear();
                dataScoresSenku.clear();
                mAdapter2048.notifyDataSetChanged();
                mAdapterSenku.notifyDataSetChanged();
            }
        });


        //2048
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback2048 = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                //Borrar de la base de datos
                dbHelper.deleteScore("ScoreData2048", userName, Integer.parseInt(dataScores2048.get(position).getScore()));
                dataScores2048.remove(position);
                mAdapter2048.notifyItemRemoved(position);
            }
        };

        //Senku
        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackSenku = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String num=dataScoresSenku.get(position).getScore();
                String[] parts = num.split(":");
                int minutes = Integer.parseInt(parts[0]);
                int seconds = Integer.parseInt(parts[1]);
                int totalTimeInSeconds = minutes * 60 + seconds;

                dbHelper.deleteScore("ScoreDataSenku", userName,totalTimeInSeconds );
                dataScoresSenku.remove(position);
                mAdapterSenku.notifyItemRemoved(position);


            }
        };

        new ItemTouchHelper(itemTouchHelperCallback2048).attachToRecyclerView(recyclerView2048);
        new ItemTouchHelper(itemTouchHelperCallbackSenku).attachToRecyclerView(recyclerViewSenku);

    }
    private void orderAndRefresh2048(String tabla) {
        Cursor cursor = dbHelper.getScoreData(tabla, userName);
        ArrayList<Score> dataScores = new ArrayList<>();
        while (cursor.moveToNext()) {
            dataScores.add(new Score(cursor.getString(0), cursor.getString(1)));
        }
        dataScores.sort((o1, o2) -> {
            if (Integer.parseInt(o1.getScore()) > Integer.parseInt(o2.getScore())) {
                return -1;
            } else if (Integer.parseInt(o1.getScore()) < Integer.parseInt(o2.getScore())) {
                return 1;
            } else {
                return 0;
            }
        });
        mAdapter2048 = new ScoresAdapter(this, dataScores);
        recyclerView2048.setAdapter(mAdapter2048);

    }
    private void orderAndRefreshSenku(String tabla){
        Cursor cursor = dbHelper.getScoreData(tabla, userName);
        ArrayList<Score> dataScores = new ArrayList<>();
        while (cursor.moveToNext()) {
            Score score = new Score(cursor.getString(0), cursor.getString(1));
            score.formatScore();
            dataScores.add(score);
        }
        dataScores.sort((o1, o2) -> {
            String[] parts1 = o1.getScore().split(":");
            String[] parts2 = o2.getScore().split(":");
            int minutes1 = Integer.parseInt(parts1[0]);
            int seconds1 = Integer.parseInt(parts1[1]);
            int totalTimeInSeconds1 = minutes1 * 60 + seconds1;

            int minutes2 = Integer.parseInt(parts2[0]);
            int seconds2 = Integer.parseInt(parts2[1]);
            int totalTimeInSeconds2 = minutes2 * 60 + seconds2;

            if (totalTimeInSeconds1 > totalTimeInSeconds2) {
                return 1;
            } else if (totalTimeInSeconds1 < totalTimeInSeconds2) {
                return -1;
            } else {
                return 0;
            }
        });
        mAdapterSenku = new ScoresAdapter(this, dataScores);
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