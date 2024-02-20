package com.example.a2048;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


public class SenkuTable {
    private static Context context;
    private int[][] table = new int[7][7];
    private int active = 0;
    private int[][] tableUndo ;

    public SenkuTable() {
        //inicializar tablero
        //1 = casilla ocupada
        //0 = casilla vacia
        //-1 = casilla no valida
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                //Si la celda está en una de las esquinas del tablero, se marca como no válida
                if ((i < 2 && j < 2) || (i > 4 && j < 2) || (i < 2 && j > 4) || (i > 4 && j > 4)) {
                    table[i][j] = -1;
                } else {
                    table[i][j] = 1;
                    active++;
                }
            }
        }
        //casilla central vacia
        table[3][3] = 0;
        // se decrementa active porque ahora hay una casilla menos ocupada (la central)
        active--;
        System.out.println(active);
    }

    public boolean isValidMove(int x, int y, int newX, int newY) {

        //si el movimiento no es valido, se devuelve false
        //un movimiento valido siempre sera en linea recta, ya sea horizontal o vertical

        //si las coordenadas son validas y la casilla de destino esta vacia se puede hacer el movimiento
        // System.out.println("x  " + x + "y  " + y + "newX  " + newX + "newY  " + newY);
        if (table[x][y] == 1 && table[newX][newY] == 0) {
            //horizontal
            if (y != newY) {
                //movimiento derecha
                if (y < newY) {
                    //verificamos que la casilla intermedia este ocupada (table[x+1][y]==1)
                    //y x==newX-2 para que el movimiento sea de 2 casillas
                    if (y == newY - 2 && table[x][y + 1] == 1) {
                        tableUndo = copiarMatrix(table);
                        table[x][y] = 0;
                        table[x][y + 1] = 0;
                        table[newX][newY] = 1;

                        return true;
                    }
                }
                //movimiento izquierda
                else {
                    //Comprobamos que la casilla intermedia este ocupada y que el movimiento sea de 2 casillas
                    if (y == newY + 2 && table[x][y - 1] == 1) {
                        tableUndo = copiarMatrix(table);
                        table[x][y] = 0;
                        table[x][y - 1] = 0;
                        table[newX][newY] = 1;


                        return true;
                    }
                }
                //vertical
            } else {

                //movimiento abajo
                if (x < newX) {
                    if (x == newX - 2 && table[x + 1][y] == 1) {
                        tableUndo = copiarMatrix(table);
                        table[x][y] = 0;
                        table[x + 1][y] = 0;
                        table[newX][newY] = 1;


                        return true;
                    }
                }
                //movimiento arriba
                else {
                    if (x == newX + 2 && table[x - 1][y] == 1) {
                        tableUndo = copiarMatrix(table);
                        table[x][y] = 0;
                        table[x - 1][y] = 0;
                        table[newX][newY] = 1;

                        return true;
                    }
                }

            }
        }


        return false;
    }

    private int[][] copiarMatrix(int[][] source) {
        int[][] copy = new int[source.length][source[0].length];
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[0].length; j++) {
                copy[i][j] = source[i][j];
            }
        }
        Log.d("tag", "copiarMatrix: ");
        return copy;
    }

    public void undoMove() {
        if (tableUndo != null) {
            table = copiarMatrix(tableUndo);
            tableUndo = null;
            active--;
        }

    }


    public boolean existMove() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (table[i][j] == 1) {
                    //horizontal
                    if (j < 5) {
                        if (table[i][j + 1] == 1 && table[i][j + 2] == 0) {
                            return true;
                        }
                    }
                    if (j > 1) {
                        if (table[i][j - 1] == 1 && table[i][j - 2] == 0) {
                            return true;
                        }
                    }
                    //vertical
                    if (i < 5) {
                        if (table[i + 1][j] == 1 && table[i + 2][j] == 0) {
                            return true;
                        }
                    }
                    if (i > 1) {
                        if (table[i - 1][j] == 1 && table[i - 2][j] == 0) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public boolean move(int x, int y, int newX, int newY) {
        if (isValidMove(x, y, newX, newY)) {
            System.out.println("Movimiento valido");
            //se decrementa active porque se ha movido una ficha
            active--;
            return true;

        } else {
            System.out.println("Movimiento no valido");
            return false;
        }

    }

    public int finishGame() {
        if (active == 1 && !existMove()) {
            System.out.println("Has ganado");
            return 1;
        }
        if (active > 1 && !existMove()) {
            System.out.println("Has perdido");
            return 0;
        }
        return -1;
    }


    public int getValueAt(int x, int y) {
        return table[x][y];
    }


}

