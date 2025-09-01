package com.main.SatellitePathPredictionSpringBoot.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "ucs_satellite_database",schema = "satellite")
public class UCSSatelliteDatabase {
	
	@Id
	@Column(name = "sat_id")
	private int satId;
	
	@Column(name = "satellite_name")
	private String satelliteName;
	
	@Column(name = "country_owner")
	private String countryOwner;
	
	@Column(name = "operator")
	private String operator;
	
	@Column(name = "users")
	private String users;
	
	@Column(name = "purpose")
	private String purpose;
	
	@Column(name = "detailedPurpose")
	private String detailedPurpose;
	
	@Column(name = "class_of_orbit")
	private String classOfOrbit;
	
	@Column(name = "contractor")
	private String contractor;
	
	@Column(name = "launch_site")
	private String launchSite;
	
	@Column(name = "launch_vehicle")
	private String launchVehicle;
	
	@Column(name = "norad")
	private String norad;

}



