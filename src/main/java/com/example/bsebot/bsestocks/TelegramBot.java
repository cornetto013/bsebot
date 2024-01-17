package com.example.bsebot.bsestocks;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    // Replace "YOUR_BOT_TOKEN" with your actual bot token
    private final String BOT_TOKEN = "6901014908:AAGnjwkNIXFZRtcXvfLx8Vi6DBdwznptW8k";

    @Override
    public String getBotUsername() {
        return "@bse_orders_bot"; // Replace with your bot's username
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // Handle incoming updates (if needed)
    }

    public void sendMessage(String chatId, String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        message.enableMarkdown(true);
        message.enableHtml(true);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TelegramBot bot = new TelegramBot();
        String chatId = "@bseorders"; // Replace with the actual chat ID (channel, group, or user)
        String messageText = "Hello, this is a test message from your Telegram bot!";
        bot.sendMessage(chatId, messageText);
    }
}
