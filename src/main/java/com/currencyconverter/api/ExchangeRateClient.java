package com.currencyconverter.api;

import com.currencyconverter.model.ExchangeRateResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Cliente para realizar solicitudes a la API de Exchange Rate
 */
public class ExchangeRateClient {
    private static final String API_KEY = "d2964208e056adacd9eec684";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY;
    
    private final HttpClient httpClient;
    private final Gson gson;
    
    public ExchangeRateClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        
        this.gson = new Gson();
    }
    
    /**
     * Obtiene las últimas tasas de cambio con USD como moneda base.
     * 
     * @return ExchangeRateResponse conteniendo las tasas o null si la solicitud falló
     */
    public ExchangeRateResponse getLatestRates() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/latest/USD"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            
            System.out.println("Obteniendo las últimas tasas de cambio...");
            
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                return parseResponse(response.body());
            } else {
                System.err.println("Error: La API retornó el código de estado " + response.statusCode());
                System.err.println("Respuesta: " + response.body());
                return null;
            }
        } catch (IOException e) {
            System.err.println("Error al conectar con la API: " + e.getMessage());
            return null;
        } catch (InterruptedException e) {
            System.err.println("La solicitud fue interrumpida: " + e.getMessage());
            Thread.currentThread().interrupt();
            return null;
        }
    }
    
    /**
     * Analiza la respuesta JSON de la API y la convierte en un objeto ExchangeRateResponse.
     * 
     * @param jsonResponse la cadena JSON de la API
     * @return ExchangeRateResponse parseado o null si el análisis falló
     */
    private ExchangeRateResponse parseResponse(String jsonResponse) {
        try {
            return gson.fromJson(jsonResponse, ExchangeRateResponse.class);
        } catch (JsonSyntaxException e) {
            System.err.println("Error al analizar la respuesta JSON: " + e.getMessage());
            return null;
        }
    }
}