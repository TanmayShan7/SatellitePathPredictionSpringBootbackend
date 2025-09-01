package com.main.SatellitePathPredictionSpringBoot.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "satellite_current_position_details", schema = "satellite")
public class SatelliteCurrentPositionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "satellite_name", referencedColumnName = "satellite_name")
	private SatelliteDetails satelliteDetails;

	@Column(name = "sat_latitude")
	private double satLatitude;

	@Column(name = "sat_longitude")
	private double satLongitude;
	
	@Column(name = "current_location_wkt")
	private String currentLocationWKT;
	

	@Column(name = "speed")
	private double speed;

	@Column(name = "azimuth")
	private double azimuth;

	@Column(name = "updated_date")
	private String updatedDate;

	@Column(name = "range")
	private double range;

	@Column(name = "footprintWKT", length = 30000)
	private String footprintWKT;

//	@Column(name = "footprint_visible_status", nullable = false)
//	private Boolean footprintVisibleStatus ;



	@Column(name = "elevation")
	private double elevation;

}
