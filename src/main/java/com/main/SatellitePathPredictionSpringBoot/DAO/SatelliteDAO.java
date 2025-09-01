package com.main.SatellitePathPredictionSpringBoot.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.SatellitePathPredictionSpringBoot.Entity.SatelliteDetails;

@Repository
public interface SatelliteDAO extends JpaRepository<SatelliteDetails, String> {

}
