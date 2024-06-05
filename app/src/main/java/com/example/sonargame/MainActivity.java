package com.example.sonargame;

import android.os.Bundle;

import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int GRID_SIZE = 5; // Размер сетки 5x5
    private final Button[][] buttons = new Button[GRID_SIZE][GRID_SIZE];
    private int treasureRow;
    private int treasureCol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Создаем кнопки и добавляем их в сетку
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                buttons[row][col] = new Button(this);
                buttons[row][col].setText("O");
                buttons[row][col].setOnClickListener(v -> {
                    Button button = (Button) v;
                    checkTreasure(button);
                });

                // Настройка параметров макета кнопок
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.columnSpec = GridLayout.spec(col, 1, 1f);
                params.rowSpec = GridLayout.spec(row, 1, 1f);
                buttons[row][col].setLayoutParams(params);

                gridLayout.addView(buttons[row][col]);
            }
        }

        // Случайным образом размещаем сокровище
        Random random = new Random();
        treasureRow = random.nextInt(GRID_SIZE);
        treasureCol = random.nextInt(GRID_SIZE);
    }

    private void checkTreasure(Button button) {
        int row = -1;
        int col = -1;

        // Определяем какая кнопка была нажата
        outerLoop:
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (buttons[i][j] == button) {
                    row = i;
                    col = j;
                    break outerLoop;
                }
            }
        }

        if (row == treasureRow && col == treasureCol) {
            button.setText("X");
            Toast.makeText(this, "You found the treasure!", Toast.LENGTH_SHORT).show();
        } else {
            button.setText("~");
            int distance = Math.abs(row - treasureRow) + Math.abs(col - treasureCol);
            Toast.makeText(this, "No treasure here. Distance: " + distance, Toast.LENGTH_SHORT).show();
        }
    }
}
