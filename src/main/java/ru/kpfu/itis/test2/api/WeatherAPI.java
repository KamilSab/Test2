package ru.kpfu.itis.test2.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPI {

    private static final String API_KEY = "3d5e1dd1a5685d5fe718369c";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    public static String getWeather(String city) {
        try {
            String urlString = String.format(API_URL, city, API_KEY);
            URL url = new URL(urlString);
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

            // Извлечение данных о погоде
            JsonObject main = jsonResponse.getAsJsonObject("main");
            JsonObject weather = jsonResponse.getAsJsonArray("weather").get(0).getAsJsonObject();

            double temperature = main.get("temp").getAsDouble();
            double feelsLike = main.get("feels_like").getAsDouble();
            int humidity = main.get("humidity").getAsInt();
            String description = weather.get("description").getAsString();

            // Форматирование строки с информацией о погоде
            return String.format("Погода в городе %s:\n" +
                            "Температура: %.1f°C\n" +
                            "Ощущается как: %.1f°C\n" +
                            "Влажность: %d%%\n" +
                            "Описание: %s",
                    city, temperature, feelsLike, humidity, description);

        } catch (Exception e) {
            e.printStackTrace();
            return "Не удалось получить данные о погоде.";
        }
    }

}
