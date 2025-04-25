package com.currencyconverter.model;

/**
 * Enum que representa las monedas soportadas con sus códigos y nombres de visualización.
 */
public enum Currency {
    ARS("ARS", "Argentine Peso"),
    BOB("BOB", "Bolivian Boliviano"),
    BRL("BRL", "Brazilian Real"),
    CLP("CLP", "Chilean Peso"),
    COP("COP", "Colombian Peso"),
    USD("USD", "US Dollar");
    
    private final String code;
    private final String displayName;
    
    Currency(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Get a currency by its code
     * 
     * @param code 
     * @return 
     */
    public static Currency getByCode(String code) {
        for (Currency currency : values()) {
            if (currency.getCode().equals(code)) {
                return currency;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return code + " - " + displayName;
    }
}