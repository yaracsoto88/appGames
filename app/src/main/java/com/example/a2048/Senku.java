package com.example.a2048;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class Senku extends AppCompatActivity {
    private SenkuTable senkuTable;
    private GridLayout gridLayout;

    private int initialX=-1;
    private int initialY=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senku);
        gridLayout = findViewById(R.id.gridLayout);
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
    }

    private void touch(int row, int column) {
        Log.d("Senku", "Touch: " + row + " " + column);
        if (initialX == -1 && initialY == -1) {
            initialX = row;
            initialY = column;
        } else {
            System.out.println("initialX: " + initialX + " initialY: " + initialY + " row: " + row + " column: " + column);
            senkuTable.move(initialX, initialY, row, column);
            initialX = -1;
            initialY = -1;
        }
        tableToView();
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


