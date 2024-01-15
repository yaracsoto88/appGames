package com.example.a2048;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class Senku extends AppCompatActivity {
    private SenkuTable senkuTable;
    private GridLayout gridLayout;
    private ImageView[][] imageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        gridLayout = findViewById(R.id.gridLayout);
        senkuTable = new SenkuTable();
        initializeImageViews();
        gridLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

//              int resourceId = getResourceIdFromTouch(x, y);
                int column = getColumnFromTouch(x, gridLayout);
                int row = getRowFromTouch(y, gridLayout);

                int initialX = 2;
                int initialY = 2;
                int destinationX = 4;
                int destinationY = 2;

                senkuTable.move(initialX, initialY, destinationX, destinationY);
                int gameResult = senkuTable.finishGame();

                showToast("Posición: (" + row + ", " + column + ") - ID: ");
                return true;
            }
        });
    }
    private int getColumnFromTouch(float x, GridLayout gridLayout) {
        float columnWidth = gridLayout.getWidth() / gridLayout.getColumnCount();
        return (int) (x / columnWidth);
    }

    private int getRowFromTouch(float y, GridLayout gridLayout) {
        float rowHeight = gridLayout.getHeight() / gridLayout.getRowCount();
        return (int) (y / rowHeight);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void initializeImageViews() {
        imageViews = new ImageView[7][7];

        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                ImageView imageView = new ImageView(this);
                imageView.setTag(i + "-" + j);
                imageViews[i][j] = imageView;

                // Configurar la apariencia inicial según el estado del tablero
                updateImageView(i, j);

                // Asignar OnClickListener para manejar eventos de toque
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleCellClick((ImageView) v);
                    }
                });

                gridLayout.addView(imageView);
            }
        }
    }

    private void handleCellClick(ImageView imageView) {
        String tag = (String) imageView.getTag();
        String[] coordinates = tag.split("-");
        int x = Integer.parseInt(coordinates[0]);
        int y = Integer.parseInt(coordinates[1]);

        // Realizar el movimiento en tu lógica de juego
        // Puedes llamar a senkuTable.move(x, y, newX, newY) aquí

        // Actualizar la interfaz de usuario después del movimiento
        updateImageView(x, y);
    }

    private void updateImageView(int x, int y) {
        // Actualizar la apariencia de la ImageView según el estado del tablero
        // Por ejemplo, establecer la visibilidad o cambiar la imagen según el valor en el tablero
        int value = senkuTable.getValueAt(x, y);
        ImageView imageView = imageViews[x][y];

        // Lógica para establecer la visibilidad o imagen según el valor en el tablero
        // imageView.setVisibility(View.VISIBLE); o imageView.setImageResource(R.drawable.pieza);

        // Puedes adaptar esta lógica según tus necesidades específicas
    }
}
}

