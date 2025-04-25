package com.currencyconverter.service;

import com.currencyconverter.api.ExchangeRateClient;
import com.currencyconverter.model.Currency;
import com.currencyconverter.model.ExchangeRateResponse;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Service class responsible for currency conversion operations.
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
     * Fetches the latest exchange rates from the API.
     * 
     * @return true if rates were successfully fetched, false otherwise
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
     * Filters the API response to only include rates for supported currencies.
     * 
     * @param allRates map of all rates from the API
     * @return map containing only supported currency rates
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
     * Converts an amount from one currency to another.
     * 
     * @param fromCurrency the source currency
     * @param toCurrency the target currency
     * @param amount the amount to convert
     * @return the converted amount or -1 if conversion failed
     */
    public double convert(Currency fromCurrency, Currency toCurrency, double amount) {
        if (rates.isEmpty()) {
            if (!fetchLatestRates()) {
                return -1;
            }
        }
        
        // Get rates for both currencies (relative to USD)
        Double fromRate = rates.get(fromCurrency.getCode());
        Double toRate = rates.get(toCurrency.getCode());
        
        if (fromRate == null || toRate == null) {
            return -1;
        }
        
        // Calculate conversion (through USD as the base)
        if (fromCurrency == Currency.USD) {
            return amount * toRate;
        } else if (toCurrency == Currency.USD) {
            return amount / fromRate;
        } else {
            // Convert to USD first, then to target currency
            double amountInUSD = amount / fromRate;
            return amountInUSD * toRate;
        }
    }
    
    /**
     * Format a currency amount with the appropriate decimal places.
     * 
     * @param amount the amount to format
     * @return formatted currency string
     */
    public String formatCurrency(double amount) {
        return decimalFormat.format(amount);
    }
    
    /**
     * Gets all available exchange rates.
     * 
     * @return map of currency codes to their rates
     */
    public Map<String, Double> getRates() {
        return rates;
    }
    
    /**
     * Gets the last updated timestamp for the rates.
     * 
     * @return last updated timestamp
     */
    public String getLastUpdated() {
        return lastUpdated;
    }
}