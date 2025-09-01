package com.main.SatellitePathPredictionSpringBoot.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.main.SatellitePathPredictionSpringBoot.DTO.SatelliteCurrentPositionDetailsDTO;

@Service

public interface SatellitePostionGeneratorService {

	public List<SatelliteCurrentPositionDetailsDTO> getSatellitePostionByName(String satelliteName);
}
