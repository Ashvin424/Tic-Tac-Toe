package com.example.tic_tac_toe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView score_player1, score_player2, status;
    private Button restart, playAgain;
    private Button[] buttons = new Button[9];
    private int playerOneScoreCount, playerTwoScoreCount;
    boolean playerOneActive;
    boolean gameActive;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    int rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        score_player1 = findViewById(R.id.score_player1);
        score_player2 = findViewById(R.id.score_player2);
        status = findViewById(R.id.textStatus);
        restart = findViewById(R.id.restart);
        playAgain = findViewById(R.id.playAgain);

        buttons[0] = findViewById(R.id.btn1);
        buttons[1] = findViewById(R.id.btn2);
        buttons[2] = findViewById(R.id.btn3);
        buttons[3] = findViewById(R.id.btn4);
        buttons[4] = findViewById(R.id.btn5);
        buttons[5] = findViewById(R.id.btn6);
        buttons[6] = findViewById(R.id.btn7);
        buttons[7] = findViewById(R.id.btn8);
        buttons[8] = findViewById(R.id.btn9);
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        playerOneActive = true;
        gameActive = true;
        rounds = 0;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setOnClickListener(this);
        }

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                updateplayerScore();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (!gameActive) {
            return;
        }
        if (!((Button) view).getText().toString().isEmpty()) {
            return;
        }
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1)) - 1;

        if (playerOneActive) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.parseColor("#808836"));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.parseColor("#FF0000"));
            gameState[gameStatePointer] = 1;
        }
        rounds++;

        if (checkWinner()) {
            gameActive = false;
            if (playerOneActive) {
                playerOneScoreCount++;
                updateplayerScore();
                status.setText("Player 1 is Winner");
            } else {
                playerTwoScoreCount++;
                updateplayerScore();
                status.setText("Player 2 is Winner");
            }
        } else if (rounds == 9) {
            gameActive = false;
            status.setText("No Winner");
        } else {
            playerOneActive = !playerOneActive;
        }
    }

    private void playAgain() {
        gameActive = true;
        rounds = 0;
        playerOneActive = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
        status.setText("");
    }

    private boolean checkWinner() {
        boolean winnerResults = false;
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                winnerResults = true;
            }
        }
        return winnerResults;
    }

    private void updateplayerScore() {
        score_player1.setText(Integer.toString(playerOneScoreCount));
        score_player2.setText(Integer.toString(playerTwoScoreCount));
    }
}
