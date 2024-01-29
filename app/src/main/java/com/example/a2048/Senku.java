package com.example.a2048;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;



public class Senku extends AppCompatActivity {
    private SenkuTable senkuTable;
    private GridLayout gridLayout;

    private int initialX=-1;
    private int initialY=-1;
    private Button btUndo;
    private Button btRestart;
    private TextView tvTimer;
    private Handler handler;
    private int segundos=0;
    private int minutos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        gridLayout = findViewById(R.id.gridLayout);
        btUndo = findViewById(R.id.btUndoMove);
        senkuTable = new SenkuTable();
        initializeImageViews();
        tableToView();
        gridLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()== MotionEvent.ACTION_UP){
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
        btRestart = findViewById(R.id.btReset);
        btRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Senku", "Restart");
                restart();
                tableToView();
            }
        });

        tvTimer = findViewById(R.id.textView);
        handler = new Handler();
            actualizarTiempo();
        }

        private void actualizarTiempo() {
            int minutosActuales = segundos / 60;
            int segundosActuales = segundos % 60;

            // Actualizar la interfaz de usuario con el tiempo transcurrido
            tvTimer.setText("Tiempo transcurrido: " + minutosActuales + " min " +
                    segundosActuales + " seg");
            // Incrementar los segundos
            segundos++;

            // Enviar un nuevo mensaje después de 1 segundo
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    actualizarTiempo();
                }
            }, 1000);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            // Detener el temporizador al salir de la actividad
            handler.removeCallbacksAndMessages(null);
        }

    public void restart(){
        senkuTable=new SenkuTable();
        tableToView();


    }

    private void touch(int row, int column) {
        Log.d("Senku", "Touch: " + row + " " + column);
        if (initialX == -1 && initialY == -1) {
            initialX = row;
            initialY = column;
        } else {
            System.out.println("initialX: " + initialX + " initialY: " + initialY + " row: " + row + " column: " + column);
            if(!senkuTable.move(initialX, initialY, row, column)){
                Toast.makeText(this, "Movimiento invalido", Toast.LENGTH_SHORT).show();
            }
            initialX = -1;
            initialY = -1;

        }
        tableToView();
        finish();
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
        public void finish(){
        switch (senkuTable.finishGame()){
            case 0:
                Toast.makeText(this, "Perdiste", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "Ganaste", Toast.LENGTH_SHORT).show();
                break;
            case -1:
                break;
        }
        }
    public void tableToView(){
        int index = 0;

        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                View view = gridLayout.getChildAt(index);
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    if (senkuTable.getValueAt(i,j) == 1) {
                        imageView.setImageResource(R.drawable.circuloazul);
                    } else if (senkuTable.getValueAt(i,j) == 0) {
                        imageView.setImageResource(R.drawable.white);
                    }
                    else {
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

}


