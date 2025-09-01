package com.main.SatellitePathPredictionSpringBoot.Others;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.github.amsacode.predict4java.GroundStationPosition;
import com.github.amsacode.predict4java.Position;
import com.github.amsacode.predict4java.SatPos;
import com.github.amsacode.predict4java.Satellite;
import com.github.amsacode.predict4java.SatelliteFactory;
import com.github.amsacode.predict4java.TLE;

public class OrbitPredictor {

	public static List<String> predictOrbitFor10Days(String tleLine1, String tleLine2, Date startDate)
			throws IOException, InterruptedException {

//		String name = "ISS (ZARYA)";
		String name = "STARLINK-3090";
		String tleData = name + "\n" + tleLine1 + "\n" + tleLine2 + "\n";
		InputStream is = new ByteArrayInputStream(tleData.getBytes());

		List<TLE> tleList = TLE.importSat(is);

		TLE tle = new TLE(tleList.get(0));

		Satellite satellite = SatelliteFactory.createSatellite(tle);

		Date date = new Date();

		GroundStationPosition qth = new GroundStationPosition(12.9716, 77.5946, 0.0); // Lat, Lon, Alt

		SatPos satPos = satellite.getPosition(qth, date);
//		System.out.println(satPos.toString());
//		Double lon = satPos.getLongitude();
//		Double lat = satPos.getLatitude();
//		System.out.println(lon + "    " + lat);
//		System.out.println(
//				"POINT(" + Math.toDegrees(satPos.getLongitude()) + " " + Math.toDegrees(satPos.getLatitude()) + ")");

		List<Position> footprintList = satPos.getRangeCircle();
//		System.out.println(satPos.getRangeCircle());
		StringBuilder sb = new StringBuilder("LINESTRING(");

		for (int i = 0; i < footprintList.size(); i++) {
			Position pos = footprintList.get(i);
			sb.append(String.format("%.6f %.6f", pos.getLon(), pos.getLat()));
			if (i != footprintList.size() - 1) {
				sb.append(", ");
			}

		}
		sb.append(")");
		System.out.println(sb);
		System.out.println("Relative Speed " + satPos.getRangeRate() * 3.6);

//		for satellite orbit

		System.out.println("Satelitte orbit  !!!");

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		Date startTime = calendar.getTime();
		double prevLon = Double.NaN;

		StringBuilder sbOrbit = new StringBuilder("LINESTRING(");
//		String firstCoordinate = null;
		int hour = 90;
		for (int i = 0; i <= hour; i++) {

			calendar.setTime(startTime);
			calendar.add(Calendar.MINUTE, i);
			Date time = calendar.getTime();
			SatPos pos = satellite.getPosition(qth, time);
//			System.out.println(pos.toString());
			double lat1 = Math.toDegrees(pos.getLatitude());
//			double lon1 = Math.toDegrees(pos.getLongitude());
			double rawLon = Math.toDegrees(pos.getLongitude());
			double unwrappedLon = rawLon;
			if (!Double.isNaN(prevLon)) {
				unwrappedLon = unwrapLongitude(prevLon, rawLon);
			}
			prevLon = unwrappedLon;
//		    double lat1 = pos.getLatitude();
//		    double lon1 = pos.getLongitude();
//		    System.out.println("POINT("+lon1+" "+lat1+")");
//			sb.append(String.format("%.6f %.6f", pos.getLon(), pos.getLat()));
//		    points.add(String.format(indiaLocale, "%.6f %.6f", lon, lat));
			sbOrbit.append(String.format("%.6f %.6f", unwrappedLon, lat1));
//			if(i==0) {
//				firstCoordinate = ", "+unwrappedLon+" "+lat1 ;
//				
//			}
			if (i != hour)
				sbOrbit.append(", ");

		}

		sbOrbit.append(")");
		System.out.println("##");
		System.out.println(sbOrbit.toString());
		System.out.println("##");
		// WKT LineString

		return null;
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

	public static void main(String[] args) throws Exception {
		// Example TLE (ISS)
//		String tle1 = "1 25544U 98067A   24193.46657407  .00005615  00000+0  10388-3 0  9994";
//		String tle2 = "2 25544  51.6402  65.7101 0004342 284.3971 145.3093 15.50446904317542";
//		String tle1 = "1 49132U 21082C   25175.54479806 -.00000407  00000+0 -26279-4 0  9997";
//		String tle2 = "2 49132  69.9980 225.2007 0003458 269.2787  90.7975 14.98330145207319";

//		STARLINK-3090           
//		1 49132U 21082C   25175.54479806 -.00000407  00000+0 -26279-4 0  9997
//		2 49132  69.9980 225.2007 0003458 269.2787  90.7975 14.98330145207319

//		List<String> orbit = predictOrbitFor10Days(tle1, tle2, new Date());

		// Example: Print as WKT LINESTRING
//		System.out.println("LINESTRING(" + String.join(", ", orbit) + ")");
	}
}