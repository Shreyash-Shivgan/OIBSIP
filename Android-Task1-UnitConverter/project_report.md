# Project Report: Task 1 - Unit Converter

## Overview
The Unit Converter Android App offers quick and reliable conversions across three major unit categories: Length, Weight, and Temperature.

## Architecture & Code Design
- **`UnitCategory.java`**: Strongly-typed categories representing Length, Weight, and Temperature.
- **`UnitConstants.java`**: Holds unit labels and provides unit lookups based on the selected category.
- **`ConversionManager.java`**: Implements the conversion logic. The conversions are normalized by translating the input value to a standard base unit (meters for length, grams for weight, Celsius for temperature) and then transforming it to the target unit.
- **`MainActivity.java`**: Connects views, handles user interactions, performs real-time dropdown population, validates input fields, and invokes the conversion manager.

## UI/UX Choices
- Rounded Material CardViews to group content sections logically.
- Outlined TextInputLayout with error highlighting to guide the user when input is empty or incorrectly formatted.
- Dark blue and teal color theme for high visual appeal and modern contrast.

## Testing & Validation
- Validated empty text field inputs (triggers error state).
- Validated letters/special characters input (filtered out or flagged by the parser).
- Checked mathematical conversion accuracy for all combinations (e.g. Celsius to Kelvin, km to feet, pounds to grams).
