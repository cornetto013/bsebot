package com.example.bsebot.bsestocks;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Service
public class OrderCountService {

    public LocalDate lastResetDate;
    public Set<CorporateAnnouncement> corporateAnnouncementSet = new HashSet<>();

    public OrderCountService() {
        lastResetDate = LocalDate.of(2023, 12, 28);
        corporateAnnouncementSet.add(new CorporateAnnouncement("NEW ORDER RECEIVED",
                "Company%20Update",
                "Award%20of%20Order%20%2F%20Receipt%20of%20Order",
                0));
        corporateAnnouncementSet.add(new CorporateAnnouncement("PRESS RELEASE",
                "Company%20Update",
                "Press+Release+%2F+Media+Release",
                0));
        corporateAnnouncementSet.add(new CorporateAnnouncement("FINANCIAL RESULTS", "Result", "Financial+Results", 0));
    }

    public void runBSEOrdersJob() {

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

                int diff = 0;
                JsonNode jsonNode = BSEClient.callBSECorporateActionsEndpoint(c.categoryUrl, c.subCategoryUrl, startDate, endDate);
                int rowCntValue = jsonNode.path("Table1").get(0).path("ROWCNT").asInt();
                System.out.println(c.getDisplayName() + " ROWCNT value: " + rowCntValue + " " + LocalDateTime.now());
                if (c.getCount() != rowCntValue) {
                    diff = rowCntValue - c.getCount();
                    JsonNode tableArray = jsonNode.path("Table");
                    Iterator<JsonNode> iterator = tableArray.elements();
                    TelegramBot bot = new TelegramBot();
                    String chatId = "@bseorders"; // Replace with the actual chat ID (channel, group, or user)
                    String messageText;


                    while (iterator.hasNext() && diff > 0) {
                        JsonNode tableEntry = iterator.next();
                        String headlineValue = tableEntry.path("NEWSSUB").asText();
                        String nsURL = tableEntry.path("ATTACHMENTNAME").asText();
                        String time = tableEntry.path("News_submission_dt").asText().replace("T", " ");
                        String sLongName = tableEntry.path("SLONGNAME").asText();
                        String scripCD = tableEntry.path("SCRIP_CD").asText();
                        diff--;
                        if(!Objects.equals(c.getDisplayName(), "NEW ORDER RECEIVED") && !(StocksCache.getStocksInCache().contains(sLongName.toLowerCase()) || StocksCache.getStocksInCache().contains(scripCD.toLowerCase()))) {
                            continue;
                        }

                        messageText = "<b><u>" + c.displayName + "</u></b> \n\n" + "<b><u>Time:</u></b> " + time + "\nSUBJECT: " + headlineValue + "\n DOC " + "www.bseindia.com/xml-data/corpfiling/AttachLive/" + nsURL;
                        //System.out.println(messageText);
                        if (botNotificationEnabled) {
                            //System.out.println(messageText);
                            bot.sendMessage(chatId, messageText);
                        }
                    }
                    c.setCount(rowCntValue);

                }
            }

        }
        catch (Exception e) {
            System.out.println("ssanghvi Error Occurred" + e);
        }


    }

    private static void resetSet(Set<CorporateAnnouncement> corporateAnnouncementSet) {
        corporateAnnouncementSet.forEach(c -> c.setCount(0));
    }
}
