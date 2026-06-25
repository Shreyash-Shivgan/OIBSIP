package com.oibsip.quizapp.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.oibsip.quizapp.R;
import com.oibsip.quizapp.data.QuestionBank;
import com.oibsip.quizapp.models.Question;

import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvQuestionCounter, tvQuestionText;
    private ProgressBar progressBar;
    private MaterialButton btnOpt1, btnOpt2, btnOpt3, btnOpt4;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private int incorrectAnswers = 0;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionList = QuestionBank.getQuizQuestions();

        initViews();
        displayQuestion();
    }

    private void initViews() {
        tvQuestionCounter = findViewById(R.id.tvQuestionCounter);
        tvQuestionText = findViewById(R.id.tvQuestionText);
        progressBar = findViewById(R.id.progressBar);
        btnOpt1 = findViewById(R.id.btnOpt1);
        btnOpt2 = findViewById(R.id.btnOpt2);
        btnOpt3 = findViewById(R.id.btnOpt3);
        btnOpt4 = findViewById(R.id.btnOpt4);

        btnOpt1.setOnClickListener(this);
        btnOpt2.setOnClickListener(this);
        btnOpt3.setOnClickListener(this);
        btnOpt4.setOnClickListener(this);
    }

    private void displayQuestion() {
        // Reset option button styles to default state
        resetButtonStyles(btnOpt1);
        resetButtonStyles(btnOpt2);
        resetButtonStyles(btnOpt3);
        resetButtonStyles(btnOpt4);

        enableOptionButtons(true);

        Question currentQuestion = questionList.get(currentQuestionIndex);

        // Update Question Counter
        tvQuestionCounter.setText(String.format(Locale.US, "Question %d of %d", (currentQuestionIndex + 1), questionList.size()));

        // Update Progress Bar
        progressBar.setProgress(((currentQuestionIndex + 1) * 100) / questionList.size());

        // Set Question & Options
        tvQuestionText.setText(currentQuestion.getQuestionText());
        btnOpt1.setText(currentQuestion.getOptions()[0]);
        btnOpt2.setText(currentQuestion.getOptions()[1]);
        btnOpt3.setText(currentQuestion.getOptions()[2]);
        btnOpt4.setText(currentQuestion.getOptions()[3]);
    }

    private void resetButtonStyles(MaterialButton button) {
        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.card_background)));
        button.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        button.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray_light)));
        button.setStrokeWidth(2);
    }

    private void enableOptionButtons(boolean enable) {
        btnOpt1.setEnabled(enable);
        btnOpt2.setEnabled(enable);
        btnOpt3.setEnabled(enable);
        btnOpt4.setEnabled(enable);
    }

    @Override
    public void onClick(View v) {
        enableOptionButtons(false);

        Question currentQuestion = questionList.get(currentQuestionIndex);
        int selectedIndex = -1;

        int id = v.getId();
        if (id == R.id.btnOpt1) selectedIndex = 0;
        else if (id == R.id.btnOpt2) selectedIndex = 1;
        else if (id == R.id.btnOpt3) selectedIndex = 2;
        else if (id == R.id.btnOpt4) selectedIndex = 3;

        highlightAnswers(selectedIndex, currentQuestion.getCorrectOptionIndex());

        if (selectedIndex == currentQuestion.getCorrectOptionIndex()) {
            correctAnswers++;
        } else {
            incorrectAnswers++;
        }

        // Delay 1.5 seconds before moving to next question
        handler.postDelayed(() -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questionList.size()) {
                displayQuestion();
            } else {
                finishQuiz();
            }
        }, 1500);
    }

    private void highlightAnswers(int selectedIndex, int correctIndex) {
        MaterialButton[] buttons = new MaterialButton[]{btnOpt1, btnOpt2, btnOpt3, btnOpt4};

        // Highlight correct answer in Green
        buttons[correctIndex].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.correct_green)));
        buttons[correctIndex].setTextColor(ContextCompat.getColor(this, android.R.color.white));
        buttons[correctIndex].setStrokeWidth(0);

        // Highlight selected answer in Red if it was incorrect
        if (selectedIndex != correctIndex && selectedIndex != -1) {
            buttons[selectedIndex].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.incorrect_red)));
            buttons[selectedIndex].setTextColor(ContextCompat.getColor(this, android.R.color.white));
            buttons[selectedIndex].setStrokeWidth(0);
        }
    }

    private void finishQuiz() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("correct_count", correctAnswers);
        intent.putExtra("incorrect_count", incorrectAnswers);
        intent.putExtra("total_count", questionList.size());
        startActivity(intent);
        finish();
    }
}
