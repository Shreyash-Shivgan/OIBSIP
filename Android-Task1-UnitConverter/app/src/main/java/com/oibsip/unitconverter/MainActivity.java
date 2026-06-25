package com.oibsip.unitconverter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerCategory;
    private Spinner spinnerFromUnit;
    private Spinner spinnerToUnit;
    private TextInputLayout inputLayoutValue;
    private TextInputEditText etInput;
    private MaterialButton btnConvert;
    private TextView tvResultValue;
    private TextView tvResultUnit;

    private ConversionManager conversionManager;
    private UnitCategory selectedCategory = UnitCategory.LENGTH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversionManager = new ConversionManager();

        initViews();
        setupCategorySpinner();
        setupListeners();
    }

    private void initViews() {
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        inputLayoutValue = findViewById(R.id.inputLayoutValue);
        etInput = findViewById(R.id.etInput);
        btnConvert = findViewById(R.id.btnConvert);
        tvResultValue = findViewById(R.id.tvResultValue);
        tvResultUnit = findViewById(R.id.tvResultUnit);
    }

    private void setupCategorySpinner() {
        ArrayAdapter<UnitCategory> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                UnitCategory.values()
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);
    }

    private void setupListeners() {
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = (UnitCategory) parent.getItemAtPosition(position);
                updateUnitSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    private void updateUnitSpinners() {
        String[] units = UnitConstants.getUnitsByCategory(selectedCategory);

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFromUnit.setAdapter(unitAdapter);
        spinnerToUnit.setAdapter(unitAdapter);

        // Pre-select different to-unit if possible for better UX
        if (units.length > 1) {
            spinnerToUnit.setSelection(1);
        }
    }

    private void performConversion() {
        String inputText = etInput.getText() != null ? etInput.getText().toString().trim() : "";

        if (TextUtils.isEmpty(inputText)) {
            inputLayoutValue.setError("Please enter a value");
            Toast.makeText(this, "Input value cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        inputLayoutValue.setError(null);

        double inputValue;
        try {
            inputValue = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            inputLayoutValue.setError("Invalid number format");
            Toast.makeText(this, "Please enter a valid numeric value", Toast.LENGTH_SHORT).show();
            return;
        }

        String fromUnit = spinnerFromUnit.getSelectedItem().toString();
        String toUnit = spinnerToUnit.getSelectedItem().toString();

        try {
            double result = conversionManager.convert(selectedCategory, inputValue, fromUnit, toUnit);

            // Format results cleanly (e.g. up to 6 decimal places, remove trailing zeros)
            String formattedResult;
            if (result == (long) result) {
                formattedResult = String.format(Locale.US, "%d", (long) result);
            } else {
                formattedResult = String.format(Locale.US, "%.6f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
            }

            tvResultValue.setText(formattedResult);
            tvResultUnit.setText(toUnit);
        } catch (Exception e) {
            Toast.makeText(this, "Error during conversion: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
