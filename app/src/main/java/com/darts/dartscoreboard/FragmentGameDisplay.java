package com.darts.dartscoreboard;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentGameDisplay extends Fragment {
    private int currentValue = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_display, container, false);

        view.addOnUnhandledKeyEventListener((v, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                handleKeyEvent(event.getKeyCode());
            }
            return true;
        });

        return view;
    }

    private void handleKeyEvent(int keyCode) {
        if (keyCode >= KeyEvent.KEYCODE_NUMPAD_0 && keyCode <= KeyEvent.KEYCODE_NUMPAD_9) {
            keyCode = keyCode - KeyEvent.KEYCODE_NUMPAD_0 + KeyEvent.KEYCODE_0;
        }

        if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            int digit = keyCode - KeyEvent.KEYCODE_0;

            int newValue = currentValue * 10 + digit;
            if (newValue <= 180) {
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
    }

    private void submitValue(int currentValue) {
        System.out.println("submit " + currentValue);
    }
}
