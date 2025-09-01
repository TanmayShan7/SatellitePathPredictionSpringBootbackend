package com.main.SatellitePathPredictionSpringBoot.DTO;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Transactional
public class SatelliteCurrentPositionDetailsDTO {

	private String satelliteName;

	private double satLatitude;
	private double satLongitude;
	private String currentLocationWKT;
	private double speed;
	private double azimuth;
	private String updatedDate;
	private double range;
	private double elevation;
	private String footprintWKT;
//	private Boolean footprintVisibleStatus;

}
