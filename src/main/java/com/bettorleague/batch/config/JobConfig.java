package com.bettorleague.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {

    @Bean
    Job footballDataJob(JobBuilderFactory jobBuilderFactory,
                        Step teamStep) {
        return jobBuilderFactory.get("restCompetitionJob")
                .incrementer(new RunIdIncrementer())
                .start(teamStep)
                .build();
    }
}
