package com.example.a2048;

public class Tablero {
    private Casilla[][] board;
    private int score;
    private boolean gameOver = false;

    public Tablero(int height, int width) {
        this.initBoard(height, width);
    }

    public void initBoard(int height, int width) {
        this.board = new Casilla[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.board[i][j] = new Casilla();
                this.board[i][j].setValor(0);
            }
        }
    }

    public void up() {
        if (!gameOver) {

            //empezamos por las filas o por las columnas??
            for (int j = 0; j < board[0].length; j++) {
                //al empezar por i=1 nos aseguramos de que no se sale del tablero y no saltamos la primera fila,
                //es decir, de 2a fila a la 1a nos tendremos que poder mover
                for (int i = 1; i < board.length; i++) {
                    //si la casilla en la que nos encontramos no está vacia podremos hacer un mov
                    if (board[i][j].getValor() != 0) {
                        moveUp(i, j);
                        fusionUp(i, j);
                    }
                }
            }
        }
    }

    private void moveUp(int i, int j) {
        //k=i porque empezamos en la posición actual y k >0 volvemos a comprobar que no estamos
        // en la 1a fila y que la casilla de arriba esté vacia
        //k-- porque nos movemos de abajo hacia arriba en la columna
        for (int k = i; k > 0 && board[k - 1][j].getValor() == 0; k--) {
            board[k - 1][j].setValor(board[k][j].getValor());
            board[k][j].setValor(0);
        }
    }

    private void fusionUp(int i, int j) {
        //i>0 no podemos estar en la 1a fila, y compara que el valor d la pos actual y el que está
        //arriba de éste sean iguales para poder fusionarlos
        if (i > 0 && board[i - 1][j].getValor() == board[i][j].getValor()) {
            //multiplicaremos por 2 ya que en este juego siempre se sumarán las casillas
            //siempre y cuando sean iguales y sean 2^n
            board[i - 1][j].setValor(board[i][j].getValor() * 2);
            board[i][j].setValor(0);
            //el valor de la casilla resultante se guarda en score para saber la puntuacion final
            score += board[i - 1][j].getValor();

        }
    }

    public void addCasilla() {
        int i = (int) (Math.random() * board.length);
        int j = (int) (Math.random() * board[0].length);
        if (board[i][j].getValor() == 0) {
            int num = (int) (Math.random() * 2);
            switch (num) {
                case 0:
                    board[i][j].setValor(2);
                    break;
                case 1:
                    board[i][j].setValor(4);
                    break;
            }
        } else if (gameLost()) {
            return;
        } else {
            addCasilla();
        }
    }
}



