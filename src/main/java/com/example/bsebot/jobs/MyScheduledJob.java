package com.example.bsebot.jobs;

import com.example.bsebot.bsestocks.OrderCount;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyScheduledJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Your scheduled task logic goes here
        OrderCount.runBSEOrdersJob();
    }
}
