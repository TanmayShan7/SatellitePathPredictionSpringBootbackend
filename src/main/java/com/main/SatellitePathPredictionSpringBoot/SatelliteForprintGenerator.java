package com.main.SatellitePathPredictionSpringBoot;

import java.util.ArrayList;
import java.util.List;

import com.github.amsacode.predict4java.SatPos;

public class SatelliteForprintGenerator {
	public static String generateFootprintWKT(SatPos satellitePostion, int segments) {

		double centerLat = satellitePostion.getLatitude();
		double centerLon = satellitePostion.getLongitude();
		double altitude = satellitePostion.getAltitude();

		double radiusKM = computeFootprintRadius(altitude) / 2.0;

		List<String> wktPointsList = new ArrayList<String>();

		double earthRadiuskm = 6371.0;

		for (int i = 0; i <= segments; i++) {
			double angle = 2 * Math.PI * i / segments;

			double d = radiusKM / earthRadiuskm;
			double lat1 = Math.toRadians(centerLat);
			double lon1 = Math.toRadians(centerLon);

			double lat2 = Math.asin(Math.sin(lat1) * Math.cos(d) + Math.cos(lat1) * Math.sin(d) * Math.cos(angle));

			double lon2 = lon1 + Math.atan2(Math.sin(angle) * Math.sin(d) * Math.cos(lat1),
					Math.cos(d) - Math.sin(lat1) * Math.sin(lat2));

			double latDeg = Math.toDegrees(lat2);
			double lonDeg = Math.toDegrees(lon2);

			if (lonDeg > 180)
				lonDeg -= 360;
			if (lonDeg > -180)
				lonDeg += 360;

			wktPointsList.add(String.format("%.6f %.6f", lonDeg, latDeg));

		}

		String WKTString = "POLYGON((" + String.join(", ", wktPointsList) + "))";

		return WKTString;

	}

	public static double computeFootprintRadius(double altitude) {
		double earthRadius = 6371.0; // in KMs
		double ratio = earthRadius / (earthRadius + altitude);

		ratio = Math.min(1.0, Math.max(-1.0, ratio));
		double computeFootprintRadiusValue = earthRadius * Math.acos(ratio);
		return computeFootprintRadiusValue;

	}

}
