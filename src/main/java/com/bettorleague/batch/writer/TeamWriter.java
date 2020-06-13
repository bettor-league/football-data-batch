package com.bettorleague.batch.writer;

import com.bettorleague.microservice.model.football.Team;
import com.bettorleague.service.TeamService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TeamWriter implements ItemWriter<Team> {
    private final TeamService teamService;

    public TeamWriter(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void write(List<? extends Team> teams) {
        Optional<Team> optionalTeam = Optional.of(teams)
                .filter(items -> items.size() > 0)
                .map(items -> items.get(0));
        if(optionalTeam.isEmpty()) return;
        teamService.saveTeam(optionalTeam.get());
    }
}
