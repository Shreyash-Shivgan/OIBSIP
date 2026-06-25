package com.oibsip.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Stack-based mathematical expression parser honoring operator precedence.
 */
public class ExpressionParser {

    /**
     * Evaluates a mathematical expression string and returns the result.
     * Supports +, -, ×, ÷, %, decimals, and negative numbers.
     */
    public static double evaluate(String expression) throws Exception {
        // Normalize expression string
        expression = expression.replaceAll("\\s+", "");
        if (expression.isEmpty()) {
            return 0;
        }

        List<String> tokens = tokenize(expression);
        return evaluateTokens(tokens);
    }

    private static List<String> tokenize(String expr) throws Exception {
        List<String> tokens = new ArrayList<>();
        int len = expr.length();
        StringBuilder numBuffer = new StringBuilder();

        for (int i = 0; i < len; i++) {
            char c = expr.charAt(i);

            // Handle decimal numbers
            if (Character.isDigit(c) || c == '.') {
                numBuffer.append(c);
            } else {
                if (numBuffer.length() > 0) {
                    tokens.add(numBuffer.toString());
                    numBuffer.setLength(0);
                }

                // Handle negative numbers (unary minus)
                if (c == '-') {
                    boolean isUnary = false;
                    if (tokens.isEmpty()) {
                        isUnary = true;
                    } else {
                        String lastToken = tokens.get(tokens.size() - 1);
                        if (lastToken.length() == 1 && CalculatorUtils.isOperator(lastToken.charAt(0))) {
                            isUnary = true;
                        }
                    }

                    if (isUnary) {
                        numBuffer.append('-');
                        continue;
                    }
                }

                // Add standard operators
                if (CalculatorUtils.isOperator(c)) {
                    tokens.add(String.valueOf(c));
                } else {
                    throw new Exception("Invalid character: " + c);
                }
            }
        }

        if (numBuffer.length() > 0) {
            tokens.add(numBuffer.toString());
        }

        return tokens;
    }

    private static double evaluateTokens(List<String> tokens) throws Exception {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);

            if (token.length() > 1 || Character.isDigit(token.charAt(0)) || (token.charAt(0) == '-' && token.length() > 1)) {
                values.push(Double.parseDouble(token));
            } else if (token.length() == 1 && !Character.isDigit(token.charAt(0))) {
                char op = token.charAt(0);

                if (op == '%') {
                    // Percentage behaves as unary division by 100 on the preceding number
                    if (values.isEmpty()) throw new Exception("Invalid syntax with %");
                    double val = values.pop();
                    values.push(val / 100.0);
                } else {
                    while (!ops.isEmpty() && hasPrecedence(op, ops.peek())) {
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                    }
                    ops.push(op);
                }
            }
        }

        while (!ops.isEmpty()) {
            if (values.size() < 2) throw new Exception("Invalid syntax");
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        if (values.size() != 1) throw new Exception("Invalid evaluation state");
        return values.pop();
    }

    private static boolean hasPrecedence(char op1, char op2) {
        if ((op1 == '×' || op1 == '÷') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    private static double applyOp(char op, double b, double a) throws ArithmeticException {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '×':
                return a * b;
            case '÷':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
        }
        return 0;
    }
}
