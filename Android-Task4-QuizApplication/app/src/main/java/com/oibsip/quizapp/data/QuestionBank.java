package com.oibsip.quizapp.data;

import com.oibsip.quizapp.models.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Question bank repository containing general knowledge questions.
 */
public class QuestionBank {

    private static final List<Question> questionsList = new ArrayList<>();

    static {
        questionsList.add(new Question(
                "Which planet is known as the Red Planet?",
                new String[]{"Earth", "Mars", "Venus", "Jupiter"},
                1
        ));
        questionsList.add(new Question(
                "Who wrote the play 'Romeo and Juliet'?",
                new String[]{"Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"},
                1
        ));
        questionsList.add(new Question(
                "What is the chemical symbol for gold?",
                new String[]{"Au", "Ag", "Fe", "Gd"},
                0
        ));
        questionsList.add(new Question(
                "Which is the largest ocean on Earth?",
                new String[]{"Atlantic Ocean", "Indian Ocean", "Arctic Ocean", "Pacific Ocean"},
                3
        ));
        questionsList.add(new Question(
                "In which year did World War II end?",
                new String[]{"1943", "1944", "1945", "1946"},
                2
        ));
        questionsList.add(new Question(
                "What is the capital city of Australia?",
                new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"},
                2
        ));
        questionsList.add(new Question(
                "Which element has the atomic number 1?",
                new String[]{"Oxygen", "Helium", "Hydrogen", "Carbon"},
                2
        ));
        questionsList.add(new Question(
                "Who painted the famous 'Mona Lisa'?",
                new String[]{"Vincent van Gogh", "Leonardo da Vinci", "Pablo Picasso", "Michelangelo"},
                1
        ));
        questionsList.add(new Question(
                "What is the hardest natural substance on Earth?",
                new String[]{"Gold", "Iron", "Diamond", "Quartz"},
                2
        ));
        questionsList.add(new Question(
                "Which country is home to the Kangaroo?",
                new String[]{"South Africa", "New Zealand", "Austria", "Australia"},
                3
        ));
        questionsList.add(new Question(
                "What is the capital city of Japan?",
                new String[]{"Kyoto", "Osaka", "Tokyo", "Hiroshima"},
                2
        ));
        questionsList.add(new Question(
                "How many bones are there in an adult human body?",
                new String[]{"206", "208", "210", "212"},
                0
        ));
        questionsList.add(new Question(
                "Which is the longest river in the world?",
                new String[]{"Amazon River", "Nile River", "Yangtze River", "Mississippi River"},
                1
        ));
    }

    /**
     * Gets a shuffled list of exactly 10 questions.
     */
    public static List<Question> getQuizQuestions() {
        List<Question> shuffledList = new ArrayList<>(questionsList);
        Collections.shuffle(shuffledList);
        // Take a sublist of 10 questions
        return shuffledList.subList(0, Math.min(10, shuffledList.size()));
    }
}
