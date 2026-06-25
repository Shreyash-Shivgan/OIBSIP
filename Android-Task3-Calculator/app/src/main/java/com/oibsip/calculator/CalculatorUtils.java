package com.oibsip.calculator;

import java.util.Locale;

/**
 * Utility class containing checker methods and output formatters.
 */
public class CalculatorUtils {

    /**
     * Checks if a character is a standard mathematical operator.
     */
    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '×' || c == '÷' || c == '%';
    }

    /**
     * Formats the final calculation result to remove trailing decimals if not needed.
     */
    public static String formatResult(double result) {
        if (Double.isInfinite(result)) {
            return "Error: Division by zero";
        }
        if (Double.isNaN(result)) {
            return "Error";
        }
        if (result == (long) result) {
            return String.format(Locale.US, "%d", (long) result);
        } else {
            // Cap to 10 decimal places and remove trailing zeros
            String formatted = String.format(Locale.US, "%.10f", result);
            formatted = formatted.replaceAll("0*$", ""); // remove trailing zeros
            if (formatted.endsWith(".")) {
                formatted = formatted.substring(0, formatted.length() - 1); // remove ending decimal point
            }
            return formatted;
        }
    }
}
