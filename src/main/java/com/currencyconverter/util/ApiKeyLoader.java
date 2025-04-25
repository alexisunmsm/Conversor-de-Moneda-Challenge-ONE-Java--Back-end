package com.currencyconverter.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Utility class for loading API keys from configuration files.
 * This makes it easier to manage API keys without hardcoding them.
 */
public class ApiKeyLoader {
    private static final String CONFIG_FILE = "config.properties";
    private static final String API_KEY_PROPERTY = "exchange.api.key";
    
    /**
     * Loads the API key from the configuration file.
     * 
     * @return the API key or null if not found
     */
    public static String loadApiKey() {
        // Check if config file exists
        Path configPath = Paths.get(CONFIG_FILE);
        if (!Files.exists(configPath)) {
            createDefaultConfigFile(configPath);
            return null;
        }
        
        // Load properties from config file
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            
            String apiKey = properties.getProperty(API_KEY_PROPERTY);
            if (apiKey == null || apiKey.isEmpty() || apiKey.equals("YOUR_API_KEY")) {
                System.err.println("API key not found or not set in " + CONFIG_FILE);
                return null;
            }
            
            return apiKey;
        } catch (IOException e) {
            System.err.println("Error loading API key: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Creates a default configuration file if one doesn't exist.
     * 
     * @param configPath path to the configuration file
     */
    private static void createDefaultConfigFile(Path configPath) {
        try {
            // Create a properties object with default values
            Properties defaultProps = new Properties();
            defaultProps.setProperty(API_KEY_PROPERTY, "YOUR_API_KEY");
            
            // Write to file with comments
            defaultProps.store(Files.newOutputStream(configPath), "Currency Converter Configuration");
            
            System.out.println("A configuration file has been created at: " + configPath.toAbsolutePath());
            System.out.println("Please edit this file and add your Exchange Rate API key.");
        } catch (IOException e) {
            System.err.println("Error creating configuration file: " + e.getMessage());
        }
    }
}