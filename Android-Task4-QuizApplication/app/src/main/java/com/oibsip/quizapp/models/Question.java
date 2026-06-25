package com.oibsip.quizapp.models;

import java.io.Serializable;

/**
 * Model representing a single quiz question.
 */
public class Question implements Serializable {
    private final String questionText;
    private final String[] options;
    private final int correctOptionIndex;

    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}
