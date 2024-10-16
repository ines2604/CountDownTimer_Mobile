package com.example.countdowntimer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView hoursTextView;
    private TextView minutesTextView;
    private TextView secondsTextView;
    private Button startButton;
    int  hours=0;
    int minutes=0;
    int seconds=0;
    private Handler handler=new Handler();
    private boolean isRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hoursTextView = findViewById(R.id.hours);
        minutesTextView = findViewById(R.id.minutes);
        secondsTextView = findViewById(R.id.seconds);
        startButton = findViewById(R.id.b_start);


        hoursTextView.setOnClickListener(v -> showNumberPickerDialog("Heures", 0, 23, newValue -> {
            hours = newValue;
            updateTime();
        }));

        minutesTextView.setOnClickListener(v -> showNumberPickerDialog("Minutes", 0, 59, newValue -> {
            minutes = newValue;
            updateTime();
        }));
        secondsTextView.setOnClickListener(v -> showNumberPickerDialog("Secondes", 0, 59, newValue -> {
            seconds = newValue;
            updateTime();
        }));

        startButton.setOnClickListener(v -> startTimer());
    }

    private void updateTime() {
        hoursTextView.setText(String.format("%02d", hours));
        minutesTextView.setText(String.format("%02d", minutes));
        secondsTextView.setText(String.format("%02d", seconds));
    }

    private void showNumberPickerDialog(String title, int minValue, int maxValue, OnNumberSetListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setMinValue(minValue);
        numberPicker.setMaxValue(maxValue);
        numberPicker.setValue(minValue);

        builder.setView(numberPicker);
        builder.setPositiveButton("OK", (dialog, which) -> listener.onNumberSet(numberPicker.getValue()));
        builder.setNegativeButton("Annuler", null);
        builder.show();
    }
    interface OnNumberSetListener {
        void onNumberSet(int newValue);
    }

    private void startTimer() {
        if (!isRunning) {
            isRunning = true;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isRunning) {
                        if (hours == 0 && minutes == 0 && seconds == 0) {
                            isRunning = false;
                            Toast.makeText(MainActivity.this, "Votre compte à rebours est écoulé!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (seconds > 0) {
                            seconds--;
                        } else {
                            seconds = 59;
                            if (minutes > 0) {
                                minutes--;
                            } else {
                                minutes = 59;
                                if (hours > 0) {
                                    hours--;
                                }
                            }
                        }

                        updateTime();
                        handler.postDelayed(this, 1000);
                    }
                }
            }, 1000);
        }
    }
}
