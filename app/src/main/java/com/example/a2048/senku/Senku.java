package com.example.a2048.senku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2048.DBHelper;
import com.example.a2048.Menu;
import com.example.a2048.R;


public class Senku extends AppCompatActivity {
    private SenkuTable senkuTable;
    private GridLayout gridLayout;
    private static final String data = "ScoreDataSenku";
    private int initialX = -1;
    private int initialY = -1;
    private Button btUndo;
    private Button btRestart;
    private TextView tvTimer;
    private Handler handler;
    private int seconds = 0;
    private int minutes = 0;
    private DBHelper dbHelper;
    private boolean isRunning = true;
    private Button btMenu;
    private TextView tvBest;
    private String userName;
    private MediaPlayer mediaPlayerOver;
    private MediaPlayer mediaPlayerWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("ActiveUser", "");
        dbHelper = new DBHelper(this);
        initView();
        putListeners();

        senkuTable = new SenkuTable();
        mediaPlayerOver = MediaPlayer.create(this, R.raw.gameover);
        mediaPlayerWin = MediaPlayer.create(this, R.raw.gamewon);

        initializeImageViews();
        tableToView();
        getMaxScore();

        handler = new Handler();
        updateTime();
    }

    private void initView() {
        gridLayout = findViewById(R.id.gridLayout);
        btUndo = findViewById(R.id.btUndoMove);
        btMenu = findViewById(R.id.btMenu);
        tvBest = findViewById(R.id.tvBest);
        btRestart = findViewById(R.id.btReset);
        tvTimer = findViewById(R.id.textView);
    }

    private void putListeners() {
        gridLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float x = event.getX();
                    float y = event.getY();

                    int column = getColumnFromTouch(x, gridLayout);
                    int row = getRowFromTouch(y, gridLayout);
                    System.out.println("row: " + row + " column: " + column);
                    touch(row, column);
                }


                return true;
            }
        });
        btUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Senku", "Undo");
                senkuTable.undoMove();
                tableToView();

            }
        });

        btRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Senku", "Restart");
                restart();
                tableToView();
                isRunning = true;
                seconds = 0;
                minutes = 0;

            }
        });
        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Senku.this, Menu.class);
                intent.putExtra("UserName", userName);
                startActivity(intent);
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
        // detener temporizador y liberar los recursos del mediaPlayer
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

    public void restart() {
        senkuTable = new SenkuTable();
        tableToView();
    }

    private void touch(int row, int column) {
        if (isRunning) {
            if (initialX == -1 && initialY == -1) {
                initialX = row;
                initialY = column;
                addBrightCircle(row, column);
            } else {
                if (!senkuTable.move(initialX, initialY, row, column)) {
                    Toast.makeText(this, "Invalid move. Please, try again.", Toast.LENGTH_SHORT).show();
                    vibrateInvalidMove();
                }
                senkuTable.move(initialX, initialY, row, column);
                initialX = -1;
                initialY = -1;
                tableToView();

            }
            finish();
        }
    }

    private void vibrateInvalidMove() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private void addBrightCircle(int row, int column) {
        int index = row * 7 + column;
        View view = gridLayout.getChildAt(index);
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(R.drawable.circuloazulbrillante);
        }
    }

    private void initializeImageViews() {
        ImageView imageView;
        int index = 0;
        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.circuloazul);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setAdjustViewBounds(true);
                imageView.setId(index++);
                GridLayout.Spec rowSpec = GridLayout.spec(i);
                GridLayout.Spec colSpec = GridLayout.spec(j);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                int widthInDp = (int) getResources().getDimension(R.dimen.image_width);
                int heightInDp = (int) getResources().getDimension(R.dimen.image_height);
                params.width = widthInDp;
                params.height = heightInDp;
                imageView.setLayoutParams(params);
                gridLayout.addView(imageView);
            }
        }
    }

    public void finish() {
        switch (senkuTable.finishGame()) {
            case 0:
                Toast.makeText(this, "You have lost :(", Toast.LENGTH_SHORT).show();
                saveScore();
                isRunning = false;
                mediaPlayerOver.start();
                break;
            case 1:
                Toast.makeText(this, "You have won! :)", Toast.LENGTH_SHORT).show();
                saveScore();
                isRunning = false;
                mediaPlayerWin.start();
                break;
            case -1:
                break;
        }
    }

    public void tableToView() {
        int index = 0;

        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                View view = gridLayout.getChildAt(index);
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    if (senkuTable.getValueAt(i, j) == 1) {
                        imageView.setImageResource(R.drawable.circuloazul);
                    } else if (senkuTable.getValueAt(i, j) == 0) {
                        imageView.setImageResource(R.drawable.white);
                    } else {
                        imageView.setVisibility(View.GONE);
                    }
                }
                index++;
            }
        }
    }

    private int getColumnFromTouch(float x, GridLayout gridLayout) {
        float columnWidth = gridLayout.getWidth() / gridLayout.getColumnCount();
        return (int) (x / columnWidth);
    }

    private int getRowFromTouch(float y, GridLayout gridLayout) {
        float rowHeight = gridLayout.getHeight() / gridLayout.getRowCount();
        return (int) (y / rowHeight);
    }

    private void saveScore() {
        int score = 60 * minutes + seconds;
        dbHelper.insertScoreData(data, userName, score);
    }

    public void getMaxScore() {
        int maxScore = dbHelper.getMaxScoreSenku(data, userName);
        int seconds = maxScore;

        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;

        String time = String.format("%02d:%02d", minutes, remainingSeconds);
        tvBest.setText(time);
    }
}


