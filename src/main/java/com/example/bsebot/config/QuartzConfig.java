package com.example.bsebot.config;

import com.example.bsebot.jobs.MyScheduledJob;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail myScheduledJobDetail() {
        return JobBuilder.newJob(MyScheduledJob.class)
                         .withIdentity("myScheduledJob")
                         .storeDurably()
                         .build();
    }

    @Bean
    public CronTriggerFactoryBean myScheduledJobTrigger(JobDetail myScheduledJobDetail) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(myScheduledJobDetail);
        trigger.setCronExpression("0/15 * * * * ?"); // Run every 5 seconds (example)
        trigger.setName("myScheduledJobTrigger");
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Trigger myScheduledJobTrigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(myScheduledJobTrigger);
        return schedulerFactoryBean;
    }
}
