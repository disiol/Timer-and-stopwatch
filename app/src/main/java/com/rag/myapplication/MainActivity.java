package com.rag.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonStopwatch, buttonTimerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStopwatch = findViewById(R.id.button4);
        buttonTimerActivity = findViewById(R.id.button3);

        buttonTimerActivity.setOnClickListener(v -> {
            startTimerActivity();
        });

        buttonStopwatch.setOnClickListener(v -> {
            startStopwatchActivity();
        });


    }

    private void startStopwatchActivity() {
        Intent intent = new Intent(this, StopwatchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void startTimerActivity() {
        Intent intent = new Intent(this, TimerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
