package com.example.bsebot.bsestocks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BSEClient {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");


    public static JsonNode callBSECorporateActionsEndpoint(String categoryUrl, String subCategoryUrl, LocalDate startDate, LocalDate endDate) throws IOException, InterruptedException {
        String END_DATE = endDate.format(formatter);
        String START_DATE = startDate.format(formatter);

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

    public static JsonNode callBSEResultsEndpoint(LocalDate startDate, LocalDate endDate) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String END_DATE = endDate.format(formatter);
        String START_DATE = startDate.format(formatter);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.bseindia.com/BseIndiaAPI/api/Corpforthresults/w?fromdate=" + START_DATE + "&scripcode=&todate="+ END_DATE))
                .GET()
                .setHeader("accept", "application/json, text/plain, */*")
                .setHeader("accept-language", "en-US,en;q=0.9,gu-IN;q=0.8,gu;q=0.7,hi-IN;q=0.6,hi;q=0.5")
                .setHeader("origin", "https://www.bseindia.com")
                .setHeader("priority", "u=1, i")
                .setHeader("referer", "https://www.bseindia.com/")
                .setHeader("sec-ch-ua", "\"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"")
                .setHeader("sec-ch-ua-mobile", "?0")
                .setHeader("sec-ch-ua-platform", "\"macOS\"")
                .setHeader("sec-fetch-dest", "empty")
                .setHeader("sec-fetch-mode", "cors")
                .setHeader("sec-fetch-site", "same-site")
                .setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.body());

    }

    public static JsonNode getSecuritiesList() {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.bseindia.com/BseIndiaAPI/api/ListofScripData/w?Group=B&Scripcode=&industry=&segment=Equity&status=Active"))
                .GET()
                .setHeader("accept", "application/json, text/plain, */*")
                .setHeader("accept-language", "en-US,en;q=0.9,gu-IN;q=0.8,gu;q=0.7,hi-IN;q=0.6,hi;q=0.5")
                .setHeader("origin", "https://www.bseindia.com")
                .setHeader("priority", "u=1, i")
                .setHeader("referer", "https://www.bseindia.com/")
                .setHeader("sec-ch-ua", "\"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"")
                .setHeader("sec-ch-ua-mobile", "?0")
                .setHeader("sec-ch-ua-platform", "\"macOS\"")
                .setHeader("sec-fetch-dest", "empty")
                .setHeader("sec-fetch-mode", "cors")
                .setHeader("sec-fetch-site", "same-site")
                .setHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
