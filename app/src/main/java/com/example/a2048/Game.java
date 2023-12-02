package com.example.a2048;

import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class Game {
    private TableLayout tableLayout;
    int filas = 4;
    int columnas = 4;
    private int[][] matriz;
    private int posx;
    private int posy;

    public Game(TableLayout tableLayout) {
        this.matriz = new int[filas][columnas];
        this.tableLayout = tableLayout;
        inicializarMatriz();
        conectarMatrizEnVista();


    }

    public int getValor(int x, int y) {
        return matriz[x][y];
    }

    public void inicializarMatriz() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                matriz[i][j] = 0;
            }

        }
        colocarNumeroInicial(matriz);
    }
    public void conectarMatrizEnVista() {
        for (int i = 0; i < 4; i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 4; j++) {
                TextView textView = (TextView) row.getChildAt(j);
                if (matriz[i][j] == 0) {
                    textView.setText("");
                } else {
                    textView.setText(String.valueOf(matriz[i][j]));
                }

            }
        }

    }

    private void colocarSitioRandom() {
        Random ran = new Random();
        int x, y;
        x = ran.nextInt(4);
        y = ran.nextInt(4);
        boolean empty = false;

        while (!empty) {
            if (matriz[x][y] == 0) {
                matriz[x][y] = 2;
                empty = true;
            }else{
                x = ran.nextInt(4);
                y = ran.nextInt(4);
            }
        }

    }
    private void colocarNumeroInicial(int[][] matriz) {
        Random ran = new Random();
        int x, y;
        x = ran.nextInt(matriz.length);
        y = ran.nextInt(matriz[0].length);

        matriz[x][y] = 2;
    }

    public void movimientoDerecha() {
        if (posy < 3) {
            matriz[posx][posy + 1] = 2;
            matriz[posx][posy] = 0;
            posy++;
        }
        colocarSitioRandom();
        conectarMatrizEnVista();


    }

    public void movimientoIzquierda() {
        if (posy > 0) {
            matriz[posx][posy - 1] = 2;
            matriz[posx][posy] = 0;
            posy--;
        }
        colocarSitioRandom();
        conectarMatrizEnVista();

    }

    public void movimientoArriba() {
        if (posx > 0) {
            matriz[posx - 1][posy] = 2;
            matriz[posx][posy] = 0;
            posx--;
        }
        colocarSitioRandom();
        conectarMatrizEnVista();

    }

    public void movimientoAbajo() {
        if (posx < 3) {
            matriz[posx + 1][posy] = 2;
            matriz[posx][posy] = 0;
            posx++;
        }
        colocarSitioRandom();
        conectarMatrizEnVista();

    }

}