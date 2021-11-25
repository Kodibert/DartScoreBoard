package com.darts.dartscoreboard;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class FragmentGameDisplay extends Fragment {
    private int currentValue = 0;

    private int numberOfPlayers;
    private int[] scorePlayer;
    private int activePlayer; // 0 bis Spieleranzahl - 1

    private TextView tvScorePlayer1;
    private TextView tvScorePlayer2;

    private View framePlayer1;
    private View framePlayer2;

    private Deque<GameState> undoStack = new ArrayDeque<>();
    private Deque<GameState> redoStack = new ArrayDeque<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int gameType = 501; // todo fragment parameter

        numberOfPlayers = 2;
        scorePlayer = new int[]{gameType, gameType};
        activePlayer = 0;

        undoStack.clear();
        redoStack.clear();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_display, container, false);

        tvScorePlayer1 = view.findViewById(R.id.count_player_one);
        tvScorePlayer2 = view.findViewById(R.id.count_player_two);

        framePlayer1 = view.findViewById(R.id.frame_count_player_one);
        framePlayer2 = view.findViewById(R.id.frame_count_player_two);

        view.addOnUnhandledKeyEventListener((v, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                handleKeyEvent(event.getKeyCode());
            }
            return true;
        });

        updateUserInterface();

        return view;
    }

    private void handleKeyEvent(int keyCode) {
        if (keyCode >= KeyEvent.KEYCODE_NUMPAD_0 && keyCode <= KeyEvent.KEYCODE_NUMPAD_9) {
            keyCode = keyCode - KeyEvent.KEYCODE_NUMPAD_0 + KeyEvent.KEYCODE_0;
        }

        if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            int digit = keyCode - KeyEvent.KEYCODE_0;

            int newValue = currentValue * 10 + digit;
            if (newValue >= 0) {
                currentValue = newValue;
            }
        }

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            currentValue = currentValue / 10;
        }

        if (keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            submitValue(currentValue);

            currentValue = 0;
        }

        if (keyCode == KeyEvent.KEYCODE_NUMPAD_SUBTRACT || keyCode == KeyEvent.KEYCODE_U) {
            undo();
        }

        if (keyCode == KeyEvent.KEYCODE_NUMPAD_ADD || keyCode == KeyEvent.KEYCODE_R) {
            redo();
        }
    }

    private void submitValue(int value) {
        System.out.println("submit " + value);

        if (value > 180) {
            Toast.makeText(getContext(), "Wrong value", Toast.LENGTH_SHORT).show();
            return;
        }

        undoStack.addLast(new GameState(scorePlayer, activePlayer));

        redoStack.clear();

        int currentScore = scorePlayer[activePlayer];

        if (value == currentScore) {
            // gewonnen todo
        } else if (value <= currentScore - 2) {
            // Gültiger Wurf -> subtrahieren
            scorePlayer[activePlayer] -= value;
        } else {
            // überfressen
        }

        activePlayer = activePlayer + 1;
        if (activePlayer == numberOfPlayers) {
            activePlayer = 0;
        }

        updateUserInterface();
    }

    private void undo() {
        if (undoStack.isEmpty())
            return;

        redoStack.addLast(new GameState(scorePlayer, activePlayer));

        GameState gameState = undoStack.removeLast();

        this.scorePlayer = Arrays.copyOf(gameState.scorePlayer, gameState.scorePlayer.length);
        this.activePlayer = gameState.activePlayer;

        updateUserInterface();
    }

    private void redo() {
        if (redoStack.isEmpty())
            return;

        undoStack.addLast(new GameState(scorePlayer, activePlayer));

        GameState gameState = redoStack.removeLast();

        this.scorePlayer = Arrays.copyOf(gameState.scorePlayer, gameState.scorePlayer.length);
        this.activePlayer = gameState.activePlayer;

        updateUserInterface();
    }

    private void updateUserInterface() {
        System.out.println("undoStack = " + undoStack);
        System.out.println("redoStack = " + redoStack);

        tvScorePlayer1.setText(String.valueOf(scorePlayer[0]));
        tvScorePlayer2.setText(String.valueOf(scorePlayer[1]));

        if (activePlayer == 0) {
            framePlayer1.setBackgroundResource(R.drawable.border_count);
            framePlayer2.setBackgroundResource(R.drawable.border_count_wait);
        } else {
            framePlayer1.setBackgroundResource(R.drawable.border_count_wait);
            framePlayer2.setBackgroundResource(R.drawable.border_count);
        }
    }

    private static class GameState {
        private int[] scorePlayer;
        private int activePlayer; // 0 bis Spieleranzahl - 1

        public GameState(int[] scorePlayer, int activePlayer) {
            this.scorePlayer = Arrays.copyOf(scorePlayer, scorePlayer.length);
            this.activePlayer = activePlayer;
        }

        @Override
        public String toString() {
            return "GameState{" +
                    "scorePlayer=" + Arrays.toString(scorePlayer) +
                    ", activePlayer=" + activePlayer +
                    '}';
        }
    }
}
