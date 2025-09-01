package com.main.SatellitePathPredictionSpringBoot.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.amsacode.predict4java.GroundStationPosition;
import com.github.amsacode.predict4java.Position;
import com.github.amsacode.predict4java.SatPos;
import com.github.amsacode.predict4java.Satellite;
import com.github.amsacode.predict4java.SatelliteFactory;
import com.github.amsacode.predict4java.TLE;
import com.main.SatellitePathPredictionSpringBoot.DAO.SatelliteCurrentPositionDetailsDAO;
import com.main.SatellitePathPredictionSpringBoot.DAO.SatelliteDAO;
import com.main.SatellitePathPredictionSpringBoot.DTO.SatelliteCurrentPositionDetailsDTO;
import com.main.SatellitePathPredictionSpringBoot.Entity.SatelliteCurrentPositionDetails;
import com.main.SatellitePathPredictionSpringBoot.Entity.SatelliteDetails;

import jakarta.transaction.Transactional;

@Service
public class SatellitePostionGeneratorServiceIImpl implements SatellitePostionGeneratorService {

	@Autowired
	SatelliteDAO daoSatelliteDAO;

	@Autowired
	SatelliteCurrentPositionDetailsDAO satelliteCurrentPositionDetailsDAO;

//	@Override
//	public List<SatelliteCurrentPositionDetailsDTO> getSatellitePostionByName(String satelliteName) {
//		SatelliteCurrentPositionDetailsDTO satelliteCurrentPositionDetailsDTO = new SatelliteCurrentPositionDetailsDTO();
//
//		Optional<SatelliteCurrentPositionDetails> recordChecker = satelliteCurrentPositionDetailsDAO
//				.findBySatelliteDetails_SatelliteName(satelliteName);
//
//		SatelliteCurrentPositionDetails entity = null;
//		
//		System.out.println(recordChecker.isPresent()); 
//		if (!recordChecker.isPresent()) {
//
//
//			SatelliteDetails satelliteDetails = daoSatelliteDAO.getReferenceById(satelliteName);
//			String tleData = satelliteName + "\n" + satelliteDetails.getSatelliteLine1() + "\n"
//					+ satelliteDetails.getSatelliteLine2() + "\n";
//			InputStream is = new ByteArrayInputStream(tleData.getBytes());
//			List<TLE> tleList;
//
//			try {
//				tleList = TLE.importSat(is);
//				TLE tle = new TLE(tleList.get(0));
//				Satellite satellite = SatelliteFactory.createSatellite(tle);
//				Date date = new Date();
//				GroundStationPosition qth = new GroundStationPosition(12.9716, 77.5946, 0.0); // Lat, Lon, Alt 
//
//				SatPos satPos = satellite.getPosition(qth, date);
//				System.out.println(satPos);
//
//				double lon = Math.toDegrees(satPos.getLongitude());
//				double lat = Math.toDegrees(satPos.getLatitude());
//
//				String satellitePositionWKT = "POINT(" + Math.toDegrees(satPos.getLongitude()) + " "
//						+ Math.toDegrees(satPos.getLatitude()) + ")";
//
//
//				List<Position> footprintList = satPos.getRangeCircle();
//////		System.out.println(satPos.getRangeCircle());
//				StringBuilder footprintWKT = new StringBuilder("LINESTRING(");
//
//				for (int i = 0; i < footprintList.size(); i++) {
//					Position pos = footprintList.get(i);
//					footprintWKT.append(String.format("%.6f %.6f", pos.getLon(), pos.getLat()));
//					if (i != footprintList.size() - 1) {
//						footprintWKT.append(", ");
//					}
//
//				}
//				
//				footprintWKT.append(")");
//				
//				
//				
//				satelliteCurrentPositionDetailsDTO.setSatelliteName(satelliteName); // setting satellite name in DTO class
//				satelliteCurrentPositionDetailsDTO.setSatLongitude(lon); // setting satellite longitude in DTO class
//				satelliteCurrentPositionDetailsDTO.setSatLatitude(lat); // setting satellite latitude in DTO class
//				satelliteCurrentPositionDetailsDTO.setAzimuth(satPos.getAzimuth());
//				satelliteCurrentPositionDetailsDTO.setSpeed((satPos.getRangeRate() * 3.6));
//				satelliteCurrentPositionDetailsDTO.setCurrentLocationWKT(satellitePositionWKT);
//				satelliteCurrentPositionDetailsDTO.setUpdatedDate(satPos.getTime().toString());
//				satelliteCurrentPositionDetailsDTO.setElevation(satPos.getElevation());
//				satelliteCurrentPositionDetailsDTO.setRange(satPos.getRange());
//				satelliteCurrentPositionDetailsDTO.setFootprintWKT(footprintWKT.toString());		
//
//
////			satelliteCurrentPositionDetailsDAO.save(satelliteCurrentPositionDetailsDTO);
//				entity = convertDTOToEntity(satelliteCurrentPositionDetailsDTO, satelliteDetails);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
////			update record 
//			
//			
//			
//
//		}
//		satelliteCurrentPositionDetailsDAO.save(entity);
//
////		System.out.println("Relative Speed " + satPos.getRangeRate() * 3.6);
//
//		return null;
//	}

