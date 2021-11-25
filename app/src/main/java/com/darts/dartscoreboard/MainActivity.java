package com.darts.dartscoreboard;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //ButtonsOnMainScreen
    Button btnQuit, btnSettings, btnStatistic, btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideNavigationBar();

        //Button Quit ClickListener
        btnQuit = findViewById(R.id.btn_statistic_last_game);
        btnQuit.setOnClickListener(view -> {
            finish();
            System.exit(0);
        });

        //Button Settings ClickListener
        btnSettings = findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(v -> openSettingsActivity());

        //Button Statistics ClickListener
        btnStatistic = findViewById(R.id.btn_statistic);
        btnStatistic.setOnClickListener(v -> openStatisticActivity());

        //Button Start Game ClickListener
        btnStartGame = findViewById(R.id.btn_start_game);
        btnStartGame.setOnClickListener(v -> openGameActivity());

    }

    //Method to intent new GameActivity
    public void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    //Method to intent new StatisticActivity
    public void openStatisticActivity() {
        Intent intent = new Intent(this, StatisticActivity.class);
        startActivity(intent);
    }

    //Method to intent new SettingsActivity
    public void openSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    //Method for Display 'FullScreen' 'No Actionbar' 'No NavigationBar'
    private void hideNavigationBar() {
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }
}