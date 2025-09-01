package com.main.SatellitePathPredictionSpringBoot.DTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@ToString

public class SatelliteDetailsDTO {
private String satelliteName;
	private String norad;
	private String satelliteLine1;
	private String satelliteLine2;
	private String country_origin;
	private String category;
	private String operator;
	private String purpose;
	private String contractor;
	private String launchSite;
	private String launchVehicle;
	private String satelliteOrbit;
}
