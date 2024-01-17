package com.example.bsebot;

import com.example.bsebot.bsestocks.OrderCount;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class MainController {

    @GetMapping("/hello")
    public String hello() {
        CompletableFuture.runAsync(OrderCount::runBSEOrdersJob);
        System.out.println("Hello World");
        return "Hello, World!";
    }
}
