package com.oibsip.unitconverter;

/**
 * Enum representing the unit categories available in the converter.
 */
public enum UnitCategory {
    LENGTH("Length"),
    WEIGHT("Weight"),
    TEMPERATURE("Temperature");

    private final String displayName;

    UnitCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
