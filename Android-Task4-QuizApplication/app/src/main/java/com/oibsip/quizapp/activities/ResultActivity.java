package com.oibsip.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.oibsip.quizapp.R;

import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    private TextView tvScorePercentage, tvScoreFraction, tvCorrectCount, tvIncorrectCount;
    private MaterialButton btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initViews();
        displayResults();
        setupListeners();
    }

    private void initViews() {
        tvScorePercentage = findViewById(R.id.tvScorePercentage);
        tvScoreFraction = findViewById(R.id.tvScoreFraction);
        tvCorrectCount = findViewById(R.id.tvCorrectCount);
        tvIncorrectCount = findViewById(R.id.tvIncorrectCount);
        btnRestart = findViewById(R.id.btnRestart);
    }

    private void displayResults() {
        int correct = getIntent().getIntExtra("correct_count", 0);
        int incorrect = getIntent().getIntExtra("incorrect_count", 0);
        int total = getIntent().getIntExtra("total_count", 10);

        int scorePercentage = (correct * 100) / total;

        tvScorePercentage.setText(String.format(Locale.US, "%d%%", scorePercentage));
        tvScoreFraction.setText(String.format(Locale.US, "You scored %d out of %d", correct, total));
        tvCorrectCount.setText(String.valueOf(correct));
        tvIncorrectCount.setText(String.valueOf(incorrect));
    }

    private void setupListeners() {
        btnRestart.setOnClickListener(v -> {
            startActivity(new Intent(ResultActivity.this, WelcomeActivity.class));
            finish();
        });
    }
}
