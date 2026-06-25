# Task 4 - Quiz Application

A modern multiple-choice General Knowledge Quiz Application for Android built with Java and XML.

## Features
- **Welcome Screen**: A simple, aesthetic splash screen that starts the quiz.
- **Randomized Questions**: 10 questions are randomly loaded from a larger question pool.
- **Immediate Color Feedback**:
  - Selecting the correct option colors it **Green**.
  - Selecting an incorrect option colors it **Red** and highlights the correct choice in **Green**.
- **User Progression**: Displays progress percentage using a smooth ProgressBar and shows current page numbers ("Question X of 10").
- **Final Scoring Card**: Displays percentage, fractional counts of correct and incorrect answers, and a restart button to clear the state and play again.

## Tech Stack
- **Languages**: Java, XML
- **Minimum SDK**: 24
- **Target SDK**: 34
- **Layout**: ConstraintLayout, LinearLayout, CardView

## Installation
1. Download or clone this folder.
2. Open Android Studio and select **Open**.
3. Select the `Android-Task4-QuizApplication` directory.
4. Let Gradle sync completely.
5. Build and run.

## Future Improvements
- Implement database integration for keeping leaderboards.
- Add category selection (Science, History, Sports).
- Integrate a countdown timer for each question.
