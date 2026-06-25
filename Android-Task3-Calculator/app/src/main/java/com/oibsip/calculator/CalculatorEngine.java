package com.oibsip.calculator;

/**
 * Manages the current expression state and parses input inputs.
 */
public class CalculatorEngine {

    private String expression = "";
    private String result = "0";
    private boolean isFinished = false; // flag to reset display if key pressed after '='

    public String getExpression() {
        return expression;
    }

    public String getResult() {
        return result;
    }

    public void clear() {
        expression = "";
        result = "0";
        isFinished = false;
    }

    public void backspace() {
        if (isFinished) {
            clear();
            return;
        }
        if (!expression.isEmpty()) {
            expression = expression.substring(0, expression.length() - 1);
        }
    }

    public void appendNumber(String num) {
        if (isFinished) {
            expression = "";
            isFinished = false;
        }
        expression += num;
    }

    public void appendOperator(String op) {
        if (isFinished) {
            expression = result;
            isFinished = false;
        }

        if (expression.isEmpty()) {
            if (op.equals("-")) {
                expression = "-";
            }
            return;
        }

        char lastChar = expression.charAt(expression.length() - 1);
        if (CalculatorUtils.isOperator(lastChar)) {
            // Replace the last operator with the new operator
            expression = expression.substring(0, expression.length() - 1) + op;
        } else if (lastChar != '.') {
            expression += op;
        }
    }

    public void appendDecimal() {
        if (isFinished) {
            expression = "0";
            isFinished = false;
        }

        if (expression.isEmpty()) {
            expression = "0.";
            return;
        }

        // Find the last number boundary to see if it already contains a decimal
        int lastOpIndex = -1;
        for (int i = expression.length() - 1; i >= 0; i--) {
            if (CalculatorUtils.isOperator(expression.charAt(i))) {
                lastOpIndex = i;
                break;
            }
        }

        String lastNumber = expression.substring(lastOpIndex + 1);
        if (!lastNumber.contains(".")) {
            if (lastNumber.isEmpty()) {
                expression += "0.";
            } else {
                expression += ".";
            }
        }
    }

    public void toggleSign() {
        if (isFinished) {
            expression = result;
            isFinished = false;
        }

        if (expression.isEmpty()) {
            expression = "-";
            return;
        }

        // Find last operator or start of string to toggle the sign of the last number
        int lastOpIndex = -1;
        for (int i = expression.length() - 1; i >= 0; i--) {
            char c = expression.charAt(i);
            // Ignore minus if it's acting as a unary sign
            if (CalculatorUtils.isOperator(c)) {
                if (c == '-' && (i == 0 || CalculatorUtils.isOperator(expression.charAt(i - 1)))) {
                    // This is a unary minus, ignore it as boundary
                    continue;
                }
                lastOpIndex = i;
                break;
            }
        }

        if (lastOpIndex == -1) {
            // Whole expression is a single number
            if (expression.startsWith("-")) {
                expression = expression.substring(1);
            } else {
                expression = "-" + expression;
            }
        } else {
            // There is an operator separating numbers
            String prefix = expression.substring(0, lastOpIndex + 1);
            String lastNumber = expression.substring(lastOpIndex + 1);

            if (lastNumber.startsWith("-")) {
                expression = prefix + lastNumber.substring(1);
            } else {
                expression = prefix + "-" + lastNumber;
            }
        }
    }

    public void calculate() {
        if (expression.isEmpty()) {
            result = "0";
            return;
        }

        // Strip trailing operator if user pressed '=' with unfinished operator
        char lastChar = expression.charAt(expression.length() - 1);
        if (CalculatorUtils.isOperator(lastChar)) {
            expression = expression.substring(0, expression.length() - 1);
        }

        try {
            double value = ExpressionParser.evaluate(expression);
            result = CalculatorUtils.formatResult(value);
            isFinished = true;
        } catch (ArithmeticException e) {
            result = "Error: Division by zero";
            isFinished = true;
        } catch (Exception e) {
            result = "Error";
            isFinished = true;
        }
    }
}
