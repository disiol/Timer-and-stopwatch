package com.rag.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.rag.myapplication.databinding.ActivityTimerBinding;

import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {


    private ActivityTimerBinding binding;
    private CountDownTimer countDownTimer;
    private long timerTime;
    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_timer);
        binding.startButton.setOnClickListener(v -> {




            getDataForTimer();


            setTimer(timerTime);
            countDownTimer.start();
            timerTime = 0;


        });

        binding.stopButton.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            timerTime = 0;
        });

        binding.resetButton.setOnClickListener(v -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            timerTime = 0;
            binding.hoursTextView.getText().clear();
            binding.minutesTextView.getText().clear();
            binding.secondsTextView.getText().clear();
            setTimer(timerTime);
        });
    }

    private void getDataForTimer() {
        String hoursTextViewString = binding.hoursTextView.getText().toString();
        if (!hoursTextViewString.isEmpty()) {
            int hours = Integer.parseInt(hoursTextViewString) * 3600000;
            timerTime = timerTime + hours;
        }

        String minutesTextViewS = binding.minutesTextView.getText().toString();
        if (!minutesTextViewS.isEmpty()) {
            int minutes = Integer.parseInt(minutesTextViewS) * 60000;
            timerTime = timerTime + minutes;

        }

        String secondsTextViewS = binding.secondsTextView.getText().toString();
        if (!secondsTextViewS.isEmpty()) {
            int secs = Integer.parseInt(secondsTextViewS) * 1000;
            timerTime = timerTime + secs;

        }
    }

    private void setTimer(long time) {
        countDownTimer = new CountDownTimer(time, 1) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                binding.secondsTextView.setText(String.format("%d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                binding.minutesTextView.setText(String.format("%d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))));

                binding.hoursTextView.setText(String.format("%d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished)));

            }


            @Override
            public void onFinish() {

                showMessage(binding.timerTitleTextView.getText().toString() + " " + "finish");
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                ringtone.play();            }
        };
    }

    public void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        (dialog, id) -> {
                    ringtone.stop();
                            dialog.cancel();
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

