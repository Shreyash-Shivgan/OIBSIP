# Task 3 - Calculator App

A beautiful, dark-themed mathematical expression calculator for Android built with Java and XML.

## Features
- **Expression Evaluation**: Standard operator precedence (multiplication and division prioritized over addition and subtraction).
- **Advanced Operation Handling**: Supports percentage (%) operations, sign toggling (+/-), decimals, and negative numbers.
- **Robust Calculations**: Correctly handles division by zero, invalid operator patterns, and large digits without crashing.
- **Consecutive Operator Logic**: Standard operator substitution (e.g. typing `5 +` and then `*` automatically replaces `+` with `*`).
- **Interactive UI**: Horizontal scrolling for lengthy expressions, Apple-inspired dark palette layout, and responsive key sizing.

## Screenshots

<p align="center">
  <img src="asset/c1.jpg" width="180" />
  <img src="asset/c2.jpg" width="180" />
</p>

## Tech Stack
- **Languages**: Java, XML
- **Minimum SDK**: 24
- **Target SDK**: 34
- **Layout**: ConstraintLayout, LinearLayout

## Installation
1. Download or clone this folder.
2. Open Android Studio and select **Open**.
3. Select the `Android-Task3-Calculator` directory.
4. Let Gradle sync completely.
5. Build and run.

## Future Improvements
- Add calculation history tab.
- Support parenthesis `()` grouping.
- Add scientific functions (sine, cosine, log, power).
