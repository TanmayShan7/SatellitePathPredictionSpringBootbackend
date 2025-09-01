package com.main.SatellitePathPredictionSpringBoot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.SatellitePathPredictionSpringBoot.DTO.SatelliteCurrentPositionDetailsDTO;
import com.main.SatellitePathPredictionSpringBoot.Entity.SatelliteCurrentPositionDetails;
import com.main.SatellitePathPredictionSpringBoot.Service.SatellitePostionGeneratorService;
import com.main.SatellitePathPredictionSpringBoot.Service.SatellitePostionGeneratorServiceIImpl;

@RestController
@RequestMapping("/api/currentLocation")

public class SatelliteCurrentPositionDetailsController {

	@Autowired
	SatellitePostionGeneratorServiceIImpl generatorServiceIImpl;
	
	@GetMapping("/{satelliteName}")
	public List<SatelliteCurrentPositionDetailsDTO> getSatellitePostion(@PathVariable String satelliteName) {

//		generatorServiceIImpl.getSatellitePostionByName(satelliteName);
		return generatorServiceIImpl.getSatellitePostionByName(satelliteName);

	}

}
