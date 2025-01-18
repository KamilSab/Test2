package ru.kpfu.itis.test2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.kpfu.itis.test2.api.CurrencyAPI;
import ru.kpfu.itis.test2.api.WeatherAPI;

public class ChatBotApp extends Application {

    private TextArea chatArea;
    private TextField inputField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat Bot");

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        inputField = new TextField();
        inputField.setPromptText("Enter command here...");

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> processCommand());

        inputField.setOnAction(e -> processCommand());

        VBox layout = new VBox(10, chatArea, inputField, sendButton);
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void processCommand() {
        String command = inputField.getText().trim();
        inputField.clear();

        if (command.isEmpty()) {
            return;
        }

        chatArea.appendText("You: " + command + "\n");

        switch (command.split(" ")[0]) {
            case "list":
                listCommands();
                break;
            case "weather":
                getWeather(command);
                break;
            case "exchange":
            case "rate":
                getExchangeRate(command);
                break;
            case "quit":
                quit();
                break;
            default:
                chatArea.appendText("Bot: Unknown command. Type 'list' for available commands.\n");
        }
    }

    private void listCommands() {
        chatArea.appendText("Bot: Available commands:\n");
        chatArea.appendText(" - list: List all commands\n");
        chatArea.appendText(" - weather [city]: Get weather in the specified city\n");
        chatArea.appendText(" - exchange/rate [currency]: Get exchange rate for the specified currency\n");
        chatArea.appendText(" - quit: Return to main page\n");
    }

    private void getWeather(String command) {
        String[] parts = command.split(" ");
        if (parts.length < 2) {
            chatArea.appendText("Bot: Please specify a city.\n");
            return;
        }
        String city = parts[1];
        String weather = WeatherAPI.getWeather(city);
        chatArea.appendText("Bot: " + weather + "\n");
    }

    private void getExchangeRate(String command) {
        String[] parts = command.split(" ");
        if (parts.length < 2) {
            chatArea.appendText("Bot: Please specify a currency code.\n");
            return;
        }
        String currencyCode = parts[1];
        String rate = CurrencyAPI.getExchangeRate(currencyCode);
        chatArea.appendText("Bot: " + rate + "\n");
    }

    private void quit() {
        chatArea.appendText("Bot: Returning to main page...\n");
        // Здесь можно добавить логику для возврата на главную страницу
    }
}
