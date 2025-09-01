package com.main.SatellitePathPredictionSpringBoot.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.main.SatellitePathPredictionSpringBoot.DTO.SatelliteDetailsDTO;

@Service
public interface SatelliteService {
	
	public List<String>	getAllSatelliteNames();
	
	public List<SatelliteDetailsDTO> getAllSatelliteDetails();

	

}
