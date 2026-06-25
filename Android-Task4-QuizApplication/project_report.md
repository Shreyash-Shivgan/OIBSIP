# Project Report: Task 4 - Quiz Application

## Overview
This application is designed to challenge users with general knowledge trivia. It maintains active state tracking across questions and enforces user-friendly immediate visual feedbacks.

## Architecture & Code Design
- **`Question.java`**: Object model enclosing the question text string, option labels array, and the correct option index.
- **`QuestionBank.java`**: Contains a list of 13 general knowledge questions. It exposes a helper function that shuffles the list and slices exactly 10 questions for each play.
- **`WelcomeActivity.java`**: Launcher screen welcoming the user and initiating the quiz intent.
- **`QuizActivity.java`**: The main game controller. Manages question rendering, option buttons state, ProgressBar, and click feedback. Employs a `Handler` to hold the screen for 1.5 seconds after a user choice is recorded to allow reviewing correct/incorrect states.
- **`ResultActivity.java`**: Summary page displaying calculated score percentage and the correct/incorrect fraction breakdowns.

## UI/UX Choices
- Implements immediate green (correct) / red (incorrect) colors using Android `ColorStateList` directly.
- Standard Outlined buttons inside ConstraintLayout to ensure neat styling across varying viewport screen sizes.
- Purple-Teal modern aesthetic details.

## Testing & Validation
- Option disabling: Checks that clicking an option disables all 4 buttons to prevent double-clicks or switching answers.
- Handler delays: Ensures transition occurs smoothly without snapping.
- Shuffling validation: Restarts multiple times and verifies that questions are shown in random orders.
- Final math check: Compiles correct/incorrect tallies accurately on the results page.
