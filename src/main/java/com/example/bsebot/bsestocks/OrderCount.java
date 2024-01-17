package com.example.bsebot.bsestocks;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class OrderCount {

    public static void runBSEOrdersJob() {


        LocalDate lastResetDate = LocalDate.of(2023, 12, 28);

        Set<CorporateAnnouncement> corporateAnnouncementSet = new HashSet<>();
        corporateAnnouncementSet.add(new CorporateAnnouncement("NEW ORDER RECEIVED", "Company%20Update", "Award%20of%20Order%20%2F%20Receipt%20of%20Order", 0));
        corporateAnnouncementSet.add(new CorporateAnnouncement("PRESS RELEASE", "Company%20Update","Press+Release+%2F+Media+Release", 0));
        corporateAnnouncementSet.add(new CorporateAnnouncement("FINANCIAL RESULTS","Result","Financial+Results",0));
        while (true) {
            try {
                LocalDate endDate = LocalDate.now();
                LocalDate startDate = LocalDate.now();
                boolean botNotificationEnabled = true;
                if (lastResetDate.isBefore(startDate)) {
                    resetSet(corporateAnnouncementSet);
                    botNotificationEnabled = false;
                    lastResetDate = startDate;
                }
                for (CorporateAnnouncement c : corporateAnnouncementSet) {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                    String END_DATE = endDate.format(formatter);
                    String START_DATE = startDate.format(formatter);
                    int diff = 0;
                    JsonNode jsonNode = BSEClient.callBSEServers(c.categoryUrl, c.subCategoryUrl, START_DATE, END_DATE);
                    int rowCntValue = jsonNode.path("Table1").get(0).path("ROWCNT").asInt();
                    System.out.println(c.getDisplayName() + " ROWCNT value: " + rowCntValue + " " + LocalDateTime.now());
                    if (c.getCount() != rowCntValue) {
                        diff = rowCntValue - c.getCount();
                        JsonNode tableArray = jsonNode.path("Table");
                        Iterator<JsonNode> iterator = tableArray.elements();
                        TelegramBot bot = new TelegramBot();
                        String chatId = "@bseorders"; // Replace with the actual chat ID (channel, group, or user)
                        String messageText = "Hello, this is a test message from your Telegram bot!";


                        while (iterator.hasNext() && diff > 0) {
                            JsonNode tableEntry = iterator.next();
                            String headlineValue = tableEntry.path("NEWSSUB").asText();
                            String nsURL = tableEntry.path("ATTACHMENTNAME").asText();
                            String time = tableEntry.path("News_submission_dt").asText().replace("T", " ");
                            //System.out.println("Time: " + time + "\nNEWSSUB: " + headlineValue + "\n NSURL " + nsURL);

                            messageText = "<b><u>" + c.displayName + "</u></b> \n\n" + "<b><u>Time:</u></b> " + time + "\nSUBJECT: " + headlineValue + "\n DOC " + "www.bseindia.com/xml-data/corpfiling/AttachLive/" + nsURL;
                            //System.out.println(messageText);
                            if (botNotificationEnabled) {
                                //System.out.println("Bot Notification Sent");
                                bot.sendMessage(chatId, messageText);
                            }

                            diff--;
                        }
                        c.setCount(rowCntValue);

                    }


                }
                Thread.sleep(20000);

            } catch (Exception e) {
                System.out.println("ssanghvi Error Occurred" + e);
            }

        }
    }

    private static void resetSet(Set<CorporateAnnouncement> corporateAnnouncementSet) {
        corporateAnnouncementSet.forEach(c -> c.setCount(0));
    }
}
