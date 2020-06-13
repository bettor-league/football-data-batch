package com.bettorleague.service.impl;

import com.bettorleague.microservice.model.football.Area;
import com.bettorleague.microservice.model.football.Team;
import com.bettorleague.repository.TeamRepository;
import com.bettorleague.service.AreaService;
import com.bettorleague.service.TeamService;
import org.assertj.core.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {
    private final AreaService areaService;
    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository,
                           AreaService areaService){
        this.teamRepository = teamRepository;
        this.areaService = areaService;
    }

    @Override
    public Team saveTeam(Team team) {
        Optional<Team> teamOptional = teamRepository.findByName(team.getName());

        if(teamOptional.isPresent()){
            return teamOptional.get();
        }

        Area area = areaService.saveArea(team.getArea());
        assert !Strings.isNullOrEmpty(area.getId());

        team.setArea(area);
        team.setId(null);

        return teamRepository.save(team);
    }
}
