package com.main.SatellitePathPredictionSpringBoot.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.SatellitePathPredictionSpringBoot.Entity.UCSSatelliteDatabase;

@Repository
public interface UCSSatelliteDatabaseDAO extends JpaRepository<UCSSatelliteDatabase, Integer> {
	List<UCSSatelliteDatabase> findByNorad(String noradId);


}
