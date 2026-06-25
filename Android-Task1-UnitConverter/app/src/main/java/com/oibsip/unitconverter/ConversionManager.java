package com.oibsip.unitconverter;

/**
 * Handles unit conversion operations for all supported categories.
 */
public class ConversionManager {

    /**
     * Converts a given value from one unit to another within a category.
     *
     * @param category    The category of units (Length, Weight, Temperature).
     * @param value       The numerical value to convert.
     * @param sourceUnit  The unit to convert from.
     * @param targetUnit  The unit to convert to.
     * @return The converted value.
     * @throws IllegalArgumentException If units do not match or are unsupported.
     */
    public double convert(UnitCategory category, double value, String sourceUnit, String targetUnit) {
        if (sourceUnit.equals(targetUnit)) {
            return value;
        }

        switch (category) {
            case LENGTH:
                return convertLength(value, sourceUnit, targetUnit);
            case WEIGHT:
                return convertWeight(value, sourceUnit, targetUnit);
            case TEMPERATURE:
                return convertTemperature(value, sourceUnit, targetUnit);
            default:
                throw new IllegalArgumentException("Unsupported category: " + category);
        }
    }

    private double convertLength(double value, String from, String to) {
        // Convert input value to meters (base unit)
        double valueInMeters;
        switch (from) {
            case UnitConstants.LENGTH_CM:
                valueInMeters = value / 100.0;
                break;
            case UnitConstants.LENGTH_M:
                valueInMeters = value;
                break;
            case UnitConstants.LENGTH_KM:
                valueInMeters = value * 1000.0;
                break;
            case UnitConstants.LENGTH_INCH:
                valueInMeters = value * 0.0254;
                break;
            case UnitConstants.LENGTH_FEET:
                valueInMeters = value * 0.3048;
                break;
            default:
                throw new IllegalArgumentException("Unknown length unit: " + from);
        }

        // Convert base unit (meters) to target unit
        switch (to) {
            case UnitConstants.LENGTH_CM:
                return valueInMeters * 100.0;
            case UnitConstants.LENGTH_M:
                return valueInMeters;
            case UnitConstants.LENGTH_KM:
                return valueInMeters / 1000.0;
            case UnitConstants.LENGTH_INCH:
                return valueInMeters / 0.0254;
            case UnitConstants.LENGTH_FEET:
                return valueInMeters / 0.3048;
            default:
                throw new IllegalArgumentException("Unknown length unit: " + to);
        }
    }

    private double convertWeight(double value, String from, String to) {
        // Convert input value to grams (base unit)
        double valueInGrams;
        switch (from) {
            case UnitConstants.WEIGHT_MG:
                valueInGrams = value / 1000.0;
                break;
            case UnitConstants.WEIGHT_G:
                valueInGrams = value;
                break;
            case UnitConstants.WEIGHT_KG:
                valueInGrams = value * 1000.0;
                break;
            case UnitConstants.WEIGHT_POUND:
                valueInGrams = value * 453.59237;
                break;
            default:
                throw new IllegalArgumentException("Unknown weight unit: " + from);
        }

        // Convert base unit (grams) to target unit
        switch (to) {
            case UnitConstants.WEIGHT_MG:
                return valueInGrams * 1000.0;
            case UnitConstants.WEIGHT_G:
                return valueInGrams;
            case UnitConstants.WEIGHT_KG:
                return valueInGrams / 1000.0;
            case UnitConstants.WEIGHT_POUND:
                return valueInGrams / 453.59237;
            default:
                throw new IllegalArgumentException("Unknown weight unit: " + to);
        }
    }

    private double convertTemperature(double value, String from, String to) {
        // Convert to Celsius (base unit)
        double valueInCelsius;
        switch (from) {
            case UnitConstants.TEMP_CELSIUS:
                valueInCelsius = value;
                break;
            case UnitConstants.TEMP_FAHRENHEIT:
                valueInCelsius = (value - 32.0) * 5.0 / 9.0;
                break;
            case UnitConstants.TEMP_KELVIN:
                valueInCelsius = value - 273.15;
                break;
            default:
                throw new IllegalArgumentException("Unknown temperature unit: " + from);
        }

        // Convert base unit (Celsius) to target unit
        switch (to) {
            case UnitConstants.TEMP_CELSIUS:
                return valueInCelsius;
            case UnitConstants.TEMP_FAHRENHEIT:
                return (valueInCelsius * 9.0 / 5.0) + 32.0;
            case UnitConstants.TEMP_KELVIN:
                return valueInCelsius + 273.15;
            default:
                throw new IllegalArgumentException("Unknown temperature unit: " + to);
        }
    }
}
