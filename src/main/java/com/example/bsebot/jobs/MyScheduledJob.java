package com.example.bsebot.jobs;

import com.example.bsebot.bsestocks.BSEResultsService;
import com.example.bsebot.bsestocks.OrderCountService;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyScheduledJob {

    @Autowired
    private OrderCountService orderCountService;

    @Autowired
    private BSEResultsService bseResultsService;

    @Scheduled(fixedDelay = 5000, initialDelay = 30000) //
    public void runBSEOrdersJob() {
        orderCountService.runBSEOrdersJob();
    }

    @Scheduled(cron = "0 0 3 * * ?") //
    public void runBSEResultsJob() {
        System.out.println("Running BSE Results Job");
        try {
            bseResultsService.runBSEResults();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}