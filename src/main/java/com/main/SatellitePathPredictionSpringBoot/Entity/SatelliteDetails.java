package com.main.SatellitePathPredictionSpringBoot.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Table(name = "satellite_details", schema = "satellite")
public class SatelliteDetails {
	@Id
	@Column(name = "satellite_name")
	private String satelliteName;
	
	@Column(name = "norad")
	private String norad;

	@Column(name = "satellite_line1")
	private String satelliteLine1;

	@Column(name = "satellite_line2")
	private String satelliteLine2;
	
	@Column(name = "country_orgin")
	private String country_origin;

	@Column(name = "category")
	private String category;
	
	@Column(name = "operator")
	private String operator;
	
	@Column(name = "purpose")
	private String purpose;
	
	@Column(name = "contractor")
	private String contractor;
	
	@Column(name = "launch_site")
	private String launchSite;
	
	@Column(name = "launch_vehicle")
	private String launchVehicle;
	
	
	@Column(name = "satellite_orbit", length = 30000)
	private String satelliteOrbit;

	
	// One-to-many relationship with currentPositionDetails
	@OneToMany(mappedBy = "satelliteDetails", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SatelliteCurrentPositionDetails> currentPositionDetails;
	
	
	

}
