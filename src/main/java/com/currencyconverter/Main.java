package com.currencyconverter;

import com.currencyconverter.ui.ConsoleMenu;

/**
 * Punto de entrada principal para la aplicación de Conversor de Moneda.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Currency Converter...");
        
        // Crear e iniciar el menú de consola

        ConsoleMenu menu = new ConsoleMenu();
        menu.start();
    }
}