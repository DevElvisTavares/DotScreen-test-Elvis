package com.example.tictactoe_elvis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
        private Button[][] buttons = new Button[3][3]; // plate
        private boolean player1Turn = true;
        private int roundCount;
        private int pl1Points;
        private int pl2Points;
        private TextView textViewPlayer1;
        private TextView textViewPlayer2;

//-----------------------------------------------------------------------------------------------
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            textViewPlayer1 = findViewById(R.id.text_view_p1);
            textViewPlayer2 = findViewById(R.id.text_view_p2);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    String buttonID = "button_" + i + j;
                    int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                    buttons[i][j] = findViewById(resID);
                    buttons[i][j].setOnClickListener(this);
                }
            }
            Button buttonReset = findViewById(R.id.button_reset);
            buttonReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetGame();
                }
            });
        }
//--------------------------------------------------------------------------------------------------------


        @Override
        public void onClick(View v) {
            if (!((Button) v).getText().toString().equals("")) {
                return;
            }
            if (player1Turn) {
                ((Button) v).setText("X");
            } else {
                ((Button) v).setText("O");
            }
            roundCount++;
            if (Win()) {
                if (player1Turn) {
                    pl1Win();
                } else {
                    pl2Win();
                }
            } else if (roundCount == 9) {
                equality();
            } else {
                player1Turn = !player1Turn;
            }
        }
        //--------------------------------------------------------------------------------------------------------

    private boolean Win() {
            String[][] tab = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    tab[i][j] = buttons[i][j].getText().toString();
                }
            }
            for (int i = 0; i < 3; i++) {
                if (tab[i][0].equals(tab[i][1])
                        && tab[i][0].equals(tab[i][2])
                        && !tab[i][0].equals("")) {
                    return true;
                }
            }
            for (int i = 0; i < 3; i++) {
                if (tab[0][i].equals(tab[1][i])
                        && tab[0][i].equals(tab[2][i])
                        && !tab[0][i].equals("")) {
                    return true;
                }
            }
            if (tab[0][0].equals(tab[1][1])
                    && tab[0][0].equals(tab[2][2])
                    && !tab[0][0].equals("")) {
                return true;
            }
            if (tab[0][2].equals(tab[1][1])
                    && tab[0][2].equals(tab[2][0])
                    && !tab[0][2].equals("")) {
                return true;
            }
            return false;
        }

    private void equality() {
        Toast.makeText(this, "Draw! Replay", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + pl1Points);
        textViewPlayer2.setText("Player 2: " + pl2Points);

    }
        private void pl1Win() {
            pl1Points++;
            Toast.makeText(this, "Player 1 has won!", Toast.LENGTH_SHORT).show();
            updatePointsText();
            resetBoard();
        }
        private void pl2Win() {
            pl2Points++;
            Toast.makeText(this, "Player 2 has won!", Toast.LENGTH_SHORT).show();
            updatePointsText();
            resetBoard();
        }

        private void resetBoard() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText("");
                }
            }
            roundCount = 0;
            player1Turn = true;
        }
        private void resetGame() {
            pl1Points = 0;
            pl2Points = 0;
            updatePointsText();
            resetBoard();
        }
        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("roundCount", roundCount);
            outState.putInt("player1Points", pl1Points);
            outState.putInt("player2Points", pl2Points);
            outState.putBoolean("player1Turn", player1Turn);
        }
        @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
            super.onRestoreInstanceState(savedInstanceState);
            roundCount = savedInstanceState.getInt("roundCount");
            pl1Points = savedInstanceState.getInt("player1Points");
            pl2Points = savedInstanceState.getInt("player2Points");
            player1Turn = savedInstanceState.getBoolean("player1Turn");
        }
    }
