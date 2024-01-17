package com.example.bsebot.bsestocks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BSEClient {

    public static JsonNode callBSEServers(String categoryUrl, String subCategoryUrl, String START_DATE, String END_DATE) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                                      .followRedirects(HttpClient.Redirect.NORMAL)
                                      .build();
        String url = "https://api.bseindia.com/BseIndiaAPI/api/AnnSubCategoryGetData/w?pageno=1&strCat="+ categoryUrl +"&strPrevDate=" + START_DATE + "&strScrip=&strSearch=P&strToDate=" + END_DATE + "&strType=C&subcategory=" + subCategoryUrl;
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(url))
                                         .GET()
                                         .setHeader("authority", "api.bseindia.com")
                                         .setHeader("accept", "application/json, text/plain, */*")
                                         .setHeader("accept-language",
                                                    "en-US,en;q=0.9,gu-IN;q=0.8,gu;q=0.7,hi-IN;q=0.6,hi;q=0.5")
                                         .setHeader("origin", "https://www.bseindia.com")
                                         .setHeader("referer", "https://www.bseindia.com/")
                                         .setHeader("sec-ch-ua",
                                                    "\"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"")
                                         .setHeader("sec-ch-ua-mobile", "?0")
                                         .setHeader("sec-ch-ua-platform", "\"macOS\"")
                                         .setHeader("sec-fetch-dest", "empty")
                                         .setHeader("sec-fetch-mode", "cors")
                                         .setHeader("sec-fetch-site", "same-site")
                                         .setHeader("user-agent",
                                                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                                         .build();




        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.body());

    }
}
