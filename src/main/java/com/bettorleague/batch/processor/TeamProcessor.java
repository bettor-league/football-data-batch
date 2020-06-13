package com.bettorleague.batch.processor;


import com.bettorleague.microservice.model.football.Area;
import com.bettorleague.microservice.model.football.Source;
import com.bettorleague.microservice.model.football.Team;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.batch.item.ItemProcessor;

import java.util.Arrays;
import java.util.Map;

public class TeamProcessor implements ItemProcessor<JsonNode, Team> {
    @Override
    public Team process(JsonNode jsonTeam) {
        JsonNode jsonArea = jsonTeam.get("area");
        Area area = Area.builder()
                .id(null)
                .name(jsonArea.get("name").asText())
                .ids(Map.of(Source.FOOTBALL_DATA, jsonArea.get("id").asText()))
                .build();
        Team team = Team.builder()
                .id(null)
                .area(area)
                .name(jsonTeam.get("name").asText())
                .shortName(jsonTeam.get("shortName").asText())
                .acronym(jsonTeam.get("tla").asText())
                .logo(jsonTeam.get("crestUrl").asText())
                .address(jsonTeam.get("address").asText())
                .phone(jsonTeam.get("phone").asText())
                .website(jsonTeam.get("website").asText())
                .email(jsonTeam.get("email").asText())
                .founded(jsonTeam.get("founded").asInt())
                .colors(Arrays.asList(jsonTeam.get("clubColors").asText().split(" / ")))
                .stadium(jsonTeam.get("venue").asText())
                .ids(Map.of(Source.FOOTBALL_DATA, String.valueOf(jsonTeam.get("id").asInt())))
                .build();
        return team;
    }
}
