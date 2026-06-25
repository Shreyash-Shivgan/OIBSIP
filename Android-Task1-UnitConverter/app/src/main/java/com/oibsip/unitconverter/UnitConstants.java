package com.oibsip.unitconverter;

/**
 * Constants for the units supported by each category.
 */
public class UnitConstants {
    // Length units
    public static final String LENGTH_CM = "cm";
    public static final String LENGTH_M = "m";
    public static final String LENGTH_KM = "km";
    public static final String LENGTH_INCH = "inch";
    public static final String LENGTH_FEET = "feet";

    public static final String[] LENGTH_UNITS = {
            LENGTH_CM, LENGTH_M, LENGTH_KM, LENGTH_INCH, LENGTH_FEET
    };

    // Weight units
    public static final String WEIGHT_MG = "mg";
    public static final String WEIGHT_G = "g";
    public static final String WEIGHT_KG = "kg";
    public static final String WEIGHT_POUND = "pound";

    public static final String[] WEIGHT_UNITS = {
            WEIGHT_MG, WEIGHT_G, WEIGHT_KG, WEIGHT_POUND
    };

    // Temperature units
    public static final String TEMP_CELSIUS = "Celsius";
    public static final String TEMP_FAHRENHEIT = "Fahrenheit";
    public static final String TEMP_KELVIN = "Kelvin";

    public static final String[] TEMP_UNITS = {
            TEMP_CELSIUS, TEMP_FAHRENHEIT, TEMP_KELVIN
    };

    /**
     * Get the array of units corresponding to the selected category.
     */
    public static String[] getUnitsByCategory(UnitCategory category) {
        switch (category) {
            case LENGTH:
                return LENGTH_UNITS;
            case WEIGHT:
                return WEIGHT_UNITS;
            case TEMPERATURE:
                return TEMP_UNITS;
            default:
                return new String[0];
        }
    }
}
