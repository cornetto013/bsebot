package com.example.bsebot.bsestocks;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class ListSecurities {

    public static void main(String[] args) {
        System.out.println("List of securities");
        Map<String, List<String>> securitiesMap = new HashMap<>();
//        JsonNode jsonNode = BSEClient.getSecuritiesList();
//
//
//        Iterator<JsonNode> iterator = jsonNode.elements();
//
//        while (iterator.hasNext()) {
//            JsonNode tableEntry = iterator.next();
//            securitiesMap.putIfAbsent(tableEntry.path("SCRIP_CD").asText(), Arrays.asList(tableEntry.path("Scrip_Name").asText(), tableEntry.path("scrip_id").asText()));
//        }
//
//        try (PrintWriter writer = new PrintWriter(new FileWriter("/Users/siddharthsanghvi/Desktop/securities.csv"))) {
//            // Write header
//            writer.println("SCRIP_CD,Scrip_Name, scrip_id");
//
//            // Write map entries
//            for (Map.Entry<String, List<String>> entry : securitiesMap.entrySet()) {
//                writer.println(entry.getKey() + "," + entry.getValue().get(0) + ',' + entry.getValue().get(1));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
