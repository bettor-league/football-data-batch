package com.bettorleague.repository;

import com.bettorleague.microservice.model.football.Area;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends PagingAndSortingRepository<Area, String> {
    Optional<Area> findByName(String name);
}