	@Override
	@Transactional
	public List<SatelliteCurrentPositionDetailsDTO> getSatellitePostionByName(String satelliteName) {
	    SatelliteCurrentPositionDetailsDTO dto = new SatelliteCurrentPositionDetailsDTO();

	    Optional<SatelliteCurrentPositionDetails> existingRecord =
	            satelliteCurrentPositionDetailsDAO.findBySatelliteDetails_SatelliteName(satelliteName);

	    SatelliteDetails satelliteDetails = daoSatelliteDAO.getReferenceById(satelliteName);
	    String tleData = satelliteName + "\n" + satelliteDetails.getSatelliteLine1() + "\n"
	            + satelliteDetails.getSatelliteLine2() + "\n";
	    InputStream is = new ByteArrayInputStream(tleData.getBytes());

	    try {
	        List<TLE> tleList = TLE.importSat(is);
	        TLE tle = new TLE(tleList.get(0));
	        Satellite satellite = SatelliteFactory.createSatellite(tle);
	        Date date = new Date();
	        GroundStationPosition qth = new GroundStationPosition(12.9716, 77.5946, 0.0);
	        SatPos satPos = satellite.getPosition(qth, date);

	        // Extract required values
	        double lon = Math.toDegrees(satPos.getLongitude());
	        double lat = Math.toDegrees(satPos.getLatitude());
	        String satellitePositionWKT = "POINT(" + lon + " " + lat + ")";
	        double azimuth = satPos.getAzimuth();
	        String updatedDate = satPos.getTime().toString();
	        double elevation = satPos.getElevation();
	        double speed = (satPos.getRangeRate() * 3.6);
	        double range = Math.toDegrees(satPos.getRange());

	        // Build footprint WKT
	        StringBuilder footprintWKT = new StringBuilder("LINESTRING(");
	        for (int i = 0; i < satPos.getRangeCircle().size(); i++) {
	            Position pos = satPos.getRangeCircle().get(i);
	            footprintWKT.append(String.format("%.6f %.6f", pos.getLon(), pos.getLat()));
	            if (i != satPos.getRangeCircle().size() - 1) {
	                footprintWKT.append(", ");
	            }
	        }
	        footprintWKT.append(")");

	        SatelliteCurrentPositionDetails entity;

	        if (existingRecord.isPresent()) {
	            // ✅ Only update selective fields
	            entity = existingRecord.get();
	        } else {
	            // ✅ Create new entity
	            entity = new SatelliteCurrentPositionDetails();
	            entity.setSatelliteDetails(satelliteDetails);
//	            entity.setFootprintVisibleStatus(true); // default for new
	        }

	        // Common updates
	        entity.setSatLatitude(lat);
	        entity.setSatLongitude(lon);
	        entity.setCurrentLocationWKT(satellitePositionWKT);
	        entity.setAzimuth(azimuth);
	        entity.setUpdatedDate(updatedDate);
	        entity.setFootprintWKT(footprintWKT.toString());
	        entity.setElevation(elevation);
	        entity.setSpeed(speed);
	        entity.setRange(range);

	        // Save (insert or update)
	        satelliteCurrentPositionDetailsDAO.save(entity);

	        // Build DTO
	        dto.setSatelliteName(satelliteName);
	        dto.setSatLatitude(lat);
	        dto.setSatLongitude(lon);
	        dto.setCurrentLocationWKT(satellitePositionWKT);
	        dto.setAzimuth(azimuth);
	        dto.setUpdatedDate(updatedDate);
	        dto.setElevation(elevation);
	        dto.setSpeed(speed);
	        dto.setRange(range);
	        dto.setFootprintWKT(footprintWKT.toString());
//	        dto.setFootprintVisibleStatus(false);

	        return Collections.singletonList(dto);

	    } catch (IOException e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}
	
	
//	private SatelliteCurrentPositionDetails convertDTOToEntity(SatelliteCurrentPositionDetailsDTO dto,
//			SatelliteDetails satelliteDetails) {
//		SatelliteCurrentPositionDetails entity = new SatelliteCurrentPositionDetails();
//
//		entity.setSatLatitude(dto.getSatLatitude());
//		entity.setSatLongitude(dto.getSatLongitude());
//		entity.setCurrentLocationWKT(dto.getCurrentLocationWKT());
//		entity.setSpeed(dto.getSpeed());
//		entity.setAzimuth(dto.getAzimuth());
//		entity.setUpdatedDate(dto.getUpdatedDate());
//		entity.setRange(dto.getRange());
//		entity.setFootprintWKT(dto.getFootprintWKT());
//
//		// Use default or true if null check is needed
//		entity.setFootprintVisibleStatus(false);
//
//		entity.setSatelliteDetails(satelliteDetails);
//		entity.setElevation(dto.getElevation());
//
//		return entity;
//	}

}
