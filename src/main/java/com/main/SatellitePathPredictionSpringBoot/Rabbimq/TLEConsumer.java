package com.main.SatellitePathPredictionSpringBoot.Rabbimq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.amsacode.predict4java.GroundStationPosition;
import com.github.amsacode.predict4java.SatPos;
import com.github.amsacode.predict4java.Satellite;
import com.github.amsacode.predict4java.SatelliteFactory;
//import com.github.amsacode.predict4java.GroundStationPosition;
//import com.github.amsacode.predict4java.SatelliteFactory;
//import com.github.amsacode.predict4java.TLE;
import com.github.amsacode.predict4java.TLE;

import com.main.SatellitePathPredictionSpringBoot.DAO.SatelliteDAO;
import com.main.SatellitePathPredictionSpringBoot.DAO.UCSSatelliteDatabaseDAO;
import com.main.SatellitePathPredictionSpringBoot.Entity.SatelliteDetails;
import com.main.SatellitePathPredictionSpringBoot.Entity.UCSSatelliteDatabase;
import com.main.SatellitePathPredictionSpringBoot.Others.RabbitMQConfiguration;
import com.main.SatellitePathPredictionSpringBoot.Others.SatelliteOrbitGenerator;

@Service
public class TLEConsumer {

	@Autowired
	public UCSSatelliteDatabaseDAO ucsSatelliteDatabaseDAO;

	private final SatelliteDAO satelliteDAO;

	SatelliteOrbitGenerator satelliteOrbitGenerator = new SatelliteOrbitGenerator();

	public TLEConsumer(SatelliteDAO satelliteDAO) {
		super();
		this.satelliteDAO = satelliteDAO;
	}

	@RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
	public void tleConsumerDatabaseWriter(String message) throws Exception {
		satelliteDAO.deleteAll();
		int count = 0;
		List<String> messageList = message.lines().filter(l -> !l.trim().isEmpty()).toList();
//		messageList.stream().sorted(Comparator.)
		for (int i = 0; i < messageList.size(); i += 3) {
			SatelliteDetails satellite = new SatelliteDetails();
			UCSSatelliteDatabase satelliteData = GetSatelliteDetails(messageList.get(i + 1).trim().substring(2, 7).trim());
			satellite.setSatelliteName(messageList.get(i).trim());
			satellite.setSatelliteLine1(messageList.get(i + 1).trim());
			satellite.setNorad(getNoradFromLine1(messageList.get(i + 1).trim()));
			satellite.setSatelliteLine2(messageList.get(i + 2).trim());
			satellite.setSatelliteOrbit(getSatelliteOrbit(messageList.get(i).trim(), messageList.get(i + 1).trim(),
					messageList.get(i + 2).trim()));
//			if satelliteData is not present in ucs then it will be null or N/A; 
			if (satelliteData != null) {
				satellite.setCategory(satelliteData.getUsers());
				satellite.setCountry_origin(satelliteData.getCountryOwner());
				satellite.setOperator(satelliteData.getOperator());
				satellite.setPurpose(satelliteData.getPurpose());
				satellite.setContractor(satelliteData.getContractor());
				satellite.setLaunchSite(satelliteData.getLaunchSite());
				satellite.setLaunchVehicle(satelliteData.getLaunchVehicle());
			} else {
				satellite.setCategory(null);
				satellite.setCountry_origin(null);
				satellite.setOperator(null);
				satellite.setPurpose(null);
				satellite.setContractor(null);
				satellite.setLaunchSite(null);
				satellite.setLaunchVehicle(null);

			}
			
			count++;
			satelliteDAO.save(satellite);

		}
		
	}

	private UCSSatelliteDatabase GetSatelliteDetails(String norad) {
		// TODO Auto-generated method stub
		System.out.println(norad);
		List<UCSSatelliteDatabase> data = ucsSatelliteDatabaseDAO.findByNorad(norad);
		if (!data.isEmpty()) {
			return data.get(0);
		} else {
			return null;
		}
	}

	public String getNoradFromLine1(String line1) {
		// TODO Auto-generated method stub
		return line1.substring(2, 7).trim();
	}

	public String getSatelliteOrbit(String name, String line1, String line2) throws IOException {
		// TODO Auto-generated method stub

		String tleData = name + "\n" + line1 + "\n" + line2 + "\n";
		InputStream is = new ByteArrayInputStream(tleData.getBytes());
		List<TLE> tleList = TLE.importSat(is);
		TLE tle = new TLE(tleList.get(0));
		Satellite satellite = SatelliteFactory.createSatellite(tle);
		GroundStationPosition qth = new GroundStationPosition(12.9716, 77.5946, 0.0); // Lat, Lon, Alt => bangalore
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Date startTime = calendar.getTime();
		double prevLon = Double.NaN;

		StringBuilder sbOrbit = new StringBuilder("LINESTRING(");
		int hour = 100;
		for (int i = 0; i <= hour; i++) {

			calendar.setTime(startTime);
			calendar.add(Calendar.MINUTE, i);
			Date time = calendar.getTime();
			SatPos pos = satellite.getPosition(qth, time);
			double lat1 = Math.toDegrees(pos.getLatitude());
			double rawLon = Math.toDegrees(pos.getLongitude());
			double unwrappedLon = rawLon;
			if (!Double.isNaN(prevLon)) {
				unwrappedLon = unwrapLongitude(prevLon, rawLon);
			}
			prevLon = unwrappedLon;
			sbOrbit.append(String.format("%.6f %.6f", unwrappedLon, lat1));
			if (i != hour)
				sbOrbit.append(", ");

		}
		sbOrbit.append(")");
		return sbOrbit.toString();
	}

	public static double unwrapLongitude(double prevLon, double currentLon) {
		// TODO Auto-generated method stub
		double delta = currentLon - prevLon;
		if (delta > 180)
			return currentLon - 360;
		if (delta < -180)
			return currentLon + 360;
		return currentLon;
	}

}
