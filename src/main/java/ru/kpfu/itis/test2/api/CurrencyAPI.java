package ru.kpfu.itis.test2.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyAPI {

    private static final String API_KEY = "";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/RUB";

    public static String getExchangeRate(String currencyCode) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            // Парсинг JSON
            JsonObject jsonResponse = JsonParser.parseString(content.toString()).getAsJsonObject();
            JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");
            double rate = rates.get(currencyCode.toUpperCase()).getAsDouble();

            return "Курс " + currencyCode.toUpperCase() + " к RUB: " + rate;
        } catch (Exception e) {
            e.printStackTrace();
            return "Не удалось получить курс валюты.";
        }
    }
}
