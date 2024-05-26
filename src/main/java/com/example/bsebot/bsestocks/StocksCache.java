package com.example.bsebot.bsestocks;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StocksCache {

    public static Set<String> stocksInCache = new HashSet<>();

    public static Set<String> getStocksInCache() {
        return stocksInCache;
    }

    public static void addStocksToCache(Set<String> stocks) {
        stocksInCache.addAll(stocks);
    }

    public static void removeStocksFromCache(Set<String> stocks) {
        stocksInCache.removeAll(stocks);
    }

}
