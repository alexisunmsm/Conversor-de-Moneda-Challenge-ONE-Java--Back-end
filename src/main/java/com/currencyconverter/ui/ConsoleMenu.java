package com.currencyconverter.ui;

import com.currencyconverter.model.Currency;
import com.currencyconverter.service.CurrencyConverterService;

import java.util.Scanner;

/**
 * Console-based user interface for the Currency Converter application.
 */
public class ConsoleMenu {
    private final Scanner scanner;
    private final CurrencyConverterService converterService;
    private boolean running;
    
    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
        this.converterService = new CurrencyConverterService();
        this.running = true;
    }
    
    /**
     * Starts the console menu interface.
     */
    public void start() {
        displayWelcomeMessage();
        
        // Main application loop
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Elija una opción válida: ");
            
            processMainMenuChoice(choice);
        }
        
        // Clean up resources
        scanner.close();
        System.out.println("\nGracias por usar el Conversor de Moneda. ¡Hasta pronto!");
    }
    
    /**
     * Displays a welcome message when the application starts.
     */
    private void displayWelcomeMessage() {
        System.out.println("=============================================");
        System.out.println("       CONVERSOR DE MONEDA - CHALLENGE ONE   ");
        System.out.println("=============================================");
        System.out.println("Sea bienvenido/a al Conversor de Moneda");
        System.out.println("Desarrollado como parte del Challenge ONE - Java - Back end");
        System.out.println("=============================================\n");
    }
    
    /**
     * Displays the main menu options.
     */
    private void displayMainMenu() {
        System.out.println("\n----- MENÚ PRINCIPAL -----");
        System.out.println("1. Convertir monedas");
        System.out.println("2. Ver tasas de cambio actuales");
        System.out.println("3. Actualizar tasas de cambio");
        System.out.println("0. Salir");
        System.out.println("---------------------------");
    }
    
    /**
     * Processes the user's choice from the main menu.
     * 
     * @param choice the user's menu selection
     */
    private void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                showConversionMenu();
                break;
            case 2:
                showCurrentRates();
                break;
            case 3:
                updateRates();
                break;
            case 0:
                running = false;
                break;
            default:
                System.out.println("Opción inválida. Por favor intente nuevamente.");
        }
    }
    
    /**
     * Displays the conversion menu and handles the currency conversion process.
     */
    private void showConversionMenu() {
        System.out.println("\n----- CONVERTIR MONEDAS -----");
        
        // Display available currencies
        displayAvailableCurrencies();
        
        // Get source currency
        Currency fromCurrency = getCurrencyInput("Seleccione la moneda de origen: ");
        if (fromCurrency == null) return;
        
        // Get target currency
        Currency toCurrency = getCurrencyInput("Seleccione la moneda de destino: ");
        if (toCurrency == null) return;
        
        // Get amount to convert
        double amount = getDoubleInput("Ingrese la cantidad a convertir: ");
        if (amount < 0) {
            System.out.println("La cantidad debe ser mayor que cero. Operación cancelada.");
            return;
        }
        
        // Perform conversion
        double result = converterService.convert(fromCurrency, toCurrency, amount);
        
        if (result == -1) {
            System.out.println("No se pudo realizar la conversión. Verifique la conexión a internet y las tasas de cambio.");
        } else {
            // Display result
            System.out.println("\n----- RESULTADO DE LA CONVERSIÓN -----");
            System.out.println(converterService.formatCurrency(amount) + " " + fromCurrency.getCode() + " = " + 
                              converterService.formatCurrency(result) + " " + toCurrency.getCode());
            System.out.println("Tasa de cambio actualizada el: " + converterService.getLastUpdated());
            System.out.println("---------------------------------------");
        }
    }
    
    /**
     * Displays all available currencies with their codes.
     */
    private void displayAvailableCurrencies() {
        System.out.println("Monedas disponibles:");
        for (int i = 0; i < Currency.values().length; i++) {
            Currency currency = Currency.values()[i];
            System.out.println((i + 1) + ". " + currency.toString());
        }
        System.out.println();
    }
    
    /**
     * Gets a currency selection from the user.
     * 
     * @param prompt the message to display to the user
     * @return the selected Currency or null if the selection was invalid
     */
    private Currency getCurrencyInput(String prompt) {
        int selection = getIntInput(prompt);
        
        if (selection >= 1 && selection <= Currency.values().length) {
            return Currency.values()[selection - 1];
        } else {
            System.out.println("Selección inválida.");
            return null;
        }
    }
    
    /**
     * Displays the current exchange rates.
     */
    private void showCurrentRates() {
        System.out.println("\n----- TASAS DE CAMBIO ACTUALES -----");
        System.out.println("Base: USD (Dólar Estadounidense)");
        System.out.println("Actualizado el: " + converterService.getLastUpdated());
        System.out.println("------------------------------------");
        
        // Display all exchange rates
        for (Currency currency : Currency.values()) {
            Double rate = converterService.getRates().get(currency.getCode());
            if (rate != null) {
                System.out.println(currency.getCode() + " (" + currency.getDisplayName() + "): " + rate);
            }
        }
    }
    
    /**
     * Updates the exchange rates from the API.
     */
    private void updateRates() {
        System.out.println("\nActualizando tasas de cambio...");
        
        boolean success = converterService.fetchLatestRates();
        
        if (success) {
            System.out.println("Tasas de cambio actualizadas correctamente.");
            System.out.println("Última actualización: " + converterService.getLastUpdated());
        } else {
            System.out.println("No se pudieron actualizar las tasas de cambio. Verifique su conexión a internet.");
        }
    }
    
    /**
     * Gets an integer input from the user.
     * 
     * @param prompt the message to display to the user
     * @return the entered integer
     */
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número.");
            }
        }
    }
    
    /**
     * Gets a double input from the user.
     * 
     * @param prompt the message to display to the user
     * @return the entered double
     */
    private double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número válido.");
            }
        }
    }
}