package com.currencyconverter;

import com.currencyconverter.ui.ConsoleMenu;

/**
 * Main entry point for the Currency Converter application.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing Currency Converter...");
        
        // Create and start the console menu
        ConsoleMenu menu = new ConsoleMenu();
        menu.start();
    }
}