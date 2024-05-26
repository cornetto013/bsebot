package com.example.bsebot;

import com.example.bsebot.bsestocks.BSEResultsService;
import com.example.bsebot.bsestocks.OrderCountService;

import com.example.bsebot.bsestocks.StocksCache;
import jakarta.ws.rs.BadRequestException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RestController
public class MainController {


    @Autowired
    private OrderCountService orderCountService;

    @Autowired
    private BSEResultsService bseResultsService;

    @GetMapping("/ping")
    public String ping() {
        return "Welcome to BSE Bot!";
    }

    @GetMapping("/stocks")
    public Set<String> getStocks() {
        return StocksCache.getStocksInCache();
    }

    @GetMapping("/hello")
    public String hello() {
        CompletableFuture.runAsync(() -> orderCountService.runBSEOrdersJob());
        System.out.println("Hello World");
        return "Hello, World!";
    }

    @PutMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        Set<String> stocks = new HashSet<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            String csvSplitBy = ",";
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (!data[0].isEmpty()) {
                    stocks.add(data[0].toLowerCase());
                }
            }
            StocksCache.addStocksToCache(stocks);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed");
        }
    }

    @PostMapping("/stocks")
    public void addStocks(@RequestBody List<String> stocksList) {
        StocksCache.addStocksToCache(new HashSet<>(stocksList));
    }

    @DeleteMapping("/stocks")
    public void deleteStocks(@RequestBody List<String> stocksList) {
        StocksCache.removeStocksFromCache(new HashSet<>(stocksList));
    }

    @PostMapping("/results")
    public void runResults() {
        CompletableFuture.runAsync(() -> {
            try {
                bseResultsService.runBSEResults();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
