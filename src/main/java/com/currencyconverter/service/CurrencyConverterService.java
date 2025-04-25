package com.currencyconverter.service;

import com.currencyconverter.api.ExchangeRateClient;
import com.currencyconverter.model.Currency;
import com.currencyconverter.model.ExchangeRateResponse;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de servicio responsable de las operaciones de conversión de moneda.
 */
public class CurrencyConverterService {
    private final ExchangeRateClient apiClient;
    private Map<String, Double> rates;
    private String lastUpdated;
    private final DecimalFormat decimalFormat;
    
    public CurrencyConverterService() {
        this.apiClient = new ExchangeRateClient();
        this.rates = new HashMap<>();
        this.decimalFormat = new DecimalFormat("#,##0.00");
        this.fetchLatestRates();
    }
    
    /**
     * Obtiene las tasas de cambio más recientes desde la API.
     * 
     * @return true si las tasas se obtuvieron con éxito, false en caso contrario
     */
    public boolean fetchLatestRates() {
        ExchangeRateResponse response = apiClient.getLatestRates();
        
        if (response != null && response.getConversion_rates() != null) {
            this.rates = filterSupportedRates(response.getConversion_rates());
            this.lastUpdated = response.getTime_last_update_utc();
            return true;
        }
        
        return false;
    }
    
    /**
     * Filtra la respuesta de la API para incluir solo tasas de monedas soportadas.
     * 
     * @param allRates mapa de todas las tasas desde la API
     * @return mapa que contiene solo las tasas de monedas soportadas
     */
    private Map<String, Double> filterSupportedRates(Map<String, Double> allRates) {
        Map<String, Double> filteredRates = new HashMap<>();
        
        for (Currency currency : Currency.values()) {
            String code = currency.getCode();
            if (allRates.containsKey(code)) {
                filteredRates.put(code, allRates.get(code));
            }
        }
        
        return filteredRates;
    }
    
    /**
     * Convierte una cantidad de una moneda a otra.
     * 
     * @param fromCurrency la moneda de origen
     * @param toCurrency la moneda de destino
     * @param amount la cantidad a convertir
     * @return la cantidad convertida o -1 si la conversión falló
     */
    public double convert(Currency fromCurrency, Currency toCurrency, double amount) {
        if (rates.isEmpty()) {
            if (!fetchLatestRates()) {
                return -1;
            }
        }
        
        // Obtiene las tasas para ambas monedas (relativas al USD)
        Double fromRate = rates.get(fromCurrency.getCode());
        Double toRate = rates.get(toCurrency.getCode());
        
        if (fromRate == null || toRate == null) {
            return -1;
        }
        
        // Calcula la conversión (a través del USD como base)
        if (fromCurrency == Currency.USD) {
            return amount * toRate;
        } else if (toCurrency == Currency.USD) {
            return amount / fromRate;
        } else {
            // Convierte primero a USD, luego a la moneda de destino
            double amountInUSD = amount / fromRate;
            return amountInUSD * toRate;
        }
    }
    
    /**
     * Formatea una cantidad de moneda con los decimales apropiados.
     * 
     * @param amount la cantidad a formatear
     * @return cadena de texto con la moneda formateada
     */
    public String formatCurrency(double amount) {
        return decimalFormat.format(amount);
    }
    
    /**
     * Obtiene todas las tasas de cambio disponibles.
     * 
     * @return mapa de códigos de moneda a sus tasas
     */
    public Map<String, Double> getRates() {
        return rates;
    }
    
    /**
     * Obtiene la marca de tiempo de la última actualización de las tasas.
     * 
     * @return marca de tiempo de la última actualización
     */
    public String getLastUpdated() {
        return lastUpdated;
    }
}