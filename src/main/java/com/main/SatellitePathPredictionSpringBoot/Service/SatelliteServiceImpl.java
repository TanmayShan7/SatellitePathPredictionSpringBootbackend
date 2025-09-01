package com.main.SatellitePathPredictionSpringBoot.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.SatellitePathPredictionSpringBoot.DAO.SatelliteDAO;
import com.main.SatellitePathPredictionSpringBoot.DTO.SatelliteDetailsDTO;
import com.main.SatellitePathPredictionSpringBoot.Entity.SatelliteDetails;

@Service
public class SatelliteServiceImpl implements SatelliteService {

	@Autowired
	SatelliteDAO satelliteDAO;

	@Override
	public List<String> getAllSatelliteNames() {
		// TODO Auto-generated method stub
		List<String> satelliteNameList = new ArrayList<String>();

		List<SatelliteDetails> satelliteList = satelliteDAO.findAll();

		for (SatelliteDetails satellite : satelliteList) {
			System.out.println(satellite.getSatelliteName());
			satelliteNameList.add(satellite.getSatelliteName());
		}

		return satelliteNameList;
	}

	@Override
	public List<SatelliteDetailsDTO> getAllSatelliteDetails() {
		// TODO Auto-generated method stub
		List<SatelliteDetailsDTO> satelliteDetailsDTOList = new ArrayList<SatelliteDetailsDTO>();
		List<SatelliteDetails> satelliteList = satelliteDAO.findAll();
		for (SatelliteDetails satellite : satelliteList) {
			System.out.println(satellite.getSatelliteName());
			SatelliteDetailsDTO satelliteDetailsDTO = new SatelliteDetailsDTO();

			satelliteDetailsDTO.setSatelliteName(satellite.getSatelliteName());
			satelliteDetailsDTO.setSatelliteLine1(satellite.getSatelliteLine1());
			satelliteDetailsDTO.setSatelliteLine2(satellite.getSatelliteLine2());
			satelliteDetailsDTO.setCategory(satellite.getCategory());
			satelliteDetailsDTO.setCountry_origin(satellite.getCountry_origin());
			satelliteDetailsDTO.setNorad(satellite.getNorad());
			satelliteDetailsDTO.setOperator(satellite.getOperator());
			satelliteDetailsDTO.setPurpose(satellite.getPurpose());
			satelliteDetailsDTO.setContractor(satellite.getContractor());
			satelliteDetailsDTO.setLaunchSite(satellite.getLaunchSite());
			satelliteDetailsDTO.setLaunchVehicle(satellite.getLaunchVehicle());
			satelliteDetailsDTO.setSatelliteOrbit(satellite.getSatelliteOrbit());
			satelliteDetailsDTOList.add(satelliteDetailsDTO);
			

		}

		return satelliteDetailsDTOList;
	}

}
