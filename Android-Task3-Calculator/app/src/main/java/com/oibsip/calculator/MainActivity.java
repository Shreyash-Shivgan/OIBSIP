package com.oibsip.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvExpression;
    private TextView tvResult;
    private HorizontalScrollView scrollExpression;
    private CalculatorEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        engine = new CalculatorEngine();

        initViews();
        setupKeyListeners();
    }

    private void initViews() {
        tvExpression = findViewById(R.id.tvExpression);
        tvResult = findViewById(R.id.tvResult);
        scrollExpression = findViewById(R.id.scrollExpression);
    }

    private void setupKeyListeners() {
        // Numbers
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
        findViewById(R.id.btnDot).setOnClickListener(this);

        // Operators
        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnSubtract).setOnClickListener(this);
        findViewById(R.id.btnMultiply).setOnClickListener(this);
        findViewById(R.id.btnDivide).setOnClickListener(this);
        findViewById(R.id.btnPercentage).setOnClickListener(this);

        // Actions
        findViewById(R.id.btnClear).setOnClickListener(this);
        findViewById(R.id.btnBackspace).setOnClickListener(this);
        findViewById(R.id.btnToggleSign).setOnClickListener(this);
        findViewById(R.id.btnEqual).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn0) engine.appendNumber("0");
        else if (id == R.id.btn1) engine.appendNumber("1");
        else if (id == R.id.btn2) engine.appendNumber("2");
        else if (id == R.id.btn3) engine.appendNumber("3");
        else if (id == R.id.btn4) engine.appendNumber("4");
        else if (id == R.id.btn5) engine.appendNumber("5");
        else if (id == R.id.btn6) engine.appendNumber("6");
        else if (id == R.id.btn7) engine.appendNumber("7");
        else if (id == R.id.btn8) engine.appendNumber("8");
        else if (id == R.id.btn9) engine.appendNumber("9");
        else if (id == R.id.btnDot) engine.appendDecimal();
        else if (id == R.id.btnAdd) engine.appendOperator("+");
        else if (id == R.id.btnSubtract) engine.appendOperator("-");
        else if (id == R.id.btnMultiply) engine.appendOperator("×");
        else if (id == R.id.btnDivide) engine.appendOperator("÷");
        else if (id == R.id.btnPercentage) engine.appendOperator("%");
        else if (id == R.id.btnClear) engine.clear();
        else if (id == R.id.btnBackspace) engine.backspace();
        else if (id == R.id.btnToggleSign) engine.toggleSign();
        else if (id == R.id.btnEqual) engine.calculate();

        updateDisplay();
    }

    private void updateDisplay() {
        tvExpression.setText(engine.getExpression());
        tvResult.setText(engine.getResult());

        // Auto-scroll expression scrollview to the end when text grows
        scrollExpression.post(() -> scrollExpression.fullScroll(View.FOCUS_RIGHT));
    }
}
