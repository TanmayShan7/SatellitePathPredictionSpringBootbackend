package com.main.SatellitePathPredictionSpringBoot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.SatellitePathPredictionSpringBoot.DTO.SatelliteDetailsDTO;
import com.main.SatellitePathPredictionSpringBoot.Service.SatelliteService;

@RestController
@RequestMapping("/api/satellite")
public class SatelliteController {

	@Autowired
	SatelliteService satelliteService;

	@GetMapping("/")
	public List<String> getAllSatelliteNames() {

		return satelliteService.getAllSatelliteNames();
	}

	@GetMapping("/getAllSatelliteDetails")
	public List<SatelliteDetailsDTO> getAllSatelliteDetails() {
		return satelliteService.getAllSatelliteDetails();

	}

}
