package com.bettorleague.batch.step;

import com.bettorleague.batch.processor.TeamProcessor;
import com.bettorleague.batch.reader.TeamReader;
import com.bettorleague.batch.writer.TeamWriter;
import com.bettorleague.microservice.model.football.Team;
import com.bettorleague.service.TeamService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TeamStepProvider {
    @Value("${football-data.base-url:}")
    private String baseUrl;
    private final RestTemplate httpClient;
    private final TeamService teamService;

    public TeamStepProvider(RestTemplate httpClient, TeamService teamService) {
        this.teamService = teamService;
        this.httpClient = httpClient;
    }

    @Bean
    @StepScope
    public ItemReader<JsonNode> teamReader(@Value("#{jobParameters[competitionId]}") String competitionId) {
        return new TeamReader(httpClient, competitionId, baseUrl);
    }

    @Bean
    @StepScope
    public ItemProcessor<JsonNode, Team> teamProcessor() {
        return new TeamProcessor();
    }

    @Bean
    @StepScope
    public ItemWriter<Team> teamWriter() {
        return new TeamWriter(teamService);
    }

    @Bean
    Step teamStep(ItemReader<JsonNode> teamReader,
                  ItemProcessor<JsonNode, Team> teamProcessor,
                  ItemWriter<Team> teamWriter,
                  StepBuilderFactory stepBuilderFactory) {

        return stepBuilderFactory.get("teamStep")
                .<JsonNode, Team>chunk(1)
                .reader(teamReader)
                .processor(teamProcessor)
                .writer(teamWriter)
                .build();
    }
}
