package com.bettorleague.repository;

import com.bettorleague.microservice.model.football.Team;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, String> {
    Optional<Team> findByName(String name);
}

