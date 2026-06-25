# Project Report: Task 3 - Calculator App

## Overview
This project delivers a responsive dark-themed calculator designed to safely parse math expressions and support advanced controls like percentage scaling, unary negative digits, and consecutive key validations.

## Architecture & Code Design
- **`CalculatorUtils.java`**: Focuses on output formatting. Truncates trailing decimals, formats double values, handles infinity values (division by zero), and checks character types.
- **`ExpressionParser.java`**: Standard stack-based parser implementing a variant of the Shunting-yard algorithm to prioritize multiplication/division (`×`, `÷`) and process addition/subtraction (`+`, `-`) in left-to-right order. It supports unary minuses (`-5`) and percentage (`%`) reduction.
- **`CalculatorEngine.java`**: Tracks the active expression buffer. Validates input keys to prevent illegal sequences (like duplicate decimal points, empty calculations, or starting operators).
- **`MainActivity.java`**: Binds screen layout keys to the calculator engine, handles horizontal scroll actions, and updates the display output.

## Math Processing Rules
- **Operator Swap**: If an operator key is pressed while the last character is already an operator, the previous operator is replaced (e.g., `12 +` becomes `12 -` if `-` is pressed next).
- **Unary Minus**: Handles inputs like `5 + -3` correctly by buffering `-` as part of the digits block.
- **Percentage**: Automatically converts the trailing number to its hundredth value (e.g. `50 %` translates to `0.5`).

## Testing & Validation
- Division by zero validation: Inputting `5 ÷ 0` outputs "Error: Division by zero" instead of crashing.
- Multi-operator evaluation: Inputs like `2 + 3 × 4` yield `14` (checks that multiplication occurs first).
- Sign toggling: Toggling sign back and forth switches values between negative and positive.
- Long equation checking: ScrollView successfully slides rightwards during long input entries.
