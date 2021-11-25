package com.darts.dartscoreboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class FragmentBullshot extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bullshot, container, false);

        View startGameButton = view.findViewById(R.id.start_game);
        startGameButton.setOnClickListener(v -> {
            GameActivity activity = (GameActivity) getActivity();
            if (activity != null) {
                activity.startBullshotGame();
            }
        });

        return view;
    }
}
