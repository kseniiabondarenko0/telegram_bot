package com.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main extends TelegramLongPollingBot {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Main());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "bimbabot"; // замініть на ім'я вашого бота
    }

    @Override
    public String getBotToken() {
        return "7261151219:AAFHtZZwgY9hudAx2Lt33KPfSPylsYx-_M0"; // замініть на токен вашого бота
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            WeatherApiClient client = new WeatherApiClient();
            try {
                WeatherData weather = client.getWeather(messageText);

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));


                String s = ("\nTemperature: " + weather.getTemperature() + "°C");
                s += ("\nHumidity: " + weather.getHumidity() + "%");
                s += ("\nWind Speed: " + weather.getWindSpeed() + " m/s");
                message.setText("Weather conditions: \n" + s);
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            } catch (IOException ioException) {

            }

        }

    }
}


