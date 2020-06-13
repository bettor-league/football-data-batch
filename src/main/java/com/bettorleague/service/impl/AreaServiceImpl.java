package com.bettorleague.service.impl;

import com.bettorleague.microservice.model.football.Area;
import com.bettorleague.repository.AreaRepository;
import com.bettorleague.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AreaServiceImpl implements AreaService {
    private AreaRepository areaRepository;

    public AreaServiceImpl(AreaRepository areaRepository){
        this.areaRepository = areaRepository;
    }

    @Override
    public Area saveArea(Area area) {
        Optional<Area> areaOptional = areaRepository.findByName(area.getName());
        if(areaOptional.isPresent()){
            return areaOptional.get();
        }
        area.setId(null);
        return areaRepository.save(area);
    }
}
