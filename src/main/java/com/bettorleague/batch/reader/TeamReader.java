package com.bettorleague.batch.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TeamReader implements ItemReader<JsonNode> {
    private final RestTemplate restTemplate;
    private final String URL;
    private final List<JsonNode> jsonTeams;
    private int cursor;

    public TeamReader(RestTemplate restTemplate,
                      String competitionId,
                      String baseUrl) {
        this.restTemplate = restTemplate;
        this.URL = String.format("%s/v2/competitions/%s/teams", baseUrl, competitionId);
        this.cursor = 0;
        this.jsonTeams = this.fetchTeams();
    }

    @Override
    public JsonNode read(){
        return cursor < jsonTeams.size() ? jsonTeams.get(cursor++) : null;
    }


    private List<JsonNode> fetchTeams() {
        JsonNode jsonResponse = restTemplate.getForObject(URL, JsonNode.class);
        return Optional.ofNullable(jsonResponse)
                .filter(json -> json.has("teams"))
                .map(json -> json.get("teams"))
                .filter(JsonNode::isArray)
                .map(jsonTeams1 -> StreamSupport.stream(jsonTeams1.spliterator(), false).collect(Collectors.toList()))
                .orElseGet(List::of);
    }
}
