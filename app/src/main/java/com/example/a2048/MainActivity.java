package com.example.a2048;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    private Game game;
    private TableLayout tableLayout;
    private GestureDetector mGestureDetector;
    private Button btNewGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = findViewById(R.id.tableLayout);
        game=new Game(tableLayout);
        mGestureDetector = new GestureDetector(this, new EscucharGestos());
        btNewGame=findViewById(R.id.btNewGame);
        btNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.inicializarMatriz();
                game.conectarMatrizEnVista();

            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class EscucharGestos extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            float ancho = Math.abs(e2.getX() - e1.getX());
            float alto = Math.abs(e2.getY() - e1.getY());

            if (ancho > alto) {
                if (e2.getX() > e1.getX()) {
                    game.movimientoDerecha();

                } else {
                    game.movimientoIzquierda();
                }
            } else {
                if (e1.getY() > e2.getY()) {
                    game.movimientoArriba();
                } else {
                    game.movimientoAbajo();
                }
            }
            return true;
        }
    }
}