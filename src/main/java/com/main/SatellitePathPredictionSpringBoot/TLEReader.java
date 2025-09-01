package com.main.SatellitePathPredictionSpringBoot;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.github.amsacode.predict4java.GroundStationPosition;
import com.github.amsacode.predict4java.SatPos;
import com.github.amsacode.predict4java.Satellite;
import com.github.amsacode.predict4java.SatelliteFactory;
import com.github.amsacode.predict4java.TLE;

public class TLEReader {

	public static void main(String[] args) {
		String fileName = "ISS_ZARYA.tle";
		JSONArray satelliteJSONData = new JSONArray();
//		SatelliteForprintGenerator footprintGenerator = new SatelliteForprintGenerator();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

			String line;
			while ((line = reader.readLine()) != null) {
	
				String name = line.trim();			
//				System.out.println("Name " + name);
				String line1 = reader.readLine();
//				System.out.println("line1 " + line1);
				String line2 = reader.readLine();
//				System.out.println("line2 " + line2);

				
				
				
				if (line1 != null && line2 != null) {
					JSONObject satelliteJsonObject = new JSONObject();
					satelliteJsonObject.put("name", name);
					satelliteJsonObject.put("line1", line1);
					satelliteJsonObject.put("line2", line2);

					satelliteJSONData.put(satelliteJsonObject);

				}

			}

			String name, line1, line2;

			for (int i = 0; i < satelliteJSONData.length(); i++) {
				JSONObject satelliteData = satelliteJSONData.getJSONObject(i);
				name = satelliteData.getString("name");
				line1 = satelliteData.getString("line1");
				line2 = satelliteData.getString("line2");

				String tleData = name + "\n" + line1 + "\n" + line2 + "\n";
				InputStream is = new ByteArrayInputStream(tleData.getBytes());
				List<TLE> tleList = TLE.importSat(is);
				if (tleList.isEmpty()) {
					System.out.println("No TLE Parsed");
					return;
				}

				TLE tle = new TLE(tleList.get(0));

				Satellite satellite = SatelliteFactory.createSatellite(tle);

				GroundStationPosition gsp = new GroundStationPosition(12.9716, 77.5946, 920);

				SatPos postion = satellite.getPosition(gsp, new Date());
				String SatelliteFootprint = SatelliteForprintGenerator.generateFootprintWKT(postion, 360);
				System.out.println(satelliteData.get("name"));
				System.out.println(postion.toString());
				System.out.println(SatelliteFootprint);
				System.out.println();
				Thread.sleep(5000);

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
