package com.example.a2048.game2048;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2048.DBHelper;
import com.example.a2048.Menu;
import com.example.a2048.R;

public class game2048 extends AppCompatActivity {
    private MediaPlayer mediaPlayerOver;
    private MediaPlayer mediaPlayerWin;
    private Tablero board;
    private TableLayout tableLayout;
    private GestureDetector mGestureDetector;
    private Button btNewGame;
    private int height = 4;
    private int width = 4;
    private TextView score;
    private String points;
    private Button btUndo;
    private TextView tvTimer;
    private Handler handler;
    private int seconds = 0;
    private TextView tvBest;
    private DBHelper dbHelper;
    private static final String data = "ScoreData2048";
    private boolean isRunning = true;
    private String userName;
    private Button btExit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("ActiveUser", "");
        initViews();
        putListeners();
        board = new Tablero(height, width, tableLayout, this);
        dbHelper = new DBHelper(this);
        mediaPlayerOver = MediaPlayer.create(this, R.raw.gameover);
        mediaPlayerWin = MediaPlayer.create(this, R.raw.gamewon);
        getMaxScore();
        mGestureDetector = new GestureDetector(this, new EscucharGestos());
        handler = new Handler();
        updateTime();
    }

    private void initViews() {
        score = findViewById(R.id.score);
        tvBest = findViewById(R.id.best);
        tableLayout = findViewById(R.id.tableLayout);
        btNewGame = findViewById(R.id.btNewGame);
        btExit = findViewById(R.id.btGoBack);
        btUndo = findViewById(R.id.btUndo);
        tvTimer = findViewById(R.id.tvTimer);

    }

    private void putListeners() {
        btNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board = new Tablero(height, width, tableLayout, game2048.this);
                // Detener el temporizador al salir de la actividad
                handler.removeCallbacksAndMessages(null);
                seconds = 0;
                updateTime();
                //Reinicar el score
                score.setText("0");


            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(game2048.this, Menu.class);
                startActivity(intent);

            }
        });
        btUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board.undo();
            }
        });
    }

    private void updateTime() {
        int minutesActual = seconds / 60;
        int secondsActual = seconds % 60;

        String timeFormatted = String.format("%02d:%02d", minutesActual, secondsActual);
        tvTimer.setText(timeFormatted);
        seconds++;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTime();
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener el temporizador al salir de la actividad
        handler.removeCallbacksAndMessages(null);
        if (mediaPlayerOver != null) {
            mediaPlayerOver.release();
            mediaPlayerOver = null;
        }
        if (mediaPlayerWin != null) {
            mediaPlayerWin.release();
            mediaPlayerWin = null;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class EscucharGestos extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            float width = Math.abs(e2.getX() - e1.getX());
            float height = Math.abs(e2.getY() - e1.getY());

            if (isRunning) {
                if (width > height) {
                    if (e2.getX() > e1.getX()) {
                        board.right();
                        points = ("" + board.getScore());
                        score.setText(points);
                        results();

                    } else {
                        board.left();
                        points = ("" + board.getScore());
                        score.setText(points);
                        results();
                    }
                } else {
                    if (e1.getY() > e2.getY()) {
                        board.up();
                        points = ("" + board.getScore());
                        score.setText(points);
                        results();
                    } else {
                        board.down();
                        points = ("" + board.getScore());
                        score.setText(points);
                        results();
                    }
                }
            }
            return true;
        }
    }


    private void results() {
        if (board.gameWon()) {
            Toast.makeText(this, "You have won! :)", Toast.LENGTH_SHORT).show();
            saveMaxScore();
            mediaPlayerWin.start();
            isRunning = false;
        }
        if (board.gameLost()) {
            Toast.makeText(this, "You have lost :(", Toast.LENGTH_SHORT).show();
            saveMaxScore();
            mediaPlayerOver.start();
            isRunning = false;
        }

    }

    private void saveMaxScore() {
        int score = board.getScore();
        dbHelper.insertScoreData(data, userName, score);
    }

    private void getMaxScore() {
        int maxScore = dbHelper.getMaxScore2048(data, userName);
        tvBest.setText(String.valueOf(maxScore));
    }


}
