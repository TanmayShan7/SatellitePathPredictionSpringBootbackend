package com.main.SatellitePathPredictionSpringBoot.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.SatellitePathPredictionSpringBoot.Entity.SatelliteCurrentPositionDetails;

@Repository
public interface SatelliteCurrentPositionDetailsDAO extends JpaRepository<SatelliteCurrentPositionDetails, Integer>{

	  Optional<SatelliteCurrentPositionDetails> findBySatelliteDetails_SatelliteName(String satelliteName);

	
}
