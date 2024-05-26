package com.example.bsebot.bsestocks;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class BSEResultsService {

    public static boolean botNotificationEnabled = true;

    public void runBSEResults() throws IOException, InterruptedException {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(10);
        JsonNode jsonNode = BSEClient.callBSEResultsEndpoint(startDate, endDate);


        Iterator<JsonNode> iterator = jsonNode.elements();
        TelegramBot bot = new TelegramBot();
        String chatId = "@bseorders"; // Replace with the actual chat ID (channel, group, or user)
        String messageText;
        Map<String, List<String>> resultsMap = new TreeMap<>();


        while (iterator.hasNext()) {
            JsonNode tableEntry = iterator.next();

            String meeting_date = tableEntry.path("meeting_date").asText();
            String sLongName = tableEntry.path("Long_Name").asText();
            String scripCD = tableEntry.path("scrip_Code").asText();
            if (!(StocksCache.getStocksInCache().contains(sLongName.toLowerCase()) || StocksCache.getStocksInCache().contains(scripCD.toLowerCase()))) {
                continue;
            }

            resultsMap.putIfAbsent(meeting_date, new ArrayList<>());
            resultsMap.get(meeting_date).add(sLongName);

        }

        messageText = "<b><u> Upcoming Results: </u></b>" + "\n\n";
        for (Map.Entry entry : resultsMap.entrySet()) {
            messageText += "<b><u>" + entry.getKey() + "</u></b> \n\n";
            for (String s : resultsMap.get(entry.getKey())) {
                messageText += s + "\n\n";
            }
            messageText += "\n";
        }

        System.out.println(messageText);
        if (botNotificationEnabled && !resultsMap.isEmpty()) {
            //System.out.println("Bot Notification Sent");
            bot.sendMessage(chatId, messageText);
        }
    }
}
